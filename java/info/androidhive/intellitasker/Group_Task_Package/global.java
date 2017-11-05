package info.androidhive.intellitasker.Group_Task_Package;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by zaoad on 11/3/17.
 */

public class global {
    public static String username="";
    public static  String myUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    // get userame
    public static  String getUserName() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(myUID).child("name");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                username = (String) dataSnapshot.getValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(postListener);
        return username;
    }
}


