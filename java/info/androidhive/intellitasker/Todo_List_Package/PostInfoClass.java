package info.androidhive.intellitasker.Todo_List_Package;
import info.androidhive.intellitasker.R;
/**
 * Created by musfiq on 10/24/17.
 */

public class PostInfoClass {

    public String Publisher, PostDetails,Catagory,PostedDate,Duration;

    public PostInfoClass()
    {

    }

    // Mandatory informations about a Post in Any feed
    public PostInfoClass(String publisher, String postDetails, String catagory, String duration, String postedDate) {
        Publisher=publisher;
        PostDetails = postDetails;
        Catagory = catagory;
        PostedDate = postedDate;
        Duration=duration;

    }

    @Override
    public String toString() {
        return "PostInfoClass{" +
                "Publisher='" + Publisher + '\'' +
                ", PostDetails='" + PostDetails + '\'' +
                ", Catagory='" + Catagory + '\'' +
                ", PostedDate='" + PostedDate + '\'' +
                '}';
    }

    public String getPublisher() {
        return Publisher;
    }

    public String getPostDetails() {
        return PostDetails;
    }

    public String getCatagory() {
        return Catagory;
    }

    public String getPostedDate() {
        return PostedDate;
    }


    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public void setPostDetails(String postDetails) {
        PostDetails = postDetails;
    }

    public void setCatagory(String catagory) {
        Catagory = catagory;
    }

    public void setPostedDate(String postedDate) {
        PostedDate = postedDate;
    }
}
