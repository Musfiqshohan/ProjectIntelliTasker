package info.androidhive.intellitasker.Group_Task_Package;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import info.androidhive.intellitasker.R;

import static java.lang.String.valueOf;

/**
 * Created by zaoad on 10/26/17.
 */

public class createNewMember extends AppCompatActivity {
    int i=0;
    EditText membernametext;
    Button memberamebutton;
    String groupname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_gruop_event);
        membernametext=(EditText)findViewById(R.id.groupnametext);
        memberamebutton =(Button) findViewById(R.id.groupnamebutton);
        memberamebutton.setText("addMember");
        memberamebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupname= valueOf(membernametext.getText());
                membernametext.setText("");
                ////send group tame and details to allGrouptaskView
                Intent intent= new Intent();
                intent.putExtra(selectedGroupConstrant.titles,groupname);
                intent.putExtra(selectedGroupConstrant.details,"edit your task");
                //intent.putExtra(FeedIntentConstraint.Datecode,curDate);
                setResult(13,intent);
                finish();
            }
        });

    }
}
