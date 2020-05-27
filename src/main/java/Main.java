import java.nio.file.Path;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage mainStage;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage) {
        try {
            mainStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("Dialog.fxml"));
            Scene scene = new Scene(root, 450, 150);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Masukkan URI");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        Path dir = GUIController.dir;
        if (dir != null) {
            GUIController.deleteDir();
        }
    }
}
