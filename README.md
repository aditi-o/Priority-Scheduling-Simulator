# Priority-Scheduling-Simulator
A Java Swing–based GUI application that simulates CPU Priority Scheduling algorithms and visualizes process execution using a Gantt Chart.

This project is designed for operating systems learning, helping students understand preemptive and non-preemptive scheduling concepts through interactive visualization.

📌 Features
Interactive Java Swing GUI

Supports:

Preemptive Priority Scheduling

Non-Preemptive Priority Scheduling (FCFS-based)

Add multiple processes with:

Process ID

Arrival Time

Burst Time

Priority

Automatically calculates:

Completion Time (CT)

Turnaround Time (TAT)

Waiting Time (WT)

Average TAT & WT

Color-coded Gantt Chart visualization

Clear tabular output of results

🧠 Algorithms Implemented
🔹 Preemptive Priority Scheduling
CPU is assigned to the process with the highest priority (lower value = higher priority).

Running process can be preempted if a higher priority process arrives.

Execution tracked per time unit for accurate visualization.

🔹 Non-Preemptive Priority Scheduling (FCFS)
Processes are executed in arrival order.

Once a process starts, it runs until completion.

No preemption.

🛠️ Tech Stack
Java (JDK 8+)

Java Swing

AWT (Graphics & Events)

📂 Project Structure
stylus
Copy code
.
├── PrioritySchedulingGUI.java
└── README.md
▶️ Getting Started
Prerequisites
Java JDK 8 or higher

Any Java IDE (IntelliJ IDEA, Eclipse, NetBeans) or terminal

Run Locally
bash
Copy code
javac PrioritySchedulingGUI.java
java PrioritySchedulingGUI
🧪 Usage
Enter:

Process ID

Arrival Time

Burst Time

Priority

Click Add Process

Select the scheduling algorithm

Click Calculate & Show Gantt Chart

View:

Process table

Gantt chart

Average waiting & turnaround times

📊 Output Format
Column	Description
AT	Arrival Time
BT	Burst Time
Pri	Priority
CT	Completion Time
TAT	Turnaround Time
WT	Waiting Time
Better input validation

Dark mode UI
