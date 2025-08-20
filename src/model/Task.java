package model;

import java.util.Date;
import java.util.UUID;


public class Task {

    private String title;
    private String description;
    private Date date;
    private boolean isCompleted;
    private UUID id;

    //this constructor will be used to create new tasks by user.
    public Task(String title, String description){
        this.title=title;
        this.description=description;
        date = new Date();
        isCompleted = false;
        id = UUID.randomUUID();
    }
    //Database class will use this constructor to all tasks to arraylist form an XML file when the application starts.
    public Task( String title, String description, Date date, boolean isCompleted, UUID id){
        this.title = title;
        this.description = description;
        this.date = date;
        this.isCompleted = isCompleted;
        this.id = id;

    }

    /**
     * Returns a multi-line string representation of the task.
     *
     * <p>The returned string contains the title, description, date (using {@code Date#toString()}), 
     * completion status ("Yes" or "No"), and the UUID.</p>
     *
     * <p>Note: this method does not perform null checks; if {@code date} is null a
     * {@link NullPointerException} may be thrown when invoking {@code date.toString()}.</p>
     *
     * @return a multi-line human-readable representation of this task
     */
    @Override
    public String toString(){
        return "Title: " + title + "\n" + "Description: " + description + "\n" +
                "Date: " + date.toString() + "\n" +
                "Completed: " + (isCompleted ? "Yes" : "No") + "\n" + "ID: " + id;
    }

    /**
 * Returns the task's title.
 *
 * @return the title string
 */
    public String getTitle() {return title;}
    /**
 * Returns the task's description.
 *
 * @return the task description, or null if not set
 */
public String getDescription() {return description;}
    /**
 * Returns the task's date.
 *
 * The returned Date is the internal stored instance; modifying it will affect this Task.
 * If an independent copy is required, construct a new Date from the returned value.
 *
 * @return the task's date (may be null)
 */
public Date getDate(){return date;}
    /**
 * Returns whether the task is completed.
 *
 * @return true if the task is completed; false otherwise
 */
public boolean getStatus() {return isCompleted;}
    /**
 * Returns the task's unique identifier.
 *
 * @return the UUID assigned to this task
 */
public UUID getId(){return id;}

    /**
     * Toggle the task's completion status.
     *
     * Flips the internal `isCompleted` flag (true → false, false → true).
     */
    public void setStatus(){
        isCompleted =!isCompleted;
    }
    public void setTitle(String title){this.title = title;}
    public void setDescription(String description){this.description = description;}

}
