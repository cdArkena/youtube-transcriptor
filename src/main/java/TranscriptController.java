import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class TranscriptController extends Controller implements Initializable {

    @FXML
    public ListView<LinkedText> transcript;
    @FXML
    public Label statusURI;
    //    @FXML
    //    public WebView player;
    @FXML
    public AnchorPane webPane;
    BrowserView view;

    public void loadWebView(String URI) {
        view.getBrowser().navigation().loadUrl(URI);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EngineOptions options = EngineOptions.newBuilder(HARDWARE_ACCELERATED).build();
        Engine engine = Engine.newInstance(options);

// Create the Browser
        Browser browser = engine.newBrowser();

// Create the JavaFX BrowserView component
        view = BrowserView.newInstance(browser);
        view.setLayoutX(121);
        view.setLayoutY(139);
        view.maxHeight(-10);
        view.minHeight(400);
        view.minWidth(600);
        view.prefHeight(400);
        view.prefWidth(600);
        webPane.getChildren().add(view);
    }
}
