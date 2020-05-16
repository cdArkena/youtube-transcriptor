import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        subs.getAllSub();
    }

    private void createDir() throws IOException {
        dir = Files.createTempDirectory("youtube-tr-");
    }

    public String getDir() {
        if (dir != null) return dir.toString();
        else return null;
    }

}

