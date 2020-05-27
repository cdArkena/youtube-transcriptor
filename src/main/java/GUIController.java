import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class GUIController extends Controller implements Initializable {

    public Label processFlag;
    @FXML
    public RadioButton idCC;
    @FXML
    public ToggleGroup toggleLang;
    @FXML
    public RadioButton idSubs;
    @FXML
    public RadioButton enCC;
    @FXML
    public RadioButton enSubs;
    @FXML
    public RadioButton typeGen;
    @FXML
    public Button transcriptButton;
    @FXML
    public Label warnLabel;

    public void updateRadio() {
        try {
            idCC.setDisable(!subtitleProcessor.idCapt);
            idSubs.setDisable(!subtitleProcessor.idSubs);
            enCC.setDisable(!subtitleProcessor.enCapt);
            enSubs.setDisable(!subtitleProcessor.enSubs);
            processFlag.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace(); //TODO: GUI Exception
        }
    }

    public void dialogAction(ActionEvent event) {
        warnLabel.setVisible(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Transcript.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            if (true) { //test
                Stage stage = (Stage) idCC.getScene().getWindow();
                stage.setScene(new Scene(root, 900,600));
                stage.setTitle("YouTube Transcript");
                stage.show();
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
