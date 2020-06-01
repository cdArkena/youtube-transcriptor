import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FindController implements Initializable {

    public ListView<LinkedText> transcript;
    @FXML
    public TextField findInput;
    @FXML
    public Label notFound;
    @FXML
    public Button searchButton;
    boolean mark;
    String key = "";
    Iterator<LinkedText> iterator;

    public void findText(ActionEvent event) {
        if (!(mark && findInput.getText().equals(key))) {
            key = findInput.getText();
            searchButton.setText("Selanjutnya");
            notFound.setVisible(false);
            resetIterate();
        }
        iterate();
    }

    public void iterate() {
        if (iterator.hasNext()) {
            mark = true;
            LinkedText text = iterator.next();

            while (!text.getText().toLowerCase().contains(key.toLowerCase())) {
                if (iterator.hasNext()) {
                    text = iterator.next();
                } else {
                    resetIterate();
                    notFound.setVisible(true);
                    mark = false;
                    searchButton.setText("Ulangi");
                    break;
                }
            }

            if (mark) {
                transcript.getSelectionModel().select(text);
                transcript.scrollTo(text);
                text.setUnderline(true);
            }
        }
    }

    public void resetIterate() {
        iterator = transcript.getItems().iterator();
        transcript.getItems().forEach(linkedText -> linkedText.setUnderline(false));
    }

    public void setTranscript(ListView<LinkedText> transcript) {
        this.transcript = transcript;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
