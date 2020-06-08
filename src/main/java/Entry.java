import static com.teamdev.jxbrowser.os.Environment.isMac;

import com.teamdev.jxbrowser.engine.Engine;
import java.nio.file.Path;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Entry extends Application {

    static Stage mainStage;

    /**
     * JavaFX Launch point,
     */

    public static void run() {
        System.setProperty("jxbrowser.license.key",
            "1BNDHFSC1FVM5M44WOBXTBJ7U0GQJQSC3SKQUD77RX06U1J12FZGN5L8YE39N66ASLVI6X");
        launch();
    }

    /**
     * JavaFX starting sequence.
     * @param primaryStage current stage
     */

    public void start(Stage primaryStage) {
        try {
            mainStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Dialog.fxml"));
            Scene scene = new Scene(root, 450, 150);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Masukkan URL Youtube");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * JavaFX closing sequence.
     */

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
        Platform.exit();
        System.exit(0);
    }
}
