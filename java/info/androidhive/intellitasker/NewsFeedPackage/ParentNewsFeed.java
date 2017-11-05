package info.androidhive.intellitasker.NewsFeedPackage;
import info.androidhive.intellitasker.R;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.firebase.client.Firebase;

/**
 * Created by musfiq on 10/25/17.
 */

public class ParentNewsFeed extends AppCompatActivity {  // Taskfeed,  ShareResourceActivity and FindingFeedclass  are child of this class
                                                        // So all common methods and instances are implemented here

    Button b_WritePost,b_TaskFeed,b_FindingFeed,b_SharedFeed;



    protected Firebase FDataBaseRef;



    void addPostInDatabas(PostInfoClass posinfo, String WhichClass)  //Adding data in firebase
    {

        Firebase UserRef =FDataBaseRef.child(posinfo.Publisher);  //1st layer
        Firebase PostDivisionRef= UserRef.child(WhichClass);  //2nd layer

        String UniquePostString= PostDivisionRef.push().getKey();   // 3rd layer
        Firebase UniquePostRef= PostDivisionRef.child(UniquePostString);

        Firebase PostDetailsRef, CatagoryRef, DateRef, PublisherRef;

        PublisherRef=UniquePostRef.child("Publisher");
        PublisherRef.setValue(posinfo.Publisher);

        PostDetailsRef= UniquePostRef.child("PostDetails");
        PostDetailsRef.setValue(posinfo.PostDetails);

        CatagoryRef= UniquePostRef.child("Catagory");
        CatagoryRef.setValue(posinfo.Catagory);

        DateRef= UniquePostRef.child("PostedDate");
        DateRef.setValue(posinfo.PostedDate);

        Log.d("ShareResourceActivity", "I think I added: "+posinfo.PostDetails+" "+posinfo.Catagory);

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_card_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
