import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TranscriptController extends Controller implements Initializable {

    @FXML
    public ListView<LinkedText> transcript;
    @FXML
    public Label statusURI;
    @FXML
    public SplitPane splitPane;
    @FXML
    BrowserView view;
    @FXML
    static Engine engine;
    @FXML
    private MenuBar menubar;
    @FXML
    private MenuItem changeLanguage;
    @FXML
    private Font x3;
    @FXML
    private Color x4;
    @FXML
    private Timer timer;

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
        view.minHeight(400);
        view.minWidth(600);
        view.prefHeight(400);
        view.prefWidth(600);
        view.setPickOnBounds(true);
        splitPane.getItems().add(0,view);
    }

    public void loadWebView(String videoId, File file) {
        this.videoId = videoId;
        this.timer = new Timer() {
            @Override
            protected void onTick() {

            }

            @Override
            protected void onFinish() {

            }
        };
        view.getBrowser().navigation().loadUrl(buildEmbed(videoId));
        statusURI.setText(videoId);
        new ParseSubtitle(file, videoId, transcript, view);
    }

    public void loadGenerated() {
        //TODO
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
