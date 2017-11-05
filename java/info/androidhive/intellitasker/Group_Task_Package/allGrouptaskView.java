package info.androidhive.intellitasker.Group_Task_Package;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import info.androidhive.intellitasker.R;
public class allGrouptaskView extends makeRecyclerView {
    allGrouptaskAdapter adapter;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_group_name_view);
        FloatingActionButton  createnewgroupbutton= (FloatingActionButton) findViewById(R.id.FAB);
        createnewgroupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(allGrouptaskView.this, createNewGroup.class);
                startActivityForResult(intent, selectedGroupConstrant.creategroup);
            }
        });
        username=global.getUserName();
        Log.d("username ",username);
        ///initialize all_group_name_view/////
        Log.d("ussername ",global.username);
        adapter = new allGrouptaskAdapter(this);
        recyclerView =
                (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        setRecyclerLayout(recyclerView);
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("grouptasks").child(username);
        final DatabaseReference ref2, ref3, ref4;
        ref2 = ref1.child("grouptask");
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Result will be holded Here
                int i=0;
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        String firebasegroupname=dsp.getKey();
                        Log.d("hey there ",firebasegroupname);
                        adapter.updatePost(firebasegroupname,"details");
                    }
                }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
    //// after creating group name return to this fucntion and add new group to the view
    protected void onActivityResult(int requestcode, int resultcode, Intent data)
    {
        if(resultcode== selectedGroupConstrant.creategroup);
        {
            ////get  values of the new group to update from the createNewGroup class
            String titles= data.getStringExtra(selectedGroupConstrant.titles);
            Log.d("ShareResourceActivity", "here result 2" + titles+" ");
            String details=data.getStringExtra(selectedGroupConstrant.details);
            ;
            Log.d("ShareResourceActivity", "here result 2" + titles+" "+details+" ");
            //Catagory= "Anonymous has shared a resource on "+Catagory;
            //update the view of group task by adding a new group
            adapter.updatePost(titles,details);
        }
    }
}
