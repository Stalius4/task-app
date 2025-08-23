package controller;

import model.Task;
import view.TaskView;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


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
     * Run the main application loop: display the menu, read the user's numeric choice,
     * and dispatch to the appropriate controller actions until the user chooses to exit.
     *
     * <p>Menu choices handled:
     * <ul>
     *   <li>1 — prompt to add a new task (calls addTask())</li>
     *   <li>2 — show task titles and read a selection (selection currently unused)</li>
     *   <li>3 — show task titles, prompt delete options, and delete the selected task (calls deleteTask)</li>
     *   <li>4 — exit the loop and return from the method</li>
     * </ul>
     *
     * <p>The method reads numeric input from the controller's Scanner and updates
     * the Database-backed task list via the controller's helper methods. Invalid
     * numeric menu choices result in a printed "Invalid option!" message.
     */
    public void run() {
        boolean running = true;
        while (running) {
            List<Task> currentTasks = db.getTaskList();
            view.displayMenu(currentTasks);
            // correctIntCheck is handling non-int input error
            int firstUserInput = correctIntCheck(1,3);
            switch (firstUserInput) {
                // Add task
                case 1:
                    addTask();
                    break;
                //Show task titles
                case 2:
                    view.displayAllTitles(currentTasks);
                    System.out.println("Choose the task:");
                    int taskListSize = currentTasks.size();
                    int selectedTask = correctIntCheck(1, taskListSize);
                    Task task = currentTasks.get(selectedTask -1);
                    view.displayTask(task);
                    view.editOptions();
                    int selectedOption = scanner.nextInt();
                    scanner.nextLine();

                    switch (selectedOption){
                        case 1: // Edit title
                            System.out.print("Enter new title: ");
                            String editTitle = scanner.nextLine();
                            db.editTask(task, selectedOption, editTitle);
                            break;
                        case 2: // Edit description
                            System.out.print("Enter new description: ");
                            String editDescription = scanner.nextLine();
                            db.editTask(task, selectedOption, editDescription);
                            break;
                        case 3: // Toggle status
                            db.editTask(task, selectedOption, null); // newValue not needed for status toggle
                            break;
                        case 4: //Delete
                            db.editTask(task,selectedOption, null);
                            break;
                        default:
                            System.out.println("Second, Invalid option!");
                    }
                    break;

                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Main, Invalid option!");
            }
        }
    }

//------------------------Add task----------------------------------------------------------------------
    /**
     * Prompts the user for task details and adds a new task to the database.
     * Collects title and description from user input.
     */
    private void addTask() {
        System.out.print("Enter task title: ");
        String title;
        while (true){
            title = scanner.nextLine().trim();
            if(!title.isEmpty()){
                break;// exits loop if title is not empty
            }
            System.out.println("Task title can not be empty.");
            System.out.println("Enter task title: ");
        }

        String description;
        System.out.print("Enter task description: ");
        while(true){
            description = scanner.nextLine().trim();
            if(!description.isEmpty()){
                break;
            }
            System.out.println("Task description can not be empty.");
            System.out.print("Enter task description: ");
        }

        Task task = new Task(title, description);
        db.addTask(task);
        System.out.println("Task added successfully!");
        System.out.println(task);
    }


//-------------------------------------Delete-task------------------------------------
    /**
     * Removes the task at the given 1-based position from the provided task list.
     *
     * If the position is out of range, the list is left unchanged and "Invalid task number!" is printed.
     * On success the task is removed from the list and "Task deleted successfully!" is printed.
     *
     * @param taskList the list to remove the task from
     * @param number the 1-based index of the task to delete
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

    /**
     *
     */
    private int correctIntCheck(int min, int max){
        boolean isValid = false;
        int userInput = -1; // default value before error handling

        while(!isValid){
            try {
                System.out.print("Choose an option: ");
                userInput = scanner.nextInt();
                if (userInput < min || userInput > max) {
                    System.out.println("Please enter a number between " + min + " and " + max);
                    continue;  // ← THIS moves to the beginning of try block if number is out of range
                }
                isValid = true;
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Please enter a number between " + min + " and " + max);
            }
        }
        return userInput;
    }
}