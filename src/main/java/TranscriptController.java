import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static java.lang.Integer.MAX_VALUE;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import java.lang.annotation.Inherited;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;

public class TranscriptController extends Controller implements Initializable {

    @FXML
    public ListView<LinkedText> transcript;
    @FXML
    public Label statusURI;
    @FXML
    public WebView player;
    @FXML
    public AnchorPane webPane;
    @FXML
    BrowserView view;
    @FXML
    static Engine engine;
    @FXML
    private MenuBar menubar;
    @FXML
    private WebView video;
    @FXML
    private ListView<?> transcript;
    @FXML
    private MenuItem changeLanguage;
    @FXML
    private Font x3;
    @FXML
    private Color x4;

    public static Engine getEngine() {
        return engine;
    }

    public void loadEngine() {
        EngineOptions options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
            .licenseKey("1BNDHFSC1FVM5M44WOBXTBJ7U0GQJQSC3SKQUD77RX06U1J12FZGN5L8YE39N66ASLVI6X")
            .build();
        engine = Engine.newInstance(options);
        Browser browser = engine.newBrowser();
        view = BrowserView.newInstance(browser);
        view.setLayoutX(121);
        view.setLayoutY(139);
        view.maxWidth(MAX_VALUE);
        view.maxHeight(Region.USE_COMPUTED_SIZE);
        view.minHeight(400);
        view.minWidth(600);
        view.prefHeight(400);
        view.prefWidth(600);
        view.setPickOnBounds(true);
        webPane.getChildren().add(view);
    }

    public void loadWebView(String URI) {
        view.getBrowser().navigation().loadUrl(URI);
    }

    public void loadTranscript(boolean lang, boolean type) {
        ObservableList<LinkedText> transcriptList = subtitleProcessor.parseFile(lang, type);
        ListView<LinkedText> root = new ListView<LinkedText>(transcriptList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
