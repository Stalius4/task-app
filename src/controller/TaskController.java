package controller;

import model.JsonDatabase;
import model.Task;
import model.TaskDAO;
import view.TaskView;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


import model.XMLDatabase;


/**
 * Controller class that manages task operations and coordinates between the view and model.
 * Implements the MVC pattern by handling user input and updating the task data.
 */
public class TaskController {
    private final TaskView view;
    private final Scanner scanner;
    private final JsonDatabase db;
    private TaskDAO taskDAO;

    enum MenuOption {
        ADD_TASK(1),
        DISPLAY_TASKS(2),
        EXIT(3);

        private final int number;
        MenuOption(int number){
            this.number = number;
        }
        public int getNumber(){
            return number;
        }
    }
    enum EditOption {
        EDIT_TITLE(1),
        EDIT_DESCRIPTION(2),
        CHANGE_STATUS(3),
        DELETE(4),
        RETURN_BACK(5);

        private final int number;

        EditOption(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }
    /**
     * Initializes the TaskController with necessary dependencies.
     * Sets up the database connection, view, and input scanner.
     */
    public TaskController() {
        db = JsonDatabase.getInstance();
        db.getData();
        view = new TaskView();
        scanner = new Scanner(System.in);
        taskDAO = new TaskDAO();
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
    public void run() throws SQLException {
        boolean isRunning = true;
        while (isRunning) {
            List<Task> currentTasks = taskDAO.listAllTasks();
            view.displayMenu(currentTasks);
            // validateIntegerInput is handling non-int input error
            int firstUserInput = validateIntegerInput(1,3, "Option");

            // new feature for me (enum menu options for a switch statement)

            MenuOption selectedOption = null;
            for(MenuOption option : MenuOption.values()){
                if(option.getNumber() == firstUserInput){
                    selectedOption = option;
                    break;
                }
            }
            switch (selectedOption) {
                case ADD_TASK:
                    addTask();
                    break;
                case DISPLAY_TASKS:
                    //display all tasks and select 1 task to edit.
                    editTask(currentTasks);
                    break;
                case EXIT:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Main, Invalid option!");
            }
        }
    }

//------------------------Add task-------------------------------------------------------
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
                break;// exits loop if the title is not empty
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
        taskDAO.save(task);
        System.out.println("Task added successfully!");
        System.out.println(task);
    }

//------------------------Show and edit tasks ---------------------------------------
    private void editTask(List<Task> currentTasks) {
        if(currentTasks.isEmpty()){
            System.out.println("No tasks!");
            return;
        }
        view.allTitles(currentTasks);
        int selectedTask = validateIntegerInput(1, currentTasks.size(), "Task");//User selecting task with correct input checking
        Task task = currentTasks.get(selectedTask -1);
        view.displayTask(task);
        view.editOptions();
        int editOption = validateIntegerInput(1,5,"Option");

        EditOption userChoice = null;
        for(EditOption option : EditOption.values()){
            if(option.getNumber() == editOption){
                userChoice = option;
                break;
            }
        }
        //Edit tasks
        switch (userChoice){
            case EDIT_TITLE: // Edit title
                String newTitle;
                while(true){
                    System.out.print("Enter new title: ");
                    newTitle =scanner.nextLine().trim();
                    if(!newTitle.isEmpty()){
                       break;// if the new title input is not empty, exits while loop
                    }
                    System.out.println("Title can not be empty.");
                }
                taskDAO.editTitle(task, newTitle);
                break;


            case EDIT_DESCRIPTION: // Edit description
                String newDescription;
                while (true) {
                    System.out.print("Enter new description: ");
                    newDescription =scanner.nextLine().trim();
                    if(!newDescription.isEmpty()){
                        break;
                    }
                    System.out.println("Description can not be empty.");
                }
                taskDAO.changeDescription(task, newDescription);
                break;
            case CHANGE_STATUS: // Toggle status
                taskDAO.toggleTaskStatus(task);
                break;
            case DELETE: //Delete
                taskDAO.deleteTask(task);
                break;
            case RETURN_BACK:
               break;
            default:
                System.out.println("Second, Invalid option!");
        }

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
     * @param min is always 1
     * @param max depends on how many tasks or options to choose from.
     * @param menuPlace decides what message to print out  ( task or option)
     */
    private int validateIntegerInput(int min, int max, String menuPlace){
        boolean isValid = false;
        int userInput = -1; // default value before error handling

        while(!isValid){
            try {
                if(menuPlace.equals("Option")){
                    System.out.print("Choose an option: ");
                }
                if(menuPlace.equals("Task")){
                    System.out.print("Choose the task: ");
                }

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