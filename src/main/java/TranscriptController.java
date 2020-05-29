import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static java.lang.Integer.MAX_VALUE;

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
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TranscriptController extends Controller implements Initializable {

    @FXML
    public ListView<LinkedText> transcript;
    @FXML
    public Label statusURI;
    @FXML
    public AnchorPane webPane;
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

    public void loadWebView(String URI, RadioButton toggle) {
        this.videoId = URI;
        view.getBrowser().navigation().loadUrl(buildEmbed(URI));
        statusURI.setText(URI);
        switch (toggle.getText()) {
            case "Bahasa Indonesia - Subtitle":
                loadTranscript(true,true);
                break;
            case "Bahasa Indonesia - CC":
                loadTranscript(true, false);
                break;
            case "English - Subtitle":
                loadTranscript(false, true);
                break;
            case "English - CC":
                loadTranscript(false, false);
                break;
            case "Generate via GCloud":
                loadGenerated(); // TODO
        }
    }

    public void loadTranscript(boolean lang, boolean type) {
        String typeString = (type) ? "sub" : "auto";
        String langString = (lang) ? "id" : "en";
        String fileName = String.format("%s.%s.%s.vtt", typeString, this.videoId, langString);
        File file = new File(dir.toFile(), fileName);
        System.out.println(file.toString());
        new ParseSubtitle(file, this.videoId, transcript, view);
    }

    public void loadGenerated() {
        //TODO
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
