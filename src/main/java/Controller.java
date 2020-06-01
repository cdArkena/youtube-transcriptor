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

    public static Path dir;
    public TranscriptProcessor transcriptProcessor;
    protected String videoId = "";
    private Pattern validateUri = Pattern.compile("^((?:https?:)?//)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be))(/(?:[\\w\\-]+\\?v=|embed/|v/)?)([\\w\\-]+)(\\S+)?$");
    private Pattern getId = Pattern.compile("([0-9a-zA-Z-_]{11})");

    /**
     * Deletes temporary directory.
     */
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

    /**
     * Validates URL to accept only youtube links.
     * @param uri user input
     * @return true if matched youtube link
     */
    public boolean uriValidation(String uri) {
        Matcher matcher = validateUri.matcher(uri);
        return matcher.matches();
    }

    /**
     * Set the current videoId.
     * @param URI the URL of the video
     */
    public void setVideoId(String URI) {
        videoId = getId(URI);
    }

    /**
     * Get the ID of the video.
     * @param url the URL
     * @return youtube video ID
     */
    public String getId(String url) {
        Matcher matcher = getId.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "-";
        }
    }

    /**
     * Build an embed link for the video.
     * @param videoId the ID of the video
     * @return embedded youtube URI
     */
    public String buildEmbed(String videoId) {
        String prefix = "https://www.youtube.com/embed/%s?&autoplay=1&showinfo=0&controls=0&disablekb=1&rel=0";
        return String.format(prefix, videoId);
    }

    /**
     * Create temporary directory as workspace.
     * @throws IOException if the creation failed
     */
    public void createDir() throws IOException {
        dir = Files.createTempDirectory("youtube-tr-");
    }

    /**
     * Change the Youtube-DL dependency location
     * @param path /path/to/youtube-dl.exe
     */
    public void changeYTDLPath(String path) { //TODO Validate, harus .exe (GUI Side)
        Path exePath = Paths.get(path);
        YoutubeDL.setExecutablePath(exePath.toString());
    }

    /**
     * Check available text in the current video on the temporary workspace.
     */
    public void processSubtitle() {
        this.transcriptProcessor = new TranscriptProcessor(videoId, dir);
        try {
            this.transcriptProcessor.checkSubs();
        } catch (YoutubeDLException | IOException e) {
            e.printStackTrace();
        }
    }
}