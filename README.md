# Priority-Scheduling-Simulator
# 🚦 Priority Scheduling Simulator (Java Swing)

A Java Swing–based GUI application that simulates **CPU Priority Scheduling algorithms** and visualizes process execution using a **Gantt Chart**.  
This project is intended for **Operating Systems learning and academic use**.

---

## 📌 Features

- Interactive **Java Swing GUI**
- Supports:
  - **Preemptive Priority Scheduling**
  - **Non-Preemptive Priority Scheduling (FCFS-based)**
- Add multiple processes with:
  - Process ID
  - Arrival Time
  - Burst Time
  - Priority
- Automatic calculation of:
  - Completion Time (CT)
  - Turnaround Time (TAT)
  - Waiting Time (WT)
  - Average TAT and WT
- **Color-coded Gantt Chart visualization**
- Clear tabular output of results

---

## 🧠 Algorithms Implemented

### 🔹 Preemptive Priority Scheduling
- CPU is assigned to the process with the **highest priority**  
  (lower number = higher priority).
- A running process is **preempted** if a higher-priority process arrives.
- Execution is tracked at **unit time level** for accurate Gantt chart display.

### 🔹 Non-Preemptive Priority Scheduling (FCFS)
- Processes are executed in **First Come First Serve (FCFS)** order.
- Once execution starts, the process runs until completion.
- No preemption occurs.

---

## 🛠️ Tech Stack

- **Java (JDK 8 or higher)**
- **Java Swing**
- **AWT (Graphics & Event Handling)**

---

## ▶️ Getting Started

### Prerequisites
- Java JDK 8+
- Any Java IDE (IntelliJ IDEA, Eclipse, NetBeans) or terminal

### Run the Application

```bash
javac PrioritySchedulingGUI.java
java PrioritySchedulingGUI

How to Use

Enter the following process details:

Process ID

Arrival Time

Burst Time

Priority

Click Add Process

Select the scheduling algorithm

Click Calculate & Show Gantt Chart

View:

Process execution table

Gantt chart

Average waiting and turnaround times
Output Description
Column	Description
AT	Arrival Time
BT	Burst Time
Pri	Priority
CT	Completion Time
TAT	Turnaround Time
WT	Waiting Time

