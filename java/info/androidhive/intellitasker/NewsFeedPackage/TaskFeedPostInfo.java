package info.androidhive.intellitasker.NewsFeedPackage;
import info.androidhive.intellitasker.R;
/**
 * Created by musfiq on 10/28/17.
 */

public class TaskFeedPostInfo  {


     public String Publisher, PostDetails,Catagory,PostedDate,Duration;

    public TaskFeedPostInfo()
    {

    }

    TaskFeedPostInfo(String publisher, String postDetails, String catagory, String postedDate, String duration)
    {
        Publisher=publisher;
        PostDetails = postDetails;
        Catagory = catagory;
        PostedDate = postedDate;
        Duration=duration;
    }


    @Override
    public String toString() {
        return "TaskFeedPostInfo{" +
                "Publisher='" + Publisher + '\'' +
                ", PostDetails='" + PostDetails + '\'' +
                ", Catagory='" + Catagory + '\'' +
                ", PostedDate='" + PostedDate + '\'' +
                ", Duration='" + Duration + '\'' +
                '}';
    }

    public String getDuration() {
        return Duration;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getPostDetails() {
        return PostDetails;
    }

    public void setPostDetails(String postDetails) {
        PostDetails = postDetails;
    }

    public String getCatagory() {
        return Catagory;
    }

    public void setCatagory(String catagory) {
        Catagory = catagory;
    }

    public String getPostedDate() {
        return PostedDate;
    }

    public void setPostedDate(String postedDate) {
        PostedDate = postedDate;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }
}
