import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DialogController implements Initializable {

    @FXML
    public TextField uriInput;
    @FXML
    public Label warnLabel;

    /**
     * Show the specification dialog after user input the URI
     */
    public void dialogAction() {
        String uri = uriInput.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Specification.fxml"));
            Parent root = loader.load();
            SpecificationController c = loader.getController();
            c.changeYTDLPath(getClass().getClassLoader().getResource("lib/youtube-dl.exe").getPath().substring(1));

            /*
             * Check the URL validity
             */

            if (c.uriValidation(uri)) {
                Stage stage = (Stage) uriInput.getScene().getWindow();
                stage.setScene(new Scene(root, 450, 200));
                stage.setTitle("Pilih spesifikasi");
                stage.show();
                c.createDir();
                c.setVideoId(uri);
                c.processSubtitle();
                c.updateRadio();
            } else {
                warnLabel.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace(); //TODO GUI Error handling
            warnLabel.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
