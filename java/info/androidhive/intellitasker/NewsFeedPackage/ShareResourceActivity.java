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

public class ShareResourceActivity extends ParentNewsFeed {  // Shared feed class

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    RecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);


        Firebase.setAndroidContext(this);
        //This is my database reference  to connect with firebase
        FDataBaseRef= new Firebase("https://intellitasker-38a1b.firebaseio.com/");



        //Addressing the buttons in corresponding xml layout
        b_WritePost= (Button)findViewById(R.id.b_WritePost);
        b_TaskFeed= (Button)findViewById(R.id.b_TaskFeed);
        b_FindingFeed= (Button)findViewById(R.id.b_FindingFeed);
        b_SharedFeed= (Button)findViewById(R.id.b_SharedFeed);

        b_TaskFeed.setBackgroundResource(R.drawable.bg1);
        b_FindingFeed.setBackgroundResource(R.drawable.bg1);
        b_SharedFeed.setBackgroundResource(R.drawable.bg2);


        recyclerView =(RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);


        //With this method I am fetching my previous  data from firebase  for this current activity and adding them in  recyclerview

        //adapter.ClearList();
        LoadPreviousDataFromFirebase("ShareResourceActivity");

    }


    public void WritePostButtonClicked(View v)   // when we want to post something
    {
        Intent intent =new Intent(ShareResourceActivity.this, WritePostClass.class);
        startActivityForResult(intent, FeedIntentConstraint.WritePostRequestCode);
    }


    public void SharedButtonClicked(View v)  /// When we want to go shared feed
    {  //nothing
    }

    public void TaskButtonClicked(View v)   // When we want to go to task feed
    {
        GoingToTaskFeed();
    }

    public  void FindingButtonClicked(View v)  // for finding feed
    {
        GoingToFindingFeed();
    }

    public void GoingToTaskFeed()
    {
        Intent intent= new Intent(ShareResourceActivity.this, TaskFeedClass.class );
        startActivityForResult(intent, FeedIntentConstraint.TaskFeedRequestCode);
    }

    public  void GoingToFindingFeed()
    {
        Intent intent= new Intent(ShareResourceActivity.this, FindingFeedClass.class );
        startActivityForResult(intent, FeedIntentConstraint.FindingFeedRequestCode);
    }




    protected void onActivityResult(int requestcode, int resultcode, Intent data) // This will happer after returning from other activities
    {
        Log.d("ShareResourceActivity", "->->->req: "+requestcode+ " res: "+requestcode);
        if(resultcode==FeedIntentConstraint.WritePostRequestCode)
        {
            String Post= data.getStringExtra(FeedIntentConstraint.PostCode);
            String Catagory=data.getStringExtra(FeedIntentConstraint.CatagoryCode);
            String date= data.getStringExtra(FeedIntentConstraint.Datecode);

            Log.d("ShareResourceActivity", "here result 2" + Post+" "+Catagory+" "+date);

            PostInfoClass postInfo= new PostInfoClass("NewsFeed", Post,Catagory,date);

            //Here adding in database , then clearing the adapter and again loading data from Firebase
            addPostInDatabas(postInfo,"ShareResourceActivity");
            adapter.ClearList();
            LoadPreviousDataFromFirebase("ShareResourceActivity");

        }
        else if(resultcode==FeedIntentConstraint.FindingFeedRequestCode)
        {
            String Goto= data.getStringExtra(FeedIntentConstraint.Goto);
            if(Goto.equals("TaskFeed"))
                GoingToTaskFeed();


        }
        else if(resultcode==FeedIntentConstraint.TaskFeedRequestCode)
        {
            String Goto= data.getStringExtra(FeedIntentConstraint.Goto);
            Log.d("ShareResourceActivity", "goto :"+Goto);
            if(Goto.equals("FindingFeed"))
                GoingToFindingFeed();


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