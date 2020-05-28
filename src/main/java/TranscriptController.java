import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class TranscriptController extends Controller implements Initializable {

    @FXML
    public WebEngine webEngine;
    @FXML
    public WebView video;
    @FXML
    public ListView<LinkedText> transcript;

    public void loadWebView(String URI) {
        webEngine.load(URI);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = video.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> observableValue, State state,
                State t1) {
                if (state == State.SUCCEEDED) {
                    System.out.println(webEngine.getLocation());
                }
            }
        });
    }
}
