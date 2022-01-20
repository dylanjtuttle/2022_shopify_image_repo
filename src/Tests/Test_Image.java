package Tests;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Date;

import Code.Image;

public class Test_Image {
    @Test
    public void Test_add_tag() {
        String tag = "test tag";
        ArrayList<String> tags = new ArrayList<>();
        tags.add(tag);
        Image test_image = new Image("filename", new Date(), tags);
        test_image.add_tag(tag);
        assertArrayEquals(tags.toArray(), test_image.get_tags().toArray());
    }

    @Test
    public void Test_has_tag_yes() {
        String tag = "test tag";
        ArrayList<String> tags = new ArrayList<>();
        tags.add(tag);
        Image test_image = new Image("filename", new Date(), tags);
        assertTrue("\"test tag\" should be in image", test_image.has_tag(tag));
    }

    @Test
    public void Test_has_tag_no() {
        String tag = "test tag";
        ArrayList<String> tags = new ArrayList<>();
        tags.add(tag);
        Image test_image = new Image("filename", new Date(), tags);
        assertFalse("\"test tag 2\" should not be in image", test_image.has_tag(tag + " 2"));
    }

    @Test
    public void Test_has_tag_empty() {
        String tag = "test tag";
        ArrayList<String> tags = new ArrayList<>();
        Image test_image = new Image("filename", new Date(), tags);
        assertFalse("\"test tag\" should not be in image", test_image.has_tag(tag));
    }
}
