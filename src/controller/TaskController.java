package controller;

import model.Task;
import view.TaskView;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import model.Database;

public class TaskController {
    private final TaskView view;
    private final Scanner scanner;
    private final Database db;

    public TaskController() {
        db = Database.getInstance();
        db.getData();
        view = new TaskView();
        scanner = new Scanner(System.in);
    }

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
                    view.displayTasks(currentTasks);
                    break;
                case 3:
                    view.showTitles(currentTasks);
                    view.dispalyDeleteOptions();
                    int delChoice = scanner.nextInt();
                    scanner.nextLine();
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

    private void addTask() {
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        Task task = new Task(title, description);
        db.addTask(task);
        System.out.println("Task added successfully!");
    }


    private void deleteTask(List<Task> taskList, Integer number) {
        if (number < 1 || number > taskList.size()) {
            System.out.println("Invalid task number!");
            return;
        }

        Iterator<Task> it = taskList.iterator();
        int currentIndex = 1;

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

}