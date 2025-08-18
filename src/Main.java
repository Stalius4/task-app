import controller.TaskController;
import model.Database;

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
//       TaskController controller = new TaskController();
//       controller.run();
        Database db = Database.getInstance();
        db.changeStatus("8975d81b-d18c-4c17-a918-0aa22cfb27eb");

    }
}
