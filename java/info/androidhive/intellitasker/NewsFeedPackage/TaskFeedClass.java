package info.androidhive.intellitasker.NewsFeedPackage;
import info.androidhive.intellitasker.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by musfiq on 10/26/17.
 */

public class TaskFeedClass  extends ParentNewsFeed {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    RecyclerAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);


        Firebase.setAndroidContext(this);
        //This is my database reference  to connect with firebase
        FDataBaseRef= new Firebase("https://intellitasker-38a1b.firebaseio.com/");


        b_WritePost= (Button)findViewById(R.id.b_WritePost);
        b_TaskFeed= (Button)findViewById(R.id.b_TaskFeed);
        b_FindingFeed= (Button)findViewById(R.id.b_FindingFeed);
        b_SharedFeed= (Button)findViewById(R.id.b_SharedFeed);

        b_TaskFeed.setBackgroundResource(R.drawable.bg2);
        b_FindingFeed.setBackgroundResource(R.drawable.bg1);
        b_SharedFeed.setBackgroundResource(R.drawable.bg1);

        b_WritePost.setEnabled(false);
        b_WritePost.setVisibility(View.GONE);


        recyclerView =(RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        LoadPreviousDataFromFirebase("TaskFeedClass");

    }

    public void GoBack(String whichClass)
    {
        Intent intent = new Intent();

        intent.putExtra(FeedIntentConstraint.Goto, whichClass);

        setResult(FeedIntentConstraint.TaskFeedRequestCode, intent);
        finish();


        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

    }

    public void onBackPressed() {

        GoBack("root");
    }

    public void SharedButtonClicked(View v)
    {
        GoBack("root");
    }

    public void FindingButtonClicked(View v)
    {
        GoBack("FindingFeed");
    }

    public void TaskButtonClicked(View v)   // When we want to go to task feed
    {

    }


    public void LoadPreviousDataFromFirebase(String WhichClass)   //Fetching data from firebase
    {

        Firebase UserRef= FDataBaseRef.child("NewsFeed");  //1st layer

        Firebase PostDivision = UserRef.child(WhichClass);  //2nd layer


        PostDivision.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dsp: dataSnapshot.getChildren()){

                    TaskFeedPostInfo TaskPostInfo= dsp.getValue(TaskFeedPostInfo.class);
                    adapter.updatePost2(TaskPostInfo);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }


}
