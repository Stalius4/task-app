package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.UUID;

public class Task {

    @JsonProperty("title")
    private String title;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final Date date;
    
    @JsonProperty("completed")
    private boolean isCompleted;
    
    @JsonProperty("id")
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
    @JsonCreator
    public Task(@JsonProperty("title") String title, 
                @JsonProperty("description") String description, 
                @JsonProperty("date") Date date, 
                @JsonProperty("completed") boolean isCompleted, 
                @JsonProperty("id") UUID id){
        this.title = title;
        this.description = description;
        this.date = date;
        this.isCompleted = isCompleted;
        this.id = id;
    }

    // Default constructor for Jackson
    public Task() {
        this.date = new Date();
        this.id = UUID.randomUUID();
        this.isCompleted = false;
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
    @JsonIgnore
    public boolean getStatus() {return isCompleted;}
    public UUID getId(){return id;}

    public void setStatus(){
        isCompleted =!isCompleted;

    }
    public void setTitle(String title){this.title = title;}
    public void setDescription(String description){this.description = description;}
}
