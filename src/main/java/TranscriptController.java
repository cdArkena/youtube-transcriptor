import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
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
    static Engine engine;
    @FXML
    public ListView<LinkedText> transcript;
    @FXML
    public Label statusURI;
    @FXML
    public SplitPane splitPane;
    public LinkedText text;
    public int iterateIndex = 0;
    public Iterator<LinkedText> iterate;
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
        splitPane.getItems().add(0, view);
    }

    public void loadWebView(String videoId, File file) {
        this.videoId = videoId;
        timer = new Timer() {
            @Override
            protected void onTick() {
                if (text.getTime() == this.getElapsedTime()) {
                    transcript.getSelectionModel().select(text);
                    transcript.getFocusModel().focus(iterateIndex);
                    if (iterate.hasNext()) {
                        iterateIndex++;
                        text = iterate.next();
                    }
                }
            }

            @Override
            protected void onFinish() {

            }
        };
        view.getBrowser().navigation().loadUrl(buildEmbed(videoId));
        statusURI.setText(videoId);
        new ParseSubtitle(file, videoId, transcript, view);
        iterate = transcript.getItems().iterator();
        text = iterate.next();
        transcript.getSelectionModel().select(text);
        transcript.getFocusModel().focus(0);
        timeEvent();
    }

    public void loadGenerated() {
        //TODO
    }

    public void timeEvent() {
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