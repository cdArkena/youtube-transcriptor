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
            Parent uriErrorRoot = FXMLLoader.load(getClass().getResource("dialog_gui.fxml"));
            Scene uniError = new Scene(uriErrorRoot, 450, 150);
            primaryStage.setScene(uniError);
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
