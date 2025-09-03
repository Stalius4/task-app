package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static database.DatabaseManager.getConnection;

public class TaskDAO {

    public void save(Task task){
        String insertSQL = "INSERT INTO tasks (id, title,description, created_date, is_completed) VALUES (?, ?, ?, CURRENT_DATE, FALSE)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, UUID.randomUUID().toString());
            pstmt.setString(2, task.getTitle());
            pstmt.setString(3, task.getDescription());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✓ Task inserted successfully: " + task.getTitle());
            }

        } catch (SQLException e) {
            System.err.println("Error inserting task: " + e.getMessage());
        }
    }

    public List<Task> listAllTasks(){
        List<Task> taskList = new ArrayList<>();
        String selectAllSQL = "SELECT id, title, description, created_date, is_completed FROM tasks";
      try(Connection conn = getConnection();
          PreparedStatement pstmt = conn.prepareStatement(selectAllSQL);)
      {
          ResultSet rs = pstmt.executeQuery();
          while(rs.next()){
              String id = rs.getString("id");
              String title  = rs.getString("title");
              String description = rs.getString("description");
              Date date = rs.getDate("created_date");
              boolean isCompleted = rs.getBoolean("is_completed");
              Task task  = new Task(title,description,date,isCompleted,UUID.fromString(id)
              );
              taskList.add(task);

          }
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      return taskList;
    }
    public void editTitle(Task task, String newValue){
        String sql = "UPDATE tasks SET title = ? WHERE id = ?";

        try(Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql) ) {
            pstmt.setString(1, newValue);
            pstmt.setString(2, task.getId().toString());
            int rs = pstmt.executeUpdate();
            if(rs > 0){
                System.out.println("Updated title");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 1. SQL query (select by name? or id? )
        // 2. connection in try(){}
        // 3. do I get result first and then modify?
        // 4. switch statement to decide what to edit
        // 5. should I create different methods to edit title, description etc.. or keep one method
    }



}
