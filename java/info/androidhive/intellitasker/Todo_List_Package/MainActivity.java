package info.androidhive.intellitasker.Todo_List_Package;
import info.androidhive.intellitasker.R;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {  //This class holds the view of the list of the tasks

    String Tag= "MainActivity";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;

    public CheckBox cbSelect;


    String messageText;
    int POSITION;

    DatabaseHelper mDatabaseHelper;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_todo);
        mDatabaseHelper= new DatabaseHelper(this);



        Log.d("MainActivity", "I am here");



        //cbSelect=(CheckBox)findViewById(R.id.cbInCard);

        recyclerView =(RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(adapter);


        LoadPreviousData();  // Getting previous data from  offline database

    }


    public  void onClick(View v)  // I will be able to create a new task
    {
        Intent intent = new Intent(MainActivity.this, EditFieldClass.class);
        startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE);

    }



    protected void onActivityResult(int requestcode, int resultcode, Intent data)
    {
            if(resultcode==Intent_Constants.INTENT_REQUEST_CODE)        //adds in the listview
            {
                messageText=data.getStringExtra(Intent_Constants.INTENT_MESSAGE_FIELD1);
                String returnedDate=data.getStringExtra(Intent_Constants.INTENT_MESSAGE_FIELD2);
                adapter.updatePost(messageText, returnedDate);
                adapter.notifyDataSetChanged();

            }
            else if(resultcode==Intent_Constants.INTENT_REQUEST_CODE_TWO)  // adds in the listview in a specific position
            {

                messageText=data.getStringExtra(Intent_Constants.INTENT_CHANGED_MESSAGE);
                POSITION=data.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION,-1);


                Toast.makeText(this, "pos= "+POSITION, Toast.LENGTH_LONG).show();


            }
            else
            {
                Log.d("MainActivity", "SomeThing Wrong Happened");
            }
    }



    public void LoadPreviousData()  // Loads previous data from  offline sqlite database
    {



        adapter.ClearList();
        Cursor data= mDatabaseHelper.getData();
        while(data.moveToNext()) {
            // Log.d(Tag, "addData: Adding " + TaskInfo+"##"+DueDate+"##"+Rem1+"##"+Rem2+"##"+Rem3 + " to " + TABLE_NAME);
            Log.d(Tag, data.getString(0)+ " "+data.getString(1)+ " "+data.getString(2)+ " "
            +data.getString(3)+ " "+data.getString(4)+ " "+data.getString(5)+ " -> "+data.getString(9));

            adapter.updatePost(data.getString(1), data.getString(4));
            adapter.notifyDataSetChanged();
            
        }

    }







}
