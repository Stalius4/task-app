package controller;

import model.Task;
import view.TaskView;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import model.Database;


/**
 * Controller class that manages task operations and coordinates between the view and model.
 * Implements the MVC pattern by handling user input and updating the task data.
 */
public class TaskController {
    private final TaskView view;
    private final Scanner scanner;
    private final Database db;

    /**
     * Initializes the TaskController with necessary dependencies.
     * Sets up the database connection, view, and input scanner.
     */
    public TaskController() {
        db = Database.getInstance();
        db.getData();
        view = new TaskView();
        scanner = new Scanner(System.in);
    }

    /**
     * Main application loop that displays the menu and processes user choices.
     * Continues running until the user chooses to exit (option 4).
     */
    public void run() {
        boolean running = true;
        while (running) {
            List<Task> currentTasks = db.getTaskList();
            view.displayMenu(currentTasks);
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    view.showTitles(currentTasks);
                    int selectTask = scanner.nextInt();
                    scanner.nextLine();
                    break;
                case 3:
                    view.showTitles(currentTasks);
                    view.dispalyDeleteOptions();
                    int delChoice = scanner.nextInt();
                    scanner.nextLine(); // consume newline after reading int
                    deleteTask(currentTasks, delChoice);
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    /**
     * Prompts the user for task details and adds a new task to the database.
     * Collects title and description from user input.
     */
    private void addTask() {
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        Task task = new Task(title, description);
        db.addTask(task);
        System.out.println("Task added successfully!");
    }

    /**
     * Deletes a task from the task list based on user selection.
     * 
     * @param taskList the list of tasks to delete from
     * @param number the position number of the task to delete (1-based index)
     */
    private void deleteTask(List<Task> taskList, Integer number) {
        // Validate the task number is within valid range
        if (number < 1 || number > taskList.size()) {
            System.out.println("Invalid task number!");
            return;
        }

        Iterator<Task> it = taskList.iterator();
        int currentIndex = 1;

        // Find and remove the task at the specified position
        while (it.hasNext()) {
            it.next();
            if (currentIndex == number) {
                it.remove();
                System.out.println("Task deleted successfully!");
                return;
            }
            currentIndex++;
        }
    }
    private void changeTaskStatus(List<Task> taskList, Integer number) {
        // Validate the task number is within valid range
        if (number < 1 || number > taskList.size()) {
            System.out.println("Invalid task number!");
            return;
        }

        Iterator<Task> it = taskList.iterator();
        int currentIndex = 1;

        // Find and remove the task at the specified position
        while (it.hasNext()) {
           Task task = it.next();
            if (currentIndex == number) {
                // get task id
                UUID id = task.getId();
                //change task status
                task.setStatus();
                //update xml file
                it.remove();
                System.out.println("Task deleted successfully!");
                return;
            }
            currentIndex++;
        }
    }
}