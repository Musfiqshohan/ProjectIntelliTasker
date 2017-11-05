package info.androidhive.intellitasker.NewsFeedPackage;
import info.androidhive.intellitasker.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by musfiq on 10/25/17.
 */

public class FindingFeedClass extends ParentNewsFeed {  // This class is similar to SharedResource activity but with different purpose
                                                        // So used methods are almost same

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    RecyclerAdapter adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Firebase.setAndroidContext(this);
        FDataBaseRef= new Firebase("https://intellitasker-38a1b.firebaseio.com/");





        b_WritePost= (Button)findViewById(R.id.b_WritePost);
        b_TaskFeed= (Button)findViewById(R.id.b_TaskFeed);
        b_FindingFeed= (Button)findViewById(R.id.b_FindingFeed);
        b_SharedFeed= (Button)findViewById(R.id.b_SharedFeed);

        b_TaskFeed.setBackgroundResource(R.drawable.bg1);
        b_FindingFeed.setBackgroundResource(R.drawable.bg2);
        b_SharedFeed.setBackgroundResource(R.drawable.bg1);



        recyclerView =(RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);


        LoadPreviousDataFromFirebase("FindingFeedClass");

    }

    public void GoBack(String whichClass)
    {
        Intent intent = new Intent();

        intent.putExtra(FeedIntentConstraint.Goto, whichClass);

        setResult(FeedIntentConstraint.FindingFeedRequestCode, intent);
        finish();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

    }

    public void onBackPressed() {
        GoBack("root");
    }

    public void SharedButtonClicked(View v)
    {
        GoBack("root");
    }


    public void TaskButtonClicked(View v)   // When we want to go to task feed
    {
            GoBack("TaskFeed");
    }

    public void FindingButtonClicked(View v)
    {

    }



    public void WritePostButtonClicked(View v)
    {
        Intent intent =new Intent(FindingFeedClass.this, WritePostClass.class);
        startActivityForResult(intent, FeedIntentConstraint.WritePostRequestCode);

    }

    protected void onActivityResult(int requestcode, int resultcode, Intent data)
    {
        if(resultcode==FeedIntentConstraint.WritePostRequestCode);
        {
            String Post= data.getStringExtra(FeedIntentConstraint.PostCode);
            String Catagory=data.getStringExtra(FeedIntentConstraint.CatagoryCode);
            String date= data.getStringExtra(FeedIntentConstraint.Datecode);

            Log.d("ShareResourceActivity", "here result 2" + Post+" "+Catagory+" "+date);

            PostInfoClass postInfo= new PostInfoClass("NewsFeed", Post,Catagory,date);

            addPostInDatabas(postInfo,"FindingFeedClass");
            adapter.ClearList();
            LoadPreviousDataFromFirebase("FindingFeedClass");

        }

    }


    public void LoadPreviousDataFromFirebase(String WhichClass)   //Fetching data from firebase
    {


        Firebase UserRef= FDataBaseRef.child("NewsFeed");  //1st layer

        Firebase PostDivision = UserRef.child(WhichClass);  //2nd layer


        PostDivision.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dsp: dataSnapshot.getChildren()){

                    PostInfoClass PostInfo= dsp.getValue(PostInfoClass.class);
                    adapter.updatePost(PostInfo);
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }




}
