import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FindController implements Initializable {

    public ListView<LinkedText> transcript;
    @FXML
    public TextField findInput;
    public Label notFound;
    boolean mark;
    String key;
    Iterator<LinkedText> iterator;

    public void findText(ActionEvent event) {
        System.out.println(1);
        if (!mark) {
            key = findInput.getText();
            iterator = transcript.getItems().iterator();
        }
        iterate();
    }

    public void iterate() {
        System.out.println(2);
        LinkedText text = iterator.next();
        while (!text.getText().contains(key)) {
            System.out.println(3);
            if (iterator.hasNext()) {
                text = iterator.next();
            } else {
                notFound.setVisible(true);
                mark = false;
                break;
            }
        }
        System.out.println(4);
        transcript.getSelectionModel().select(text);
        transcript.scrollTo(text);
        mark = true;
    }

    public void setTranscript(ListView<LinkedText> transcript) {
        this.transcript = transcript;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
