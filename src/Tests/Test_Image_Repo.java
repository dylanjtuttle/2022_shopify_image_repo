package Tests;
import static org.junit.Assert.*;
import Code.*;
import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Test_Image_Repo {
    @Test
    public void Test_image_add_no_tags() {
        Date jan_1970 = new Date(1640995200);
        ArrayList<String> tags = new ArrayList<>();
        HashMap<String, ArrayList<Image>> user_images = new HashMap<>();

        Image test_image = new Image("filename", jan_1970, tags);
        ArrayList<Image> test_array = new ArrayList<>();
        test_array.add(test_image);

        HashMap<String, ArrayList<Image>> test_map = new HashMap<>();
        test_map.put("01-1970", test_array);
        HashMap<String, ArrayList<Image>> compare_map = Image_Repo.image_add(user_images, test_image);
        assertEquals(compare_map, test_map);
    }

    @Test
    public void Test_image_add_one_tag() {
        Date jan_1970 = new Date(1640995200);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("test tag");
        HashMap<String, ArrayList<Image>> user_images = new HashMap<>();

        Image test_image = new Image("filename", jan_1970, tags);
        ArrayList<Image> test_array = new ArrayList<>();
        test_array.add(test_image);

        HashMap<String, ArrayList<Image>> test_map = new HashMap<>();
        test_map.put("01-1970", test_array);
        test_map.put("test tag", test_array);
        HashMap<String, ArrayList<Image>> compare_map = Image_Repo.image_add(user_images, test_image);
        assertEquals(compare_map, test_map);
    }

    @Test
    public void Test_image_add_two_tags() {
        Date jan_1970 = new Date(1640995200);
        ArrayList<String> tags = new ArrayList<>();
        tags.add("test tag 1");
        tags.add("test tag 2");
        HashMap<String, ArrayList<Image>> user_images = new HashMap<>();

        Image test_image = new Image("filename", jan_1970, tags);
        ArrayList<Image> test_array = new ArrayList<>();
        test_array.add(test_image);

        HashMap<String, ArrayList<Image>> test_map = new HashMap<>();
        test_map.put("01-1970", test_array);
        test_map.put("test tag 1", test_array);
        test_map.put("test tag 2", test_array);
        HashMap<String, ArrayList<Image>> compare_map = Image_Repo.image_add(user_images, test_image);
        assertEquals(compare_map, test_map);
    }

    @Test
    public void Test_image_search_empty() {
        HashMap<String, ArrayList<Image>> user_images = new HashMap<>();

        ArrayList<String> search_tags = new ArrayList<>();
        search_tags.add("test tag");

        ArrayList<Image> compare_array = new ArrayList<>();
        compare_array.add(new Image("filename"));
        compare_array.remove(0);

        assertArrayEquals(Image_Repo.image_search(user_images, search_tags).toArray(), compare_array.toArray());
    }

    @Test
    public void Test_image_search_no_match() {
        HashMap<String, ArrayList<Image>> user_images = new HashMap<>();
        Image test_image = new Image("filename");
        ArrayList<Image> test_array = new ArrayList<>();
        test_array.add(test_image);
        user_images.put("test tag 1", test_array);

        ArrayList<String> search_tags = new ArrayList<>();
        search_tags.add("test tag 2");

        ArrayList<Image> compare_array = new ArrayList<>();
        compare_array.add(test_image);
        compare_array.remove(0);

        assertArrayEquals(Image_Repo.image_search(user_images, search_tags).toArray(), compare_array.toArray());
    }

    @Test
    public void Test_image_search_match() {
        String test_tag = "test tag";
        HashMap<String, ArrayList<Image>> user_images = new HashMap<>();
        Image test_image = new Image("filename");
        ArrayList<Image> test_array = new ArrayList<>();
        test_array.add(test_image);
        user_images.put(test_tag, test_array);

        ArrayList<String> search_tags = new ArrayList<>();
        search_tags.add(test_tag);

        ArrayList<Image> compare_array = new ArrayList<>();
        compare_array.add(test_image);

        assertArrayEquals(Image_Repo.image_search(user_images, search_tags).toArray(), compare_array.toArray());
    }

    @Test
    public void Test_image_search_match_two_tags() {
        String test_tag_1 = "test tag 1";
        String test_tag_2 = "test tag 2";
        HashMap<String, ArrayList<Image>> user_images = new HashMap<>();
        Image test_image = new Image("filename");
        ArrayList<Image> test_array_1 = new ArrayList<>();
        ArrayList<Image> test_array_2 = new ArrayList<>();
        test_array_1.add(test_image);
        test_array_2.add(test_image);
        user_images.put(test_tag_1, test_array_1);
        user_images.put(test_tag_2, test_array_2);

        ArrayList<String> search_tags = new ArrayList<>();
        search_tags.add(test_tag_1);
        search_tags.add(test_tag_2);

        ArrayList<Image> compare_array = new ArrayList<>();
        compare_array.add(test_image);

        assertArrayEquals(Image_Repo.image_search(user_images, search_tags).toArray(), compare_array.toArray());
    }

    @Test
    public void Test_string_to_date_valid() throws ParseException {
        String date_string = "01-2022";
        SimpleDateFormat sdf = new SimpleDateFormat("M-yyyy");
        assertEquals(Image_Repo.string_to_date(date_string), sdf.parse(date_string));
    }
}
