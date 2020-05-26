import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public void start(Stage primaryStage) throws Exception {
        Parent uriErrorRoot = FXMLLoader.load(getClass().getResource("error_uri_gui.fxml"));
        Scene uniError = new Scene(uriErrorRoot);

        primaryStage.setTitle("Error");
        primaryStage.setScene(uniError);
        primaryStage.show();
    }

    public static void main(String[] args){
        System.out.println("Select youtube-dl executable:");
        Scanner io = new Scanner(System.in);
        YoutubeDL.setExecutablePath("C:\\Users\\Artemis\\Desktop\\Desktop File\\logisim-win-2.7.1.exe");
        System.out.println(YoutubeDL.getVersion());
        try {
            createDir();
        } catch (Exception e) {
            e.printStackTrace(); //TODO GUI Error handling
        }

        SubtitleProcessor subs = new SubtitleProcessor(io.nextLine());
        String data = "";
        try {
            subs.checkSubs();
            System.out.println("Enter sub code: ");
            String[] code = io.nextLine().split(" ");
            System.out.println(Arrays.toString(code));
            subs.getSub(Integer.parseInt(code[0]), Integer.parseInt(code[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
