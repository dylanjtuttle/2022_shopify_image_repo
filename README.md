# Dylan Tuttle's Very Cool Image Repository

This image repository is designed for the tagging of images, as well as an efficient method of search by tags, even at large scale.

## Installation and Execution

Open up a terminal window and ensure you are in your home directory. Execute the following commands:

```bash
git clone https://github.com/dylanjtuttle/2022_shopify_image_repo
cd 2022_shopify_image_repo
javac src/Code/*.java -d ./out
cd out
java Code.Image_Repo
```

## Data Structure Overview

This repository consists of a hierarchy of Java HashMaps. Although the current implementation does not allow for persistence, the main HashMap contains a key for each user in order to keep each user's images seperate, although there is no password required to access each user's repository (as security is not the main focus of this project). Furthermore, each user's repository is itself a HashMap containing keys for each "tag" that has been added to an image in the repository, containing an ArrayList of images which that tag has been assigned to. These tags are arbitrary strings which the user can choose to interpret as photo albums, descriptions of the contents of an image, or any other organizational system the user can think of.

Whenever an image is added to the user's repository, the user must attach a month and year in which the image was created, and has the option to add additional tags if they wish. For each tag added (including the month/year created), the repository is checked to see if a key with the same tag already exists (in which case the image is added to the end of the ArrayList). If such a key does not exist, a new ArrayList is created, the image is added to it, and the ArrayList is put into the HashMap with the tag as its key.

### Efficiency and Assumptions

HashMaps are data structures which store key-value pairs. When considering the storage and organization of images by tagging, it made sense to implement a user's repository as a HashMap with the tags as the keys, and an ArrayList of images as the values.

HashMaps have very efficient (indeed, linear) insert, delete, and search operations in the average case. However, these operations become very inefficient in the worst case. This worst case occurs most notably when nearly or all values map to the same key, essentially turning the HashMap into a linked list or array. While designing this repository, I considered the possibility that a user chooses to dump all of their photos into the repository without choosing to add any tags at all. In this case, all of the user's images (of which there could be millions) would theoretically map to the same key. To mitigate this issue, I required the user to indicate the month and year of creation, and to include that as a tag as well. Making, in my view, a reasonable assumption that no user would take more than perhaps a few thousand photos over the course of one month, this ensures that the insert, delete, and search operations remain efficient even with millions of photos and a stubborn user who chooses to ignore the tagging feature.

## Limitations and Future Improvements

The most relevant improvements that can be made to this repository are additional operations on the images. For example, deleting or adding tags to existing images (analagous to the ability to add an existing image to a photo album). Based on the existing infrastructure, these operations should be easy to implement, but within the condensed timeframe I did not have the ability to add them in this version.

Of course, the lack of persistence is a concern, as the images are stored on the user's memory during the running of the program, and then are simply deleted as soon as the program is exited. However, since the focus of this project is on the data structures, storage, and efficiency of the operations, persistence is beyond the scope of this project. Furthermore, the loosely-coupled nature of this project allows for anyone to easily swap out the front end (which provides the image, month/year of creation, and tags), and the actual storage of the HashMaps, while this program runs in the middle, connecting the two.

One may notice my use of SimpleDateFormat to store the month and year of image creation, instead of a String like the rest of the tags. This construct allows for the comparing of two dates, and could therefore allow for a user to search for images created over a period of multiple months.

The very open-ended nature of the tags may prove confusing for some users, so it would be simple to, in a future implementation, add an option for "adding images to albums" even if they use the same tagging mechanism on the back end.

Finally, the ability of the program to host the photos of multiple users and store an arbitrary set of tags could lend itself quite easily to a sharing feature, whereby users can tag images with the usernames of other users in the repository, giving them access to certain images.

## License
MIT License

Copyright (c) 2022 Dylan Tuttle

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
