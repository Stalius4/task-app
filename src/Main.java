import controller.TaskController;
import database.DatabaseInitializer;
import database.DatabaseManager;
import model.Task;
import model.TaskDAO;

public class Main {
    /**
     * Application entry point. Obtains the singleton Database instance and toggles the status
     * of the record with ID "8975d81b-d18c-4c17-a918-0aa22cfb27eb".
     *
     * Note: the previously used TaskController startup is commented out; this main method
     * performs only the single database status change and then exits.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();
       // DatabaseManager.insertSimpleTask("new task");
        TaskController controller = new TaskController();
        controller.run();

        DatabaseManager.shutdown();


//        JacksonTest  test = new JacksonTest();
//        test.convertToJson(new Task("1", "desc"));
//        test.convertToJson(new Task("12", "desc"));

    }
}
