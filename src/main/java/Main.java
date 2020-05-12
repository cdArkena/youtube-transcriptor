import com.sapher.youtubedl.YoutubeDL;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
//        System.out.println("Select youtube-dl executable:");
        Scanner io = new Scanner(System.in);
        YoutubeDL.setExecutablePath("C:\\Users\\Artemis\\Downloads\\youtube-dl.exe");
        System.out.println("Enter video url:");
        SubtitleProcessor subs = new SubtitleProcessor(io.nextLine());
        String data = "";
        try {
            subs.checkSubs();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(subs.enCapt);
        System.out.println(subs.idCapt);
        System.out.println(subs.enSubs);
        System.out.println(subs.idSubs);
    }
}
