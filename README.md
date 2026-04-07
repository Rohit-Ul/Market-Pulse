<div align="center">

# 📈 Real-Time Stock Simulation Engine

**A Spring Boot application simulating real-time stock price fluctuations for 50 scripts using Brownian Motion, with WebSocket-based live streaming, OHLCV aggregation, and a user alert system.**

<br/>

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![WebSocket](https://img.shields.io/badge/WebSocket-Live_Streaming-0078D7?style=for-the-badge&logo=websocket&logoColor=white)
![Kafka](https://img.shields.io/badge/Apache_Kafka-Planned-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-Planned-DC382D?style=for-the-badge&logo=redis&logoColor=white)

<br/>

[![Status](https://img.shields.io/badge/Status-Active_Development-brightgreen?style=flat-square)]()
[![Architecture](https://img.shields.io/badge/Architecture-Event_Driven-blueviolet?style=flat-square)]()
[![Scripts](https://img.shields.io/badge/Simulated_Scripts-50-blue?style=flat-square)]()
[![Interval](https://img.shields.io/badge/Tick_Interval-5_seconds-orange?style=flat-square)]()

</div>

---

## 📌 Table of Contents

- [Project Overview](#-project-overview)
- [Current Architecture](#-current-architecture)
- [How It Works](#-how-it-works)
- [Tech Stack](#-tech-stack)
- [Known Issues](#-known-issues--active-fixes)
- [Roadmap](#-roadmap)
- [Kafka Integration Plan](#-kafka-integration-plan)
- [Multi-User Scaling](#-multi-user-scaling-strategy)
- [Feature Backlog](#-feature-backlog)
- [Scale Milestones](#-scale-milestones)

---

## 🧭 Project Overview

This project simulates a **real-time stock market data engine** — similar to what powers live trading terminals. It is built as a learning system to explore:

- Real-time data pipelines with **WebSocket**
- In-memory buffering with **ConcurrentHashMap**
- Time-series aggregation into **OHLCV** (Open/High/Low/Close/Volume) candles
- **Price simulation** using Geometric Brownian Motion
- **User alert system** triggered on price conditions
- *(Planned)* Event-driven architecture with **Apache Kafka**
- *(Planned)* Horizontal scaling with **Redis Pub/Sub**

---

## 🏗️ Current Architecture

### System Flow Diagram

```mermaid
flowchart TD
    subgraph SIM ["⚙️ Price Simulation Layer"]
        A[Brownian Motion Algorithm] -->|generates tick every 5s| B[ConcurrentHashMap Buffer\n50 scripts in memory]
    end

    subgraph STREAM ["📡 5-Second Streaming Loop"]
        B -->|convert to OHLCV| C[OHLCV Object\nO/H/L/C/V per 5s]
        C -->|WebSocket push| D[Frontend Chart\nLive Price Display]
    end

    subgraph PERSIST ["💾 1-Minute Persistence Loop"]
        B -->|aggregate 1-min candle| E[1-Min OHLCV Object]
        E -->|store| F[(Database\nOHLCV Table)]
        F -->|check| G{Any Alerts\nfor This Script?}
        G -->|YES ⚠️| H[WebSocket Broadcast\n🔴 ALL Clients]
        G -->|NO| I[Skip]
    end

    style H fill:#ff6b6b,color:#fff,stroke:#c0392b
    style SIM fill:#e8f5e9,stroke:#4caf50
    style STREAM fill:#e3f2fd,stroke:#2196f3
    style PERSIST fill:#fff3e0,stroke:#ff9800
```

> **🔴 Bug:** The alert broadcast in the persistence loop sends to **all connected clients**, not just the user who set the alert. This is the #1 priority fix.

---

## ⚙️ How It Works

### Price Generation (Every 5 Seconds)

| Step | Component | Details |
|------|-----------|---------|
| 1 | **Brownian Motion Generator** | Simulates realistic price movement for 50 scripts using `dS = S(μdt + σdW)` |
| 2 | **ConcurrentHashMap Buffer** | Thread-safe in-memory store holds all price ticks for the current window |
| 3 | **OHLCV Conversion** | Aggregates ticks into Open/High/Low/Close/Volume for the 5s window |
| 4 | **WebSocket Push** | Sends OHLCV data to frontend — powers live candlestick chart for ITC, RELIANCE, etc. |

### Persistence & Alerts (Every 1 Minute)

| Step | Component | Details |
|------|-----------|---------|
| 1 | **1-Min Aggregation** | All 5s ticks in the last 60s are rolled into a single 1-min OHLCV candle |
| 2 | **DB Persist** | Candle stored to database for historical queries and backtesting |
| 3 | **Alert Check** | Query DB for user-configured price alerts on that script |
| 4 | **Notify** | ⚠️ *Currently broadcasts to all clients — needs fix (see below)* |

---

## 🛠️ Tech Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| Backend | Spring Boot 3.x | Application framework |
| Real-time | WebSocket (STOMP) | Live price & alert delivery |
| In-Memory | ConcurrentHashMap | Thread-safe tick buffer |
| Simulation | Brownian Motion | Realistic price fluctuation |
| Database | PostgreSQL / MySQL | OHLCV candle persistence |
| *(Planned)* | Apache Kafka | Decoupled event pipeline |
| *(Planned)* | Redis Pub/Sub | Multi-instance WS fanout |

---

## 🔴 Known Issues / Active Fixes

### Bug: Alert Broadcasting to All Clients

**Problem:** When an alert condition triggers for User A, the WebSocket notification is sent to every connected user — not just User A.

**Root Cause:** No session registry exists. The service iterates all WebSocket sessions instead of looking up the session by `userId`.

**Fix (In Progress):**

```java
@Component
public class WebSocketSessionRegistry {

    // userId → WebSocket session mapping
    private final Map<Long, WebSocketSession> sessions =
        new ConcurrentHashMap<>();

    public void register(Long userId, WebSocketSession session) {
        sessions.put(userId, session);
    }

    public void remove(Long userId) {
        sessions.remove(userId);
    }

    // ✅ Send to ONE specific user only
    public void sendToUser(Long userId, String json) {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(json));
        }
    }
}
```

> Pass `userId` as a JWT claim during the WebSocket handshake. Extract it in `afterConnectionEstablished()` via `session.getAttributes()`. For multi-tab support, use `Map<Long, List<WebSocketSession>>`.

---

## 🗺️ Roadmap

```mermaid
gantt
    title Development Roadmap
    dateFormat  YYYY-MM-DD
    section Bug Fixes
    WebSocket Session Registry     :active, fix1, 2026-04-07, 3d
    JWT Auth on WS Handshake       :fix2, after fix1, 2d
    Notification Log Table         :fix3, after fix2, 1d

    section Phase 1 - APIs
    Historical Candle REST API     :p1a, after fix3, 2d
    Multi-timeframe OHLCV          :p1b, after p1a, 4d

    section Phase 2 - Kafka
    Kafka Price-Tick Producer      :p2a, after p1b, 4d
    OHLCV Aggregator Consumer      :p2b, after p2a, 3d
    Alert Checker Consumer         :p2c, after p2b, 2d

    section Phase 3 - Scale
    User Script Subscriptions      :p3a, after p2c, 3d
    Redis Pub/Sub WS Fanout        :p3b, after p3a, 3d
    Multi-instance Testing         :p3c, after p3b, 3d
```

---

## 🔧 Kafka Integration Plan

> **Why Kafka?** Currently, price generation, OHLCV conversion, DB write, and alert checking are all tightly coupled inside one `@Scheduled` thread. A slow alert query can delay the next price tick. Kafka decouples every step into independent consumers.

### Proposed Kafka Topic Architecture

```mermaid
flowchart LR
    subgraph PROD ["Producers"]
        A[⚙️ Brownian Motion\nGenerator]
    end

    subgraph TOPICS ["Kafka Topics"]
        T1[["📨 stock-price-ticks\n50 partitions\n1 per script"]]
        T2[["📨 ohlcv-1min\n50 partitions"]]
        T3[["📨 alert-triggered\n10 partitions"]]
    end

    subgraph CONS ["Consumers"]
        C1[🔄 OHLCV Aggregator\nConsumer Group]
        C2[🔔 Alert Checker\nConsumer Group]
        C3[📡 WS Dispatcher\nConsumer Group]
    end

    A -->|key=scriptId| T1
    T1 --> C1
    C1 -->|after 1-min candle stored| T2
    T2 --> C2
    C2 -->|triggered alert| T3
    T3 --> C3
    C3 -->|sendToUser| D[👤 User WebSocket]

    style T1 fill:#ffcccc,stroke:#e74c3c
    style T2 fill:#ffcccc,stroke:#e74c3c
    style T3 fill:#ffcccc,stroke:#e74c3c
```

### Topic Design Rationale

| Topic | Partitions | Key Strategy | Why |
|-------|-----------|-------------|-----|
| `stock-price-ticks` | 50 | `scriptId` | Guarantees tick ordering per script, 50-way parallelism |
| `ohlcv-1min` | 50 | `scriptId` | Each script's candles processed independently |
| `alert-triggered` | 10 | `userId` | All alerts for one user go to same partition |

### Sample Producer/Consumer Code

<details>
<summary>📄 View Kafka Producer — Price Tick Publisher</summary>

```java
@Service
public class PriceTickProducer {

    @Autowired
    private KafkaTemplate<String, PriceTick> kafkaTemplate;

    public void publish(PriceTick tick) {
        // key = scriptId ensures ordering per script
        kafkaTemplate.send("stock-price-ticks",
            tick.getScriptId().toString(), tick);
    }
}
```

</details>

<details>
<summary>📄 View Kafka Consumer — OHLCV Aggregator</summary>

```java
// Replaces your @Scheduled aggregation logic
@KafkaListener(
    topics = "stock-price-ticks",
    groupId = "ohlcv-aggregator"
)
public void onTick(PriceTick tick) {
    buffer.accumulate(tick);
    // flush to DB + publish to ohlcv-1min every 60s
}

// Triggered by new 1-min candle — replaces @Scheduled alert check
@KafkaListener(
    topics = "ohlcv-1min",
    groupId = "alert-checker"
)
public void onCandle(OhlcvCandle candle) {
    alertService.checkAndNotify(candle);
}
```

</details>

---

## 🌐 Multi-User Scaling Strategy

### The WebSocket Fan-out Problem

When scaling to multiple server instances, User A's WebSocket lives on Server 1 and User B's on Server 2. Server 1 cannot deliver a notification to a session it does not hold.

```mermaid
flowchart TD
    subgraph PROBLEM ["❌ Without Redis — Multi-Instance Problem"]
        A1[Alert for User B fires\non Server 1] --> B1[Server 1 registry\nonly has User A]
        B1 --> C1[❌ User B's notification\nsilently dropped]
    end

    subgraph SOLUTION ["✅ With Redis Pub/Sub — Cross-Instance Delivery"]
        A2[Alert for User B fires] --> B2[Publish to Redis\nchannel: ws:user:456]
        B2 --> C2[Server 1 receives —\nUser B not here, skip]
        B2 --> D2[Server 2 receives —\nUser B IS here ✅]
        D2 --> E2[Alert delivered to\nUser B's WebSocket]
    end

    style PROBLEM fill:#ffebee,stroke:#e53935
    style SOLUTION fill:#e8f5e9,stroke:#43a047
```

> **Note:** On localhost with a single instance, this is not needed. The session registry fix alone is sufficient. Add Redis only when you run 2+ server instances.

---

## ✨ Feature Backlog

| Priority | Feature | Description | Status |
|----------|---------|-------------|--------|
| 🔴 **P0** | WebSocket Session Registry | Fix alert routing — send only to alert owner | 🔧 In Progress |
| 🔴 **P0** | Notification Log Table | Persist every triggered alert to DB | 📋 Planned |
| 🟡 **P1** | Historical Candle REST API | `GET /api/candles?symbol=ITC&interval=1m` | 📋 Planned |
| 🟡 **P1** | Multi-Timeframe OHLCV | Aggregate 1m → 5m → 15m → 1h candles | 📋 Planned |
| 🟢 **P2** | Kafka Price-Tick Pipeline | Replace `@Scheduled` with Kafka producer/consumer | 📋 Planned |
| 🟢 **P2** | Rich Alert Conditions | % change, volume spike, RSI crossing 70/30 | 📋 Planned |
| 🔵 **P3** | User Script Subscriptions | Stream only watchlisted scripts per user | 📋 Planned |
| 🔵 **P3** | Virtual Portfolio P&L | Real-time P&L across user holdings | 📋 Planned |
| ⚪ **P4** | Redis Pub/Sub WS Fanout | Multi-instance alert delivery | 📋 Planned |

---

## 📊 Scale Milestones

```mermaid
flowchart LR
    L1["🟢 Level 1\n50 Scripts\n50 Users\n\nSingle instance\nConcurrentHashMap\nSession Registry Fix"] -->
    L2["🟡 Level 2\n500 Scripts\n500 Users\n\nKafka pipeline\nConsumer groups\nMonitor lag"] -->
    L3["🔵 Level 3\nMultiple Instances\n\nRedis Pub/Sub\nLoad balancer\nJMeter stress test"]

    style L1 fill:#e8f5e9,stroke:#4caf50
    style L2 fill:#fff9c4,stroke:#f9a825
    style L3 fill:#e3f2fd,stroke:#1976d2
```

| Level | Scripts | Users | Architecture | Status |
|-------|---------|-------|-------------|--------|
| **L1** | 50 | 50 | Single instance, ConcurrentHashMap, Session Registry | 🔧 Active |
| **L2** | 500 | 500 | + Apache Kafka, Consumer Groups | 📋 Planned |
| **L3** | 1000+ | 1000+ | + Redis Pub/Sub, Load Balancer, Multi-instance | 🔮 Future |

---

## 📁 Project Structure

```
stock-simulation/
├── src/main/java/
│   ├── simulation/
│   │   ├── BrownianMotionGenerator.java    # Price simulation engine
│   │   └── PriceScheduler.java             # @Scheduled 5s tick loop
│   ├── buffer/
│   │   └── PriceTickBuffer.java            # ConcurrentHashMap store
│   ├── aggregation/
│   │   ├── OhlcvAggregator.java            # 5s + 1min OHLCV builder
│   │   └── OhlcvPersistenceJob.java        # 1-min DB write scheduler
│   ├── alert/
│   │   ├── AlertChecker.java               # DB alert query + notify
│   │   └── AlertService.java               # Alert CRUD
│   ├── websocket/
│   │   ├── StockWebSocketHandler.java      # WS connection handler
│   │   └── WebSocketSessionRegistry.java   # ⬅️ TODO: Implement this
│   └── model/
│       ├── PriceTick.java
│       ├── OhlcvCandle.java
│       └── Alert.java
└── src/main/resources/
    └── application.yml
```

---

## 🚀 Getting Started

```bash
# Clone the repository
git clone https://github.com/your-username/stock-simulation.git
cd stock-simulation

# Run the application
./mvnw spring-boot:run

# WebSocket endpoint
ws://localhost:8080/ws/stock-prices

# Connect with client
# Pass userId as query param: ws://localhost:8080/ws/stock-prices?userId=123
```

---

<div align="center">

**Built with ☕ Java + Spring Boot | Learning system for real-time data engineering**

[![GitHub](https://img.shields.io/badge/Follow_Progress-GitHub-181717?style=for-the-badge&logo=github)](https://github.com/your-username/stock-simulation)

</div>
