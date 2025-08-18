package view;

import model.Task;
import java.util.List;

public class TaskView {
    public void displayTask(Task task) {
        System.out.println(task.toString());
    }



    public void displayTasks(List<Task> tasks) {
        System.out.println("=== Tasks ===");
        for (Task task : tasks) {
            System.out.println("-----------------");
            displayTask(task);
        }
    }

    public void displayMenu(List<Task> tasks) {
        System.out.println("\n=== Task Manager ===");
        System.out.println("1. Add Task");

        if (tasks.isEmpty()) {
            System.out.println("No tasks to show.");
        } else {
            System.out.println("2. View All Tasks");
        }

        System.out.println("3. Delete task.");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    /**
     * Prints a numbered list of task titles to standard output, numbering items starting at 1.
     *
     * @param tasks the list of tasks whose titles will be printed; if the list is empty nothing is printed
     */
    public void showTitles(List<Task> tasks){
        Integer i = 0;
        for(Task task : tasks){
            i++;
            System.out.println(i + ". " + task.getTitle());
        }
    }

    /**
     * Prints a prompt asking the user to type the number of the task to delete.
     */
    public void dispalyDeleteOptions(){
        System.out.println("Please type number to delete a task.");
    }

    /**
     * Prints the task status action options to standard output.
     *
     * Outputs the two choices "1. Change status." and "2. Go back." separated by a newline.
     */
    public void displayStatusOptions(){
        System.out.print("1. Change status." + "\n" +"2. Go back.");
    }
}