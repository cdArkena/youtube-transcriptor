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

    public SubtitleProcessor subtitleProcessor;
    protected static Path dir;
    protected String videoId = "";
    private Pattern validateYTDL = Pattern.compile("[0-9]{4}\\.[0-9]{2}\\.[0-9]{2}\\s+");
    private Pattern validateUri = Pattern.compile(
        "^((?:https?:)?//)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be))(/(?:[\\w\\-]+\\?v=|embed/|v/)?)([\\w\\-]+)(\\S+)?$");
    private Pattern getId = Pattern.compile("([0-9a-zA-Z]{11})");

    public static void deleteDir() {
        String[] fs = dir.toFile().list();
        if (fs != null) {
            if (fs.length > 0) {
                for (String s : fs) {
                    File f = new File(dir.toString(), s);
                    f.delete();
                }
            }
        }
        dir.toFile().delete();
    }

    public boolean uriValidation(String uri) {
        Matcher matcher = validateUri.matcher(uri);
        return matcher.matches();
    }

    public void setVideoId(String videoId) {
        this.videoId = getId(videoId);
    }

    public String getId(String url) {
        Matcher matcher = getId.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    public String buildEmbed(String videoId) {
        String prefix = "https://www.youtube.com/embed/%s?&autoplay=1&showinfo=0&controls=0&disablekb=1&rel=0";
        return String.format(prefix, videoId);
    }

    public void createDir() throws IOException {
        dir = Files.createTempDirectory("youtube-tr-");
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

    public void processSubtitle() {
        this.subtitleProcessor = new SubtitleProcessor(videoId, dir);
        try {
            this.subtitleProcessor.checkSubs();
        } catch (YoutubeDLException | IOException e) {
            e.printStackTrace();
        }
    }
}