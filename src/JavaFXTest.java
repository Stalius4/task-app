import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXTest extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Create a simple label
        Label label = new Label("JavaFX is working for your Task Manager!");
        
        // Create a button
        Button button = new Button("Click me!");
        button.setOnAction(e -> label.setText("Button clicked! JavaFX is definitely working!"));
        
        // Create a layout container
        VBox root = new VBox(10); // 10px spacing
        root.getChildren().addAll(label, button);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");
        
        // Create the scene
        Scene scene = new Scene(root, 400, 200);
        
        // Set up the stage (window)
        primaryStage.setTitle("Task Manager - JavaFX Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
