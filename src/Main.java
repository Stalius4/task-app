

import controller.TaskController;
import model.Database;
public class Main {
    public static void main(String[] args) {
       TaskController controller = new TaskController();
       controller.run();

//        Database db = Database.getInstance();
//        db.getData();
    }
}
