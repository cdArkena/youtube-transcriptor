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

public class SpecificationController extends Controller implements Initializable {

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
            typeGen.setDisable(false);
            transcriptButton.setDisable(false);
            processFlag.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace(); //TODO: GUI Exception
        }
    }

    public void transcript(ActionEvent event) {
        if (typeGen.isSelected()) {
            // TODO: GenerateSub
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Transcript.fxml"));
                Parent root = loader.load();
                TranscriptController controller = loader.getController();
                if (controller != null) { //test
                    Stage stage = (Stage) idCC.getScene().getWindow();
                    stage.setScene(new Scene(root, 900, 600));
                    stage.setTitle("YouTube Transcript - v1.0.3a");
                    stage.show();
                    controller.loadWebView(buildEmbed(this.videoId));
                } else {
                    System.out.println("Exception"); // TODO GUI Exception
                }
            } catch (Exception e) {
                e.printStackTrace(); //TODO GUI Error handling
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
