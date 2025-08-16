package model;

import java.util.Date;
import java.lang.StringBuilder;


public class Task {

    private String title;
    private String description;
    private Date date;
    private boolean isCompleted;
    private Integer id;

    //this constructor will be used to create new tasks by user.
    public Task(String title, String description){
        this.title=title;
        this.description=description;
        date = new Date();
        isCompleted = false;
    }
    //this constructor will be used by Database class to  all tasks to arraylist form xml file when application starts.
    public Task( String title, String description, Date date, boolean isCompleted){
        this.title = title;
        this.description = description;
        this.date = date;
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Title: "+ title + "\n");
        sb.append("Description: " + description +"\n");
        sb.append("Date: " + date.toString() + "\n");
        sb.append("Compleded: " + (isCompleted ? "Yes" : "No"));
        String result =  sb.toString();
        return result;
    }
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public Date getDate(){return date;}
    public boolean getStatus() {return isCompleted;}
}
