import javafx.beans.DefaultProperty;

@DefaultProperty("text")
public class LinkedText extends javafx.scene.text.Text{

    String uri = "";

    LinkedText(String text, String uri) {
        super(text);
        this.uri = uri;
    }
}
