import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    protected String videoId = "";
    private Path dir;

    Controller(String uri) {
        try {
            createDir();
            if (uriValidation(uri)) {
                this.videoId = grabId(uri);
            } // TODO ELSE condition with GUI
        } catch (Exception e) {
            e.printStackTrace(); //TODO GUI Error handling
        }
    }

    private void createDir() throws IOException {
        dir = Files.createTempDirectory("youtube-tr-");
    }

    private void deleteDir() {
        File deldir = new File(dir.toUri());
        deldir.deleteOnExit(); // just delete???
    }

    public String getDir() {
        if (dir != null) return dir.toString();
        else return "";
    }

    public void changeYTDLPath(String path) { //TODO Validate, harus .exe (GUI Side)
        Path exePath = Paths.get(path);
        YoutubeDL.setExecutablePath(exePath.toString());
    }

    public boolean validateYTDLPath() throws YoutubeDLException { // TODO: kalo false, disable button, dilarang lanjut
        Pattern pattern = Pattern.compile("[0-9]{4}\\.[0-9]{2}\\.[0-9]{2}");
        Matcher matcher = pattern.matcher(YoutubeDL.getVersion());
        return matcher.find();
    }

    public void downloadYTDL() {
        /*
        Tadinya gw pengen user bisa download executable ytdl langsung dari app tapi keknya sulit.
         */
    }

    public boolean uriValidation(String uri) {
        Pattern pattern = Pattern.compile("^((?:https?:)?//)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be))(/(?:[\\w\\-]+\\?v=|embed/|v/)?)([\\w\\-]+)(\\S+)?$");
        Matcher matcher = pattern.matcher(uri);
        return matcher.find();
    }

    public String grabId(String url) {
        Pattern pattern = Pattern.compile("([0-9a-zA-Z]{11})");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) return matcher.group(1);
        else return "";
    }

    public void processSubtitle(String uri) {
        String prefix = "https://www.youtube.com/watch?v=";
        SubtitleProcessor subs = new SubtitleProcessor(prefix + videoId, dir);
        try {
            subs.checkSubs();
            subs.getAllSub();
        } catch (YoutubeDLException | IOException e) {
            e.printStackTrace();
        }
    }
}

