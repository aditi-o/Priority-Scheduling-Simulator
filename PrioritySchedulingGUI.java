import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;

class Process {
    int pid, arrivalTime, burstTime, priority, remainingTime, completionTime, waitingTime, turnaroundTime;
    ArrayList<int[]> executionIntervals; // Store intervals of execution (startTime, endTime)

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime; // For preemptive scheduling
        this.priority = priority;
        this.executionIntervals = new ArrayList<>(); // For preemption tracking
    }
}

public class PrioritySchedulingGUI extends JFrame {

    private JTextField processField, arrivalField, burstField, priorityField;
    private JButton addButton, calculateButton;
    private JTextArea resultArea;
    private JPanel ganttPanel;
    private JComboBox<String> algorithmSelector;
    private ArrayList<Process> processes = new ArrayList<>();
    private ArrayList<int[]> ganttSequence = new ArrayList<>(); // Track the sequence of process executions with timeline

    public PrioritySchedulingGUI() {
        setTitle("Priority Scheduling - Preemptive and Non-Preemptive (FCFS)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Process Details"));

        inputPanel.add(new JLabel("Process ID:"));
        processField = new JTextField();
        inputPanel.add(processField);

        inputPanel.add(new JLabel("Arrival Time:"));
        arrivalField = new JTextField();
        inputPanel.add(arrivalField);

        inputPanel.add(new JLabel("Burst Time:"));
        burstField = new JTextField();
        inputPanel.add(burstField);

        inputPanel.add(new JLabel("Priority:"));
        priorityField = new JTextField();
        inputPanel.add(priorityField);

        // Algorithm Selector
        inputPanel.add(new JLabel("Algorithm:"));
        algorithmSelector = new JComboBox<>(new String[]{"Preemptive Priority", "Non-Preemptive (FCFS)"});
        inputPanel.add(algorithmSelector);

        addButton = new JButton("Add Process");
        inputPanel.add(addButton);

        calculateButton = new JButton("Calculate & Show Gantt Chart");
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.NORTH);

        // Gantt Panel
        ganttPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGanttChart(g);
            }
        };
        ganttPanel.setBorder(BorderFactory.createTitledBorder("Gantt Chart"));
        add(ganttPanel, BorderLayout.CENTER);

        // Result Panel
        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProcess();
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) algorithmSelector.getSelectedItem();
                if (selectedAlgorithm.equals("Preemptive Priority")) {
                    calculatePreemptivePriorityScheduling();
                } else {
                    calculateNonPreemptivePriorityScheduling(); // FCFS
                }
                ganttPanel.repaint();
            }
        });
    }

    private void addProcess() {
        try {
            int pid = Integer.parseInt(processField.getText());
            int arrival = Integer.parseInt(arrivalField.getText());
            int burst = Integer.parseInt(burstField.getText());
            int priority = Integer.parseInt(priorityField.getText());

            processes.add(new Process(pid, arrival, burst, priority));
            resultArea.append("Process P" + pid + " added.\n");

            // Clear the fields for the next process
            processField.setText("");
            arrivalField.setText("");
            burstField.setText("");
            priorityField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.");
        }
    }

    // Non-preemptive (FCFS)
    private void calculateNonPreemptivePriorityScheduling() {
        if (processes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No processes added!");
            return;
        }

        // Sort processes by arrival time (FCFS)
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        float totalTurnaroundTime = 0, totalWaitingTime = 0;

        for (Process p : processes) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }
            p.completionTime = currentTime + p.burstTime;
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;
            currentTime = p.completionTime;

            totalTurnaroundTime += p.turnaroundTime;
            totalWaitingTime += p.waitingTime;

            // Add to the Gantt chart sequence
            ganttSequence.add(new int[]{p.pid, currentTime - p.burstTime, currentTime});
        }

        displayResults(totalTurnaroundTime, totalWaitingTime);
    }

    // Preemptive Priority Scheduling
    private void calculatePreemptivePriorityScheduling() {
        if (processes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No processes added!");
            return;
        }

        // Sort processes by arrival time initially
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0, completed = 0;
        int n = processes.size();
        float totalTurnaroundTime = 0, totalWaitingTime = 0;

        ArrayList<Process> remainingProcesses = new ArrayList<>(processes);

        while (completed != n) {
            // Find the highest priority process that has arrived by currentTime and has remaining burst time
            Process currentProcess = null;
            for (Process p : remainingProcesses) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    if (currentProcess == null || p.priority < currentProcess.priority) {
                        currentProcess = p;
                    }
                }
            }

            if (currentProcess != null) {
                currentProcess.executionIntervals.add(new int[]{currentTime, currentTime + 1});
                ganttSequence.add(new int[]{currentProcess.pid, currentTime, currentTime + 1});
                currentProcess.remainingTime--;
                currentTime++;

                if (currentProcess.remainingTime == 0) {
                    completed++;
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    totalTurnaroundTime += currentProcess.turnaroundTime;
                    totalWaitingTime += currentProcess.waitingTime;
                }
            } else {
                currentTime++;
            }
        }

        displayResults(totalTurnaroundTime, totalWaitingTime);
    }

    private void displayResults(float totalTurnaroundTime, float totalWaitingTime) {
        resultArea.setText(""); // Clear previous results
        resultArea.append("Process\tAT\tBT\tPri\tCT\tTAT\tWT\n");
        for (Process p : processes) {
            resultArea.append("P" + p.pid + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" + p.priority + "\t" +
                    p.completionTime + "\t" + p.turnaroundTime + "\t" + p.waitingTime + "\n");
        }

        float avgTurnaroundTime = totalTurnaroundTime / processes.size();
        float avgWaitingTime = totalWaitingTime / processes.size();

        resultArea.append("\nAverage Turnaround Time: " + avgTurnaroundTime);
        resultArea.append("\nAverage Waiting Time: " + avgWaitingTime);
    }

    // Drawing Gantt Chart with Preemption Reflected
    private void drawGanttChart(Graphics g) {
        if (ganttSequence.isEmpty()) {
            return;
        }

        int currentX = 50;
        int y = 50;
        int height = 40;
        int timelineY = 100;

        for (int[] entry : ganttSequence) {
            int pid = entry[0];
            int startTime = entry[1];
            int endTime = entry[2];
            int width = (endTime - startTime) * 20;

            // Color assignment based on process ID for distinction
            g.setColor(getColorByProcessID(pid));
            g.fillRect(currentX, y, width, height);
            g.setColor(Color.BLACK);
            g.drawRect(currentX, y, width, height);

            // Process ID label
            g.drawString("P" + pid, currentX + width / 2 - 10, y + height / 2);

            // Timeline markers
            g.drawString(String.valueOf(startTime), currentX, timelineY);
            currentX += width;
        }
        g.drawString(String.valueOf(currentX / 20), currentX, timelineY); // Final timeline marker
    }

    // Color assignment based on process ID for distinction
    private Color getColorByProcessID(int pid) {
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.YELLOW};
        return colors[pid % colors.length];
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PrioritySchedulingGUI gui = new PrioritySchedulingGUI();
                gui.setVisible(true);
            }
        });
    }
}
