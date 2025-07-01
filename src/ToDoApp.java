import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ToDoApp extends JFrame implements ActionListener {
    private JTextField taskInput;
    private JButton addButton, deleteButton, clearAllButton;
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JScrollPane scrollPane;
    private ArrayList<String> tasks;

    public ToDoApp() {
        tasks = new ArrayList<>();
        initializeGUI();
    }

    private void initializeGUI() {
        // Set up the main frame
        setTitle("To-Do List Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create components
        createInputPanel();
        createTaskListPanel();
        createButtonPanel();

        // Make the frame visible
        setVisible(true);
    }

    private void createInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Task"));

        taskInput = new JTextField();
        taskInput.setFont(new Font("Arial", Font.PLAIN, 14));
        taskInput.addActionListener(this); // Allow Enter key to add task

        addButton = new JButton("Add Task");
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        addButton.addActionListener(this);

        inputPanel.add(new JLabel("Task: "), BorderLayout.WEST);
        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.NORTH);
    }

    private void createTaskListPanel() {
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Tasks"));

        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setFont(new Font("Arial", Font.PLAIN, 14));

        scrollPane = new JScrollPane(taskList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        listPanel.add(scrollPane, BorderLayout.CENTER);
        add(listPanel, BorderLayout.CENTER);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(244, 67, 54));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
        deleteButton.addActionListener(this);

        clearAllButton = new JButton("Clear All");
        clearAllButton.setBackground(new Color(255, 152, 0));
        clearAllButton.setForeground(Color.WHITE);
        clearAllButton.setFont(new Font("Arial", Font.BOLD, 12));
        clearAllButton.addActionListener(this);

        buttonPanel.add(deleteButton);
        buttonPanel.add(clearAllButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == addButton || source == taskInput) {
            addTask();
        } else if (source == deleteButton) {
            deleteSelectedTask();
        } else if (source == clearAllButton) {
            clearAllTasks();
        }
    }

    private void addTask() {
        String task = taskInput.getText().trim();

        if (!task.isEmpty()) {
            if (!tasks.contains(task)) {
                tasks.add(task);
                listModel.addElement(task);
                taskInput.setText("");
                taskInput.requestFocus();

                JOptionPane.showMessageDialog(this, "Task added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Task already exists!",
                        "Duplicate Task", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a task!",
                    "Empty Task", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteSelectedTask() {
        int selectedIndex = taskList.getSelectedIndex();

        if (selectedIndex != -1) {
            String selectedTask = listModel.getElementAt(selectedIndex);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete: " + selectedTask + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                tasks.remove(selectedTask);
                listModel.removeElementAt(selectedIndex);

                JOptionPane.showMessageDialog(this, "Task deleted successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearAllTasks() {
        if (!tasks.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to clear all tasks?",
                    "Confirm Clear All", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                tasks.clear();
                listModel.clear();

                JOptionPane.showMessageDialog(this, "All tasks cleared!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No tasks to clear!",
                    "Empty List", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Set system look and feel for better appearance
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Use default look and feel if Nimbus is not available
        }

        // Create and show the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new ToDoApp();
        });
    }
}