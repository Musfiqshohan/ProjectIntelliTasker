package info.androidhive.intellitasker.NewsFeedPackage;
import info.androidhive.intellitasker.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by musfiq on 10/24/17.
 */

public class WritePostClass  extends AppCompatActivity{ // This is just a activity for writing new post and saving them


    Button b_SavePost;
    EditText CatagoryField,PostField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writepostlayout);

        b_SavePost= (Button)findViewById(R.id.b_SavePost);
        PostField= (EditText)findViewById(R.id.PostField);
        CatagoryField= (EditText)findViewById(R.id.CatagoryField);

    }


    public  void SavePostButtonClicked(View v)
    {
        String Post= PostField.getText().toString();
        String catagory= CatagoryField.getText().toString();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate=dateFormat.format(date); //2016/11/16 12:08:43

        Log.d("WritePostClass", "here result 1" + Post+" "+catagory+" "+date);
        Intent intent= new Intent();
        intent.putExtra(FeedIntentConstraint.PostCode,Post);
        intent.putExtra(FeedIntentConstraint.CatagoryCode,catagory);
        intent.putExtra(FeedIntentConstraint.Datecode,curDate);

        setResult(FeedIntentConstraint.WritePostRequestCode,intent);
        finish();


    }
}
