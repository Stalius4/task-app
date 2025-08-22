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

        System.out.println("3. Exit");

    }

    /**
     * Prints a numbered list of task titles to standard output, numbering items starting at 1.
     *
     * @param tasks the list of tasks whose titles will be printed; if the list is empty nothing is printed
     */
    public void displayAllTitles(List<Task> tasks){
        Integer i = 0;
        for(Task task : tasks){
            i++;
            System.out.println(i + ". " + task.getTitle());
        }
    }





    public void editOptions(){
        System.out.println("""
                1. Edit title
                2. Edit description
                3. Make it completed
                4. Delete""");
    }
}