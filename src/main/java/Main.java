import com.sapher.youtubedl.YoutubeDL;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
//        System.out.println("Select youtube-dl executable:");
        Scanner io = new Scanner(System.in);
        YoutubeDL.setExecutablePath("C:\\Users\\Artemis\\Downloads\\youtube-dl.exe");
        System.out.println("Enter video url:");
//        try {
//            createDir();
//        } catch (Exception e) {
//            e.printStackTrace(); //TODO GUI Error handling
//        }
//
//        SubtitleProcessor subs = new SubtitleProcessor(io.nextLine());
//        String data = "";
//        try {
//            subs.checkSubs();
//            System.out.println("Enter sub code: ");
//            String[] code = io.nextLine().split(" ");
//            System.out.println(Arrays.toString(code));
//            subs.getSub(Integer.parseInt(code[0]), Integer.parseInt(code[1]));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private static void createDir() throws IOException {
        Path dir = Files.createTempDirectory("youtube-tr");
    }
}
