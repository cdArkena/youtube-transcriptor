import static com.teamdev.jxbrowser.os.Environment.isMac;

import com.teamdev.jxbrowser.engine.Engine;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Collections;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Entry extends Application {

    static Stage mainStage;

    public static void main(String[] args) {
        System.setProperty("jxbrowser.license.key",
            "1BNDHFSC1FVM5M44WOBXTBJ7U0GQJQSC3SKQUD77RX06U1J12FZGN5L8YE39N66ASLVI6X");
        launch();
    }

    public void start(Stage primaryStage) {
        try {
            mainStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("Dialog.fxml"));
            Scene scene = new Scene(root, 450, 150);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Masukkan URL Youtube");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        Path dir = SpecificationController.dir;
        if (dir != null) {
            SpecificationController.deleteDir();
        }
        Engine engine = TranscriptController.getEngine();
        if (engine != null) {
            if (isMac()) {
                // On macOS the engine must be closed in UI thread
                engine.close();
            } else {
                // On Windows and Linux it must be closed in non-UI thread
                new Thread(engine::close).start();
            }
        }
        TranscriptController.timer.cancel();
        System.exit(0);
    }
}
