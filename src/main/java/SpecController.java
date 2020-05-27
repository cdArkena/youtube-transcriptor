import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class SpecController extends Controller implements Initializable {

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

    public SpecController() {

    }

    public void updateRadio() {
        try {
            idCC.setDisable(!subtitleProcessor.idCapt);
            idSubs.setDisable(!subtitleProcessor.idSubs);
            enCC.setDisable(!subtitleProcessor.enCapt);
            enSubs.setDisable(!subtitleProcessor.enSubs);
        } catch (Exception e) {
            e.printStackTrace(); //TODO: GUI Exception
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
