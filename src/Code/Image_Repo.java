package Code;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Image_Repo {

    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("\nWelcome to Dylan Tuttle's Very Cool Image Repository!\n");

        // Create the outer HashMap to store each user's personal HashMap and populate it with a dummy HashMap with key "root"
        HashMap<String, HashMap<String, ArrayList<Image>>> users = new HashMap<>();
        users.put("root", new HashMap<>());

        // Initialize a place to hold the current user's HashMap
        HashMap<String, ArrayList<Image>> user_images;

        // Get the current user's HashMap, either by...
        System.out.println("Enter your username:");
        String potential_username = in.nextLine();

        // Retrieving the existing HashMap if the username already exists, or...
        if (users.containsKey(potential_username)) {
            System.out.printf("\nWelcome, %s!\n", potential_username);
            user_images = users.get(potential_username);
        } else {  // Creating a new HashMap for the user and adding it to the users HashMap if the username does not exist
            System.out.printf("\nThank you for using the Very Cool Image Repository, %s!\n", potential_username);
            user_images = new HashMap<>();
            users.put(potential_username, user_images);
            System.out.println("Your account has been added to the repository.\n");
        }

        // We have entered the repository, we can now allow the user to perform operations on their images
        boolean quit = false;
        while (!quit) {
            System.out.println("\nWhat would you like to do?\nA: Add images\nS: Search for images\nQ: Quit");
            String like_to_do = in.nextLine();
            if (like_to_do.equalsIgnoreCase("a")) {
                // If the user wants to add an image, we need to come up with some metadata to store it with.
                // For the purposes of this proof of concept (and fun of testing), we will allow the user to enter this
                // metadata directly in the console.
                Image new_image = generate_image_metadata(user_images);
                System.out.println("Excellent! Adding image to repository...");
                image_add(user_images, new_image);
                System.out.println("Your image has successfully been added to the repository!\n");
            } else if (like_to_do.equalsIgnoreCase("s")) {
                // Search by tags and report the matching filenames, if any exist
                ArrayList<Image> image_matches = get_search_tags(user_images);
                if (image_matches.size() == 0) {
                    System.out.println("Your search did not return any results.");
                } else {
                    ArrayList<String> match_filenames = get_filenames(image_matches);
                    System.out.println("Search results:");
                    for (String match : match_filenames) {
                        System.out.printf("\t- %s\n", match);
                    }
                }
            } else if (like_to_do.equalsIgnoreCase("q")) {
                // Exit the program
                System.out.println("Thank you for using Dylan Tuttle's Very Cool Image Repository! See you soon!");
                quit = true;
            } else {
                System.out.println("Invalid input, please enter one of \"A\", \"S\", or \"Q\".");
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // METHODS USED FOR ADDING IMAGES
    // -----------------------------------------------------------------------------------------------------------------

    public static Image generate_image_metadata(HashMap<String, ArrayList<Image>> user_images) {
        boolean done_adding = false;
        while (!done_adding) {
            // Prompt for filename
            System.out.println("\nTo add an image, first enter the filename (For example, \"image.jpg\") or \"Q\" to exit.");
            String potential_filename = in.nextLine();
            if (potential_filename.equalsIgnoreCase("q")) {
                done_adding = true;
            } else {
                // Prompt for month and year of image creation
                System.out.println("Enter the month and year in which this image was created (MM-YYYY):");
                boolean month_valid = false;
                while (!month_valid) {
                    String potential_month = in.nextLine();
                    try {
                        Date month = string_to_date(potential_month);
                        month_valid = true;
                        // Prompt for tags
                        System.out.println("Great! Now you can optionally add some descriptive tags to your image.");
                        System.out.println("These tags could be the name of a photo album (e.g. \"Photos with Grandma\"), " +
                                "or just some kind of description of what the image contains (e.g. \"Trees\").");
                        ArrayList<String> tags = add_tags(user_images);
                        // Create the new image with the filename, month, and tags given by the user and return it
                        return new Image(potential_filename, month, tags);
                    } catch (Exception ParseException) {
                        System.out.println("Invalid input, please enter month in the numerical format MM-YYYY (e.g. 01-2022):");
                    }
                }
            }
        }
        return new Image("null_filename");
    }

    public static ArrayList<String> add_tags(HashMap<String, ArrayList<Image>> user_images) {
        // This method prompts the user to add descriptive tags to their new image
        ArrayList<String> tags = new ArrayList<>();
        // Prompt for tags
        System.out.println("Enter a tag below and press Return. If you are done or don't want to add any tags, " +
                "simply press Return.\nIf you need a reminder of the existing tags in your repository, enter \"E\".");
        boolean done_tagging = false;
        while (!done_tagging) {
            String new_tag = in.nextLine();
            if (new_tag.equalsIgnoreCase("")) {
                done_tagging = true;
            } else if (new_tag.equalsIgnoreCase("e")) {
                // Display all existing tags in the user's repository (that is, all keys in the user's HashMap)
                System.out.println("Existing tags in your repository:");
                for (String key : user_images.keySet()) {
                    System.out.printf("\t- %s\n", key);
                }
            } else {
                // Add the new tag to the ArrayList
                tags.add(new_tag);
            }
        }
        return tags;
    }

    public static HashMap<String, ArrayList<Image>> image_add (HashMap<String, ArrayList<Image>> user_images, Image new_image) {
        // This method takes an Image object and inserts it into the user's HashMap, once for every tag added
        ArrayList<String> image_tags = new_image.get_tags();
        // Since we want to organize by month of creation as well, add the string version of the month to the list of tags
        image_tags.add(date_to_string(new_image.get_month()));
        for (String tag : image_tags) {
            // For every tag in the list, if it already exists in the repo,
            // simply add the image to the existing list of images under that tag
            if (user_images.containsKey(tag)) {
                ArrayList<Image> tagged_images = user_images.get(tag);
                tagged_images.add(new_image);
            } else {
                // If the tag does not already exist in the repo, create a new list, put the image in it,
                // and add it to the HashMap
                ArrayList<Image> newly_tagged = new ArrayList<>();
                newly_tagged.add(new_image);
                user_images.put(tag, newly_tagged);
            }
        }
        return user_images;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // METHODS USED FOR SEARCHING FOR IMAGES
    // -----------------------------------------------------------------------------------------------------------------

    public static ArrayList<Image> get_search_tags (HashMap<String, ArrayList<Image>> user_images) {
        // Tags provided by the user to search the HashMap for
        ArrayList<String> tags_to_search_for = new ArrayList<>();
        System.out.println("\nTo search for images, enter one or more tags below, pressing Return after each tag.");
        System.out.println("If you need a reminder of the existing tags in your repository, enter \"E\".");
        System.out.println("When you are done adding tags to search for, simply press Return.");
        boolean done_adding_tags = false;
        while (!done_adding_tags) {
            String tag = in.nextLine();
            if (tag.equalsIgnoreCase("")) {
                done_adding_tags = true;
                System.out.println("Searching...");
            } else if (tag.equalsIgnoreCase("e")) {
                // Display all existing tags in the user's repository (that is, all keys in the user's HashMap)
                System.out.println("Existing tags in your repository:");
                for (String key : user_images.keySet()) {
                    System.out.printf("\t- %s\n", key);
                }
            } else {
                tags_to_search_for.add(tag);
            }
        }
        return image_search(user_images, tags_to_search_for);
    }

    public static ArrayList<Image> image_search (HashMap<String, ArrayList<Image>> user_images, ArrayList<String> tags_to_search_for) {
        // An array of arrays corresponding to matching tags if they are found in the HashMap
        ArrayList<ArrayList<Image>> initial_matches = new ArrayList<>();
        for (String tag : tags_to_search_for) {
            // For every tag the user wants to search for, check if it exists in the HashMap
            if (user_images.containsKey(tag)) {
                // If it does, add the array of images corresponding to that tag to the array of initial matches
                ArrayList<Image> match = new ArrayList<>(user_images.get(tag));
                initial_matches.add(match);
            } else {
                System.out.printf("Tag \"%s\" does not exist in your repository.\n", tag);
            }
        }

        System.out.println("Initial matches found...");

        // We now have an array of arrays which match the tags given by the user.
        // If the user only searched for one tag, we can simply return the single array inside initial_matches
        if (initial_matches.size() == 1) {
            return initial_matches.get(0);
        } else if (initial_matches.size() > 1) {
            // If the user searched for more than one tag, we need to find all the images which have ALL of those tags
            // (that is, all images which can be found in all the arrays in initial_matches, if any such images exist)
            while (initial_matches.size() > 1) {
                // We begin by selecting the first two arrays
                ArrayList<Image> first_match = initial_matches.get(0);
                ArrayList<Image> second_match = initial_matches.get(1);
                // We then delete all images from the first array which can not also be found in the second array
                first_match.retainAll(second_match);
                // We delete the second array, making the third array into the new second array (if it exists) and loop again
                initial_matches.remove(1);
            }
            // Once we finish looping, there is a single array in initial_matches containing only the images which
            // could be found in all arrays, so we return that array
            return initial_matches.get(0);
        } else {
            // If initial_matches is empty, there were no tags that matched the user's query, so we return an empty list
            return new ArrayList<>();
        }
    }

    public static ArrayList<String> get_filenames(ArrayList<Image> matches) {
        // This method loops through a list of images and returns a list of their filenames
        ArrayList<String> match_filenames = new ArrayList<>();
        for (Image image_match : matches) {
            match_filenames.add(image_match.get_filename());
        }
        return match_filenames;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // HELPER METHODS
    // -----------------------------------------------------------------------------------------------------------------

    public static Date string_to_date(String date_string) throws ParseException {
        // This method parses a string and returns a formatted date object corresponding to that string,
        // or throws a ParseException if the string cannot be parsed
        if (!date_string.matches("^[0-9]{2}-[0-9]{4}$")) {
            // A strange bug allowed for certain strings (for example, strings of format YYYY-M) to be accepted and
            // converted into nonsensical formatted date objects. I fixed this issue by requiring the string to also
            // match a regex expression of the form "DD-DDDD"
            throw new ParseException("String does not match regex", 1);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("M-yyyy");
        return sdf.parse(date_string);
    }

    public static String date_to_string(Date date) {
        // This method accepts a formatted date object and returns a string in "MM-YYYY" format corresponding to that date
        SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
        return sdf.format(date);
    }

}
