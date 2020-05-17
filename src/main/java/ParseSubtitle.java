import java.io.File;
import java.util.ArrayList;

public class ParseSubtitle {

    ArrayList<LinkedText> transcript = new ArrayList<>();

    /*
    Parse every .vtt file into Linkedtext in GUI
     */
    ParseSubtitle(String path, boolean type, String id, boolean lang) { // should we keep id parameter?
        // We're going to parse the individual vtt here
        // TODO: How do we make in asynchronous?
        File dir = new File(path);
        String typeString = (type) ? "sub" : "auto";
        String langString = (lang) ? "id" : "en";
        String fileName = String.format("(%s)\\.(%s)\\.(%s)\\.vtt", typeString, id, langString);
        for (final File f : dir.listFiles()) {
            if (f != null && f.isFile()) {
                if (f.getName().matches(fileName)) {
                    readSubtitle(f);
                }
            }
        }
    }

    private void readSubtitle(File f) {

    }
}
