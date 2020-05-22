import javafx.beans.DefaultProperty;

@DefaultProperty("text")
public class LinkedText extends javafx.scene.text.Text{

    private String uri;

    LinkedText(String text, String uri) {
        super(text);
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
