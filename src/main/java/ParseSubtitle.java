import com.teamdev.jxbrowser.view.javafx.BrowserView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class ParseSubtitle {

    Pattern timestamp = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2})\\.(\\d{3}) --> ");

    /**
    Parse every valid line file into Linkedtext in GUI
    */
    ParseSubtitle(File f, String videoId, ListView<LinkedText> transcript, BrowserView view) {
        if (f.exists()) {
            String line;
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                line = br.readLine();
                while (line != null) {
                    Matcher matcher = timestamp.matcher(line);
                    if (matcher.find()) {

                        /*
                         * Get the time value, instantiate LinkedText, add to ListView GUI
                         */

                        int time = Integer.parseInt(matcher.group(1)) * 360 + // This is very bad
                            Integer.parseInt(matcher.group(2)) * 60 +
                            Integer.parseInt(matcher.group(3)) +
                            ((Integer.parseInt(matcher.group(4)) > 500) ? 0 : 1);
                        String text = br.readLine();

                        if (!text.replaceAll("^\\s+", "").replaceAll("\\s+$", "").equals("")) {
                            LinkedText lt = new LinkedText(text, videoId, time);
                            lt.setStyle("-fx-font: 14 arial;");
                            lt.setOnMouseClicked(e -> {
                                view.getBrowser().navigation().loadUrl(lt.getUri());
                                TranscriptController.timer.setElapsedTime(time);
                                TranscriptController.text = lt;
                                TranscriptController.iterateIndex = transcript.getItems().indexOf(lt);
                            });

                            /*
                             * Check for duplication, use the most recent timestamp.
                             */

                            ObservableList<LinkedText> list = transcript.getItems();
                            if (list.size() != 0 && (list.get(list.size() - 1).getText().equals(lt.getText()))) {
                                list.remove(list.size() - 1);
                            }
                            list.add(lt);
                        }
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
