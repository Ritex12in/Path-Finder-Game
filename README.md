# Terrain Path Finder Game

A grid-based strategy game built with **Jetpack Compose** that challenges players to reach a destination using the **least-cost path**.  
The game visually demonstrates **pathfinding algorithms**, dynamic scoring, and real-time decision making.

This project combines **Data Structures & Algorithms (DSA)** with **modern Android UI** to create an educational yet engaging experience.

---

## 🎯 Objective

- Reach a destination tile on a grid
- Minimize total terrain cost to achieve a **higher score**
- Compare the player’s path with the **optimal path**
- Learn how algorithmic decisions affect outcomes in real time

---

## 🧠 Core Concepts Used

- Graph traversal on a 2D grid
- Weighted shortest path algorithms
- Heuristic-based search
- Real-time score projection
- Canvas-based rendering in Jetpack Compose

---

## 🧩 Game Mechanics

### Grid
- Size: **15 × 15**
- Each tile represents a terrain with a specific cost

| Terrain | Cost |
|-------|------|
| Road | 1 |
| Mud | 3 |
| Water | 7 |
| Peak | 15 |
| Wall | Blocked |

---

### Player Rules
- Player starts at the **top-left corner**
- Destination is at the **bottom-right corner**
- Movement allowed:
    - Adjacent tiles
    - Straight-line movement (if no obstacles)
- Backtracking is not allowed
- Limited undo actions are available

---

### Scoring System

Score = 100 − (Player Cost − Optimal Cost)

- Optimal cost is computed using pathfinding
- Score dynamically updates after every move
- Deviating from the optimal path reduces the maximum possible score

---

## 🧮 Algorithm Used

### A* (A-Star) Pathfinding Algorithm

The game uses **A\*** for pathfinding with the following characteristics:

- Weighted grid
- Manhattan distance heuristic
- Priority queue based on:

f(n) = g(n) + h(n)

Where:
- `g(n)` = actual cost from start
- `h(n)` = estimated distance to goal

---

### Why A*?

- Goal-directed search
- Faster than Dijkstra in single-destination scenarios
- Guarantees optimal path with admissible heuristic
- Suitable for real-time recalculations

---

### When Dijkstra Would Be Used

If the game is extended to:
- Multiple destinations
- Scoring based on the *best among all goals*
- Global cost analysis

Then **Dijkstra’s algorithm** becomes more suitable, as it computes the shortest path to all nodes in a single run.

---

## 🖼 UI & Rendering

- Built using **Jetpack Compose**
- Game board rendered using **Canvas**
- Animated player movement
- Visual distinction between:
    - Player path
    - Optimal path
    - Destination tile
- Circular countdown timer with progress indicator

---

## 🧱 Architecture

The project follows a **feature-based MVVM architecture**.


---

## 🧪 Key Features

- Guaranteed solvable map generation
- Real-time optimal path prediction
- Player vs optimal path comparison
- Dynamic scoring
- Undo system
- Time-based pressure
- Game-over analysis view

---

## 🚀 Technologies Used

- Kotlin
- Jetpack Compose
- Android Canvas
- MVVM Architecture
- Priority Queue
- Coroutines

---

## 📌 Learning Outcomes

- Practical understanding of A* vs Dijkstra
- Applying DSA concepts in real-world UI
- Handling performance-sensitive logic in games
- Designing algorithm-driven scoring systems
- Clean separation of logic and presentation

---

## 🔮 Future Enhancements

- Multiple destinations
- Algorithm toggle (A* / Dijkstra)
- Heatmap visualization of explored nodes
- Replay system
- Difficulty levels
- Sound and haptic feedback

---

## 📄 License

This project is for educational and learning purposes.
