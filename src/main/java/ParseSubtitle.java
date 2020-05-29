import com.teamdev.jxbrowser.view.javafx.BrowserView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.ListView;

    /*
    Parse every .vtt file into Linkedtext in GUI
     */

public class ParseSubtitle {

    Pattern timestamp = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2})\\.(\\d{3}) --> ");

     ParseSubtitle(File f, String videoId, ListView<LinkedText> transcript,
         BrowserView view) {
         if (f.exists()) {
             String line;
             try {
                 BufferedReader br = new BufferedReader(new FileReader(f));
                 line = br.readLine();
                 while (line != null) {
                     Matcher matcher = timestamp.matcher(line);
                     if (matcher.find()) {
                         int time = Integer.parseInt(matcher.group(1)) * 360 + // This is very bad
                             Integer.parseInt(matcher.group(2)) * 60 +
                             Integer.parseInt(matcher.group(3)) +
                             ((Integer.parseInt(matcher.group(4)) > 500) ? 0 : 1);
                         String embedPrefix = "https://www.youtube.com/embed/%s?&start=%s&autoplay=1&showinfo=0&controls=0&disablekb=1&rel=0";
                         String uri = String.format(embedPrefix, videoId, time);
                         LinkedText lt = new LinkedText(br.readLine().strip(), uri);
                         lt.setOnMouseClicked(e -> {
                            view.getBrowser().navigation().loadUrl(lt.getUri());
                         });
                         transcript.getItems().add(lt);
                     }
                     line = br.readLine();
                 }
                 br.close();
             } catch (IOException e) {
                 e.printStackTrace(); // TODO GUI Error Handling
             }
         } else {
             System.out.println("file doesn't exist"); // TODO GUI
         }
    }
}
