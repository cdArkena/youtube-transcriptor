import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseSubtitle {

    ArrayList<LinkedText> transcript = new ArrayList<>();
    Pattern timestamp = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2})\\.(\\d{3}) --> ");

    /*
    Parse every .vtt file into Linkedtext in GUI
     */

     ParseSubtitle(File f, String videoId) {
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
                         String embedPrefix = "https://www.youtube.com/embed/%s?start=%s&showinfo=0&autoplay=1";
                         String uri = String.format(embedPrefix, videoId, time);
                         LinkedText lt = new LinkedText(br.readLine().strip(), uri);

                         transcript.add(lt);

                         System.out.print(lt.getText());
                         System.out.print(" - ");
                         System.out.println(lt.getUri());
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

    public ArrayList<LinkedText> getTranscript() {
        return transcript;
    }
}
