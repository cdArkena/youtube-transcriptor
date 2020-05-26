import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Button;

public class Controller implements Initializable {

    public javafx.scene.control.TextField uriInput;
    public Button button;
    protected String videoId = "";
    private Pattern validateYTDL = Pattern.compile("[0-9]{4}\\.[0-9]{2}\\.[0-9]{2}\\s+");
    private Pattern validateUri = Pattern.compile("^((?:https?:)?//)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be))(/(?:[\\w\\-]+\\?v=|embed/|v/)?)([\\w\\-]+)(\\S+)?$");
    private Pattern getId = Pattern.compile("([0-9a-zA-Z]{11})");
    private Path dir;

//    Controller(String uri) {
//        try {
//            createDir();
//            if (uriValidation(uri)) {
//                this.videoId = grabId(uri);
//            } else {
//                // TODO ELSE condition with GUI
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); //TODO GUI Error handling
//        }
//    }
    @FXML
    public void action(ActionEvent event) {
        String uri = uriInput.getText();
        System.out.println(uri);
        try {
            createDir();
            if (uriValidation(uri)) {
                videoId = grabId(uri);
            } else {
                // TODO ELSE condition with GUI
            }
        } catch (Exception e) {
            e.printStackTrace(); //TODO GUI Error handling
        }
    }

    private void createDir() throws IOException {
        dir = Files.createTempDirectory("youtube-tr-");
    }

    public void deleteDir() {
        String[] fs = dir.toFile().list();
        if (fs != null) {
            if (fs.length > 0) {
                for (String s : fs) {
                    File f = new File(dir.toString(),s);
                    f.delete();
                }
            }
        }
        dir.toFile().delete();
    }

    public String getDir() {
        if (dir != null) {
            return dir.toString();
        } else {
            return "";
        }
    }

    public void changeYTDLPath(String path) { //TODO Validate, harus .exe (GUI Side)
        Path exePath = Paths.get(path);
        YoutubeDL.setExecutablePath(exePath.toString());
    }

    public boolean validateYTDLPath()
        throws YoutubeDLException { // TODO: kalo false, disable button, dilarang lanjut
        Matcher matcher = validateYTDL.matcher(YoutubeDL.getVersion());
        return matcher.matches();
    }

    public void downloadYTDL() {
        /*
        Tadinya gw pengen user bisa download executable ytdl langsung dari app tapi keknya sulit.
         */
    }

    public boolean uriValidation(String uri) {
        Matcher matcher = validateUri.matcher(uri);
        return matcher.matches();
    }

    public String grabId(String url) {
        Matcher matcher = getId.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    public SubtitleProcessor processSubtitle() {
        SubtitleProcessor subs = new SubtitleProcessor(videoId, dir);
        try { // TODO: This is why it took 20 seconds. We can do better
            subs.checkSubs();
            subs.downAllSub();
        } catch (YoutubeDLException | IOException e) {
            e.printStackTrace();
        }
        return subs;
    }

    public SubtitleProcessor processSubtitle(boolean lang, boolean type) {
        SubtitleProcessor subs = new SubtitleProcessor(videoId, dir);
        try {
            subs.checkSubs();
            subs.downSub(lang, type); // just don't download everything at once
        } catch (YoutubeDLException | IOException e) {
            e.printStackTrace();
        }
        return subs;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

