package Code;
import java.util.Date;
import java.util.ArrayList;

public class Image {
    public String filename;
    Date month;
    ArrayList<String> tags;

    public Image(String new_filename) {
        this.filename = new_filename;
    }

    public Image(String new_filename, Date month, ArrayList<String> new_tags) {
        this.filename = new_filename;
        this.month = month;
        this.tags = new_tags;
    }

    public String get_filename() {
        return this.filename;
    }

    public Date get_month() {
        return this.month;
    }

    public ArrayList<String> get_tags() {
        return this.tags;
    }

    public void add_tag(String tag) {
        this.tags.add(tag);
    }

    public boolean has_tag(String tag) {
        return this.tags.contains(tag);
    }
}
