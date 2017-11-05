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

import java.util.List;

import info.androidhive.intellitasker.R;

/**
 * Created by zaoad on 10/26/17.
 */
public class AllMembersView extends makeRecyclerView {
    allMembersAdapter adapter;
    String groupname;
    public static List<String> members;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_members_view);
        Bundle b = getIntent().getExtras();
        groupname = b.getString("key");
        FloatingActionButton createnewmemberbutton= (FloatingActionButton) findViewById(R.id.FAB);
        createnewmemberbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AllMembersView.this, createNewMemberFriend.class);
                startActivityForResult(intent, 13);
            }
        });
        ///initialize all_group_name_view/////
        adapter = new allMembersAdapter(this,groupname);
        recyclerView =
                (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        setRecyclerLayout(recyclerView);
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("grouptasks").child(global.username);
        DatabaseReference ref2, ref3, ref4;
        ref2 = ref1.child("grouptask");
        ref3=ref2.child(groupname);
        ref3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Result will be holded Here
                int i=0;
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String firebasegroupname=dsp.getKey();
                    Log.d("hey there ",firebasegroupname);
                    adapter.updatePost(firebasegroupname,"details");
                }
                //adapter.Removefirst();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    //// after creating group name return to this fucntion and add new group to the view
    protected void onActivityResult(int requestcode, int resultcode, Intent data)
    {
        if(resultcode==13);
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
            members=adapter.getmemberlist();
            for(int i=0;i<members.size();i++)
            {
                Log.d("loop for members check ",String.valueOf(members.get(i)));
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("grouptasks").child(String.valueOf(members.get(i)));
                DatabaseReference ref1,ref2;
                ref1=myRef.child("grouptask");
                ref2=ref1.child(groupname);
                String id;
                for(int j=0;j<members.size();j++) {
                    id = String.valueOf(members.get(j));
                    ref2.child(id).setValue(String.valueOf(members.get(j)));
                }
            }
        }
    }
}
