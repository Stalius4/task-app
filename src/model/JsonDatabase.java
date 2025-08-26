package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonDatabase {
    private static JsonDatabase instance;
    private final ObjectMapper objectMapper;
    private final String JSON_FILE_PATH = "tasks.json";
    private List<Task> taskList;

    private JsonDatabase() {
        // Configure ObjectMapper
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty print JSON
        
        // Initialize task list
        taskList = new ArrayList<>();
        
        // Load data when database is created
        loadTasksFromJson();
    }

    /**
     * Singleton pattern - get the single instance of JsonDatabase
     */
    public static JsonDatabase getInstance() {
        if (instance == null) {
            instance = new JsonDatabase();
        }
        return instance;
    }

    /**
     * Load tasks from JSON file into memory
     */
    private void loadTasksFromJson() {
        File jsonFile = new File(JSON_FILE_PATH);
        
        if (!jsonFile.exists()) {
            System.out.println("JSON file not found. Starting with empty task list.");
            return;
        }

        try {
            // Read tasks from JSON file using TypeReference for List<Task>
            TypeReference<List<Task>> typeRef = new TypeReference<List<Task>>() {};
            taskList = objectMapper.readValue(jsonFile, typeRef);
            System.out.println("Loaded " + taskList.size() + " tasks from JSON file.");
        } catch (IOException e) {
            System.out.println("Error loading tasks from JSON: " + e.getMessage());
            taskList = new ArrayList<>(); // Start with empty list if loading fails
        }
    }

    /**
     * Save all tasks to JSON file
     */
    public void saveTasksToJson() {
        try {
            File jsonFile = new File(JSON_FILE_PATH);
        
            // Ensure parent directory exists
            File parentDir = jsonFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
        
            // Write the JSON file
            objectMapper.writeValue(jsonFile, taskList);
        
            // Verify the file was created
            if (jsonFile.exists()) {
                System.out.println("✓ Tasks saved to JSON file: " + jsonFile.getAbsolutePath());
                System.out.println("✓ File size: " + jsonFile.length() + " bytes");
            } else {
                System.out.println("✗ Failed to create JSON file");
            }
        
        } catch (IOException e) {
            System.out.println("✗ Error saving tasks to JSON: " + e.getMessage());
            e.printStackTrace(); // This will show the full stack trace
        } catch (Exception e) {
            System.out.println("✗ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Add a new task and save to file
     */
    public void addTask(Task task) {
        System.out.println("Adding task: " + task.getTitle());
        taskList.add(task);
        System.out.println("Task list size: " + taskList.size());
        saveTasksToJson(); // Auto-save after adding
    }

    /**
     * Get all tasks
     */
    public List<Task> getTaskList() {
        return new ArrayList<>(taskList); // Return copy to prevent external modification
    }

    /**
     * Edit task and save changes
     */
    public void editTask(Task task, int editOption, String newValue) {
        switch (editOption) {
            case 1: // Edit title
                task.setTitle(newValue);
                break;
            case 2: // Edit description
                task.setDescription(newValue);
                break;
            case 3: // Toggle status
                task.setStatus();
                break;
            case 4: // Delete task
                taskList.remove(task);
                break;
        }
        saveTasksToJson(); // Auto-save after editing
        
        if (editOption == 4) {
            System.out.println("Task deleted successfully!");
        } else {
            System.out.println("Task updated successfully!");
        }
    }

    /**
     * Find task by ID
     */
    public Task findTaskById(UUID id) {
        return taskList.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get data method for compatibility with existing code
     */
    public void getData() {
        // This method exists for compatibility with XMLDatabase
        // Data is already loaded in constructor
        System.out.println("JSON Database initialized with " + taskList.size() + " tasks.");
    }
}
