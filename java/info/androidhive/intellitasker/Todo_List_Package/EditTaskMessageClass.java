package info.androidhive.intellitasker.Todo_List_Package;
import info.androidhive.intellitasker.R;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTaskMessageClass extends AppCompatActivity { // When we later want to change something about already created
                                                                //task


    String messageText;
    int position;

    Button b_datepick, b_reminder1, b_reminder2, b_reminder3;

    EditText field1, field2, field3;

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_layout);





        b_datepick = (Button) findViewById(R.id.b_pick);
        b_reminder1 = (Button) findViewById(R.id.b_reminder1);
        b_reminder2 = (Button) findViewById(R.id.b_reminder2);
        b_reminder3 = (Button) findViewById(R.id.b_reminder3);
        field1= (EditText)findViewById(R.id.TaskInfoField);
        field2= (EditText)findViewById(R.id.CatField);
        field3= (EditText)findViewById(R.id.DurField);


        Intent intent = getIntent();
        System.out.println("I am in Edit text");
        messageText = intent.getStringExtra(Intent_Constants.INTENT_MESSAGE_DATA);
        position = intent.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION, -1);

        LoadWholeTask(position);


        EditText messageData = (EditText) findViewById(R.id.TaskInfoField);
        messageData.setText(messageText);


        b_datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });

        b_reminder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        b_reminder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
        b_reminder3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


    }


    public void SaveButtonClicked(View v) {


        String TaskInfoMessage,CatInfoMessage,Duration;
        TaskInfoMessage = ((EditText) findViewById(R.id.TaskInfoField)).getText().toString();
        CatInfoMessage = ((EditText) findViewById(R.id.CatField)).getText().toString();
        Duration = ((EditText) findViewById(R.id.DurField)).getText().toString();





        System.out.println("I am in save Button");
        String changeMessageText = ((EditText) findViewById(R.id.TaskInfoField)).getText().toString();
        Intent intent = new Intent();
        intent.putExtra(Intent_Constants.INTENT_CHANGED_MESSAGE, changeMessageText);
        intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION, position);
        setResult(Intent_Constants.INTENT_RESULT_CODE_TWO, intent);

        finish();

    }




    public void LoadWholeTask(int position) //Gets the previously set due time and reminders for current task what we want to edit
    {                                          //from offline database

        mDatabaseHelper= new DatabaseHelper(this);
        Cursor data= mDatabaseHelper.GetWholeData(position);

        while(data.moveToNext()) {

            field1.setText(data.getString(1));
            field2.setText(data.getString(2));
            field3.setText(data.getString(3));

            b_datepick.setText(data.getString(4));
            b_reminder1.setText(data.getString(5));
            b_reminder2.setText(data.getString(6));
            b_reminder3.setText(data.getString(7));



        }


    }


}
