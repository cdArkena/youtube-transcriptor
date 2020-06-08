import javafx.beans.DefaultProperty;

/**
 * Custom JavaFX component that allows text to have time and url property.
 */

@DefaultProperty("text")
public class LinkedText extends javafx.scene.text.Text{

    String embedPrefix = "https://www.youtube.com/embed/%s?&start=%s&autoplay=1&showinfo=0&controls=0&disablekb=1&rel=0";
    private String uri;
    private int time;

    LinkedText(String text, String videoId, int time) {
        super(text);
        this.uri = String.format(embedPrefix, videoId, time);
        this.time = time;
    }

    public String getUri() {
        return this.uri;
    }

    public int getTime() {
        return this.time;
    }
}
