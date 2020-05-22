import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        Controller c = new Controller("https://www.youtube.com/watch?v=QImCld9YubE");
        c.changeYTDLPath("C:\\Users\\Artemis\\Downloads\\youtube-dl.exe");
        try {
            System.out.println(1);
            System.out.println(c.validateYTDLPath());
            if (c.validateYTDLPath()) {
                System.out.println(c.getDir());
                SubtitleProcessor sp = c.processSubtitle(true,true);
                System.out.println(4);
                // Don't put it on array first, just add the text on-the-fly
                // The array is just for storage only
//                for (LinkedText t : sp.parseFile(true,true)) {
//                    System.out.print(t.getText());
//                    System.out.print(" ");
//                    System.out.println(t.getUri());
//                }
                sp.parseFile(true,true);
                System.out.println(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
