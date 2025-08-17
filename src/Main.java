

import controller.TaskController;
import model.Database;

public class Main {
    public static void main(String[] args) {
//       TaskController controller = new TaskController();
//       controller.run();
        Database db = Database.getInstance();
        db.changeStatus("8975d81b-d18c-4c17-a918-0aa22cfb27eb");

    }
}
