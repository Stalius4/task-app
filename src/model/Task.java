package model;

import java.util.Date;
import java.util.UUID;


public class Task {

    private String title;
    private String description;
    private final Date date;
    private boolean isCompleted;
    private final UUID id;

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


    @Override
    public String toString(){
        return  "\n"+"----==Selected Task==----"+ "\n" + "Title: " + title + "\n" + "Description: " + description + "\n" +
                "Date: " + date.toString() + "\n" +
                "Completed: " + (isCompleted ? "Yes" : "No") + "\n" + "ID: " + id + "\n"+"----==|||||||||||||==----" + "\n";
    }

    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public Date getDate(){return date;}
    public boolean getStatus() {return isCompleted;}
    public UUID getId(){return id;}


    public void setStatus(){
        isCompleted =!isCompleted;
    }
    public void setTitle(String title){this.title = title;}
    public void setDescription(String description){this.description = description;}

}
