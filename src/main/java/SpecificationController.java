import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SpecificationController extends Controller implements Initializable {

    public File transcriptFile;
    @FXML
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
    @FXML
    public CheckBox scroll;

    public void updateRadio() {
        try {
            idCC.setDisable(!subtitleProcessor.idCapt);
            idSubs.setDisable(!subtitleProcessor.idSubs);
            enCC.setDisable(!subtitleProcessor.enCapt);
            enSubs.setDisable(!subtitleProcessor.enSubs);
            scroll.setDisable(false);
            typeGen.setDisable(false);
            processFlag.setVisible(false);
            transcriptButton.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace(); //TODO: GUI Exception
        }
    }

    public void updateProcess() {
        try {
            processFlag.setVisible(true);
            idCC.setDisable(true);
            enCC.setDisable(true);
            idSubs.setDisable(true);
            enSubs.setDisable(true);
            scroll.setDisable(true);
            typeGen.setDisable(true);
            transcriptButton.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace(); //TODO: GUI Exception
        }
    }

    public void transcript(ActionEvent event) {
        updateProcess();
        if (typeGen.isSelected()) {
            // TODO: GenerateSub
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Transcript.fxml"));
                Parent root = loader.load();
                TranscriptController controller = loader.getController();
                if (controller != null) {
                    if (idSubs.isSelected()) subtitleProcessor.downSub(true, true);
                    if (idCC.isSelected()) subtitleProcessor.downSub(true, false);
                    if (enSubs.isSelected()) subtitleProcessor.downSub(false, true);
                    if (enCC.isSelected()) subtitleProcessor.downSub(false, false);
                    RadioButton selected = (RadioButton) toggleLang.getSelectedToggle();
                    switch (selected.getText()) {
                        case "Bahasa Indonesia - Subtitle":
                            transcriptFile = loadTranscript(true,true);
                            break;
                        case "Bahasa Indonesia - CC":
                            transcriptFile = loadTranscript(true, false);
                            break;
                        case "English - Subtitle":
                            transcriptFile = loadTranscript(false, true);
                            break;
                        case "English - CC":
                            transcriptFile = loadTranscript(false, false);
                            break;
                    }
                    Stage stage = (Stage) idCC.getScene().getWindow();
                    stage.setScene(new Scene(root, 900, 600));
                    stage.setTitle("YouTube Transcript - v1.0.6a");
                    if (scroll.isSelected()) controller.scroll = true;
                    controller.loadEngine();
                    controller.loadWebView(this.videoId, transcriptFile);
                    stage.show();
                } else {
                    System.out.println("Exception"); // TODO GUI Exception
                }
            } catch (Exception e) {
                e.printStackTrace(); //TODO GUI Error handling
            }
        }
    }

    public File loadTranscript(boolean lang, boolean type) {
        String typeString = (type) ? "sub" : "auto";
        String langString = (lang) ? "id" : "en";
        String fileName = String.format("%s.%s.%s.vtt", typeString, this.videoId, langString);
        return new File(dir.toFile(), fileName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
