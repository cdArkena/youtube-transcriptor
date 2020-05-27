import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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

    public void dialogAction(ActionEvent event) {
        String uri = uriInput.getText();
        warnLabel.setVisible(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("specification_gui.fxml"));
            Parent root = loader.load();
            SpecController controller = loader.getController();
            if (controller.uriValidation(uri)) {
                Stage stage = (Stage) uriInput.getScene().getWindow();
                stage.setScene(new Scene(root, 450,200));
                stage.show();
                controller.createDir();
                controller.videoId = controller.grabId(uri);
                controller.processSubtitle();
                controller.updateRadio();
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
