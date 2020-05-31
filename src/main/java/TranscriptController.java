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
    public static Timer timer;
    @FXML
    public static LinkedText text;
    @FXML
    public static int iterateIndex = 0;
    @FXML
    static Engine engine;
    @FXML
    public boolean scroll;
    @FXML
    public ListView<LinkedText> transcript;
    @FXML
    public Label statusURI;
    @FXML
    public SplitPane splitPane;
    @FXML
    BrowserView view;
    @FXML
    private MenuBar menubar;
    @FXML
    private MenuItem changeLanguage;
    @FXML
    private Font x3;
    @FXML
    private Color x4;

    /**
     * Get the browser engine.
     * @return browser engine
     */
    public static Engine getEngine() {
        return engine;
    }

    /**
     * Load the browser engine into GUI.
     */
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
        splitPane.getItems().add(0, view);
    }

    /**
     * Load the video and the transcription into the GUI.
     * @param videoId the id of the video we about to load.
     * @param file subtitle file location
     */
    public void loadWebView(String videoId, File file) {
        this.videoId = videoId;
        timer = new Timer() {
            @Override
            protected void onTick() {
                if (text.getTime() == this.getElapsedTime()
                    || text.getTime() < this.getElapsedTime()) {
                    if (scroll) transcript.scrollTo(text); // this is slow
                    transcript.getSelectionModel().select(text);
                    transcript.getFocusModel().focus(iterateIndex);
                    iterateIndex++;
                }
                text = transcript.getItems().get(iterateIndex);
            }

            @Override
            protected void onFinish() {

            }
        };
        view.getBrowser().navigation().loadUrl(buildEmbed(videoId));
        statusURI.setText(videoId);
        new ParseSubtitle(file, videoId, transcript, view); //We can do this on different method
        text = transcript.getItems().get(iterateIndex);
        transcript.getSelectionModel().select(text);
        transcript.getFocusModel().focus(0);
        timeEvent();
    }

    /**
     * Load generated subtitle
     */
    public void loadGenerated() {
        //TODO
    }

    /**
     * Start/stop the clock sync.
     */
    public void timeEvent() { // TODO: How to detect if the browser is clicked?
        if (timer.isRunning()) {
            timer.pause();
        } else {
            timer.resume();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

//        List<Frame> frame1 = view.getBrowser().frames();
//        System.out.println(1);
//        for (Frame frame : frame1) {
//            System.out.println(2);
//            frame.document().ifPresent(document -> {
//                System.out.println(3);
//                document.addEventListener(EventType.CLICK, event -> {
//                    System.out.println(4);
//                }, false);
//            });
//        }