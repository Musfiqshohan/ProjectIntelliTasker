package info.androidhive.intellitasker.Todo_List_Package;
import info.androidhive.intellitasker.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by User on 2/28/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {   // This manages all operations about offline sqlite database

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "MyTaskTable6";   //Table name and columns of my data table
    private static final String COL1 = "ID";
    private static final String COL2 = "TaskInfo";
    private static final String COL3 = "CatInfo";
    private static final String COL4 = "Duration";
    private static final String COL5 = "DueDate";
    private static final String COL6 = "Reminder1";
    private static final String COL7 = "Reminder2";
    private static final String COL8 = "Reminder3";
    private static final String COL9 = "WEEK";
    private static final String COL10= "UID";


    private static final String gTABLE_NAME="graphTable";
    private static final String gCOL1 = "Week";
    private static final String gCOL2 = "Done";
    private static final String gCOL3 = "NotDone";

    private static  String userID="";


    protected Firebase FDataBaseRef;


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 2);

      /* SQLiteDatabase db = this.getWritableDatabase();
        db.delete("MyTaskTable5", null, null);


        db.delete("graphTable", null, null);
        Log.d("DatabaseHelper", "Successfully deleted");*/

        userID="11";
        try {
            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        catch (Exception e) {}
        if(userID=="11")
        {
            userID = "Anonymous";
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT, " +
                COL3+" TEXT, " +
                COL4+" TEXT, "+
                COL5+" TEXT, " +
                COL6+" TEXT, "+
                COL7+" TEXT, "+
                COL8+" TEXT, "+
                COL9+" TEXT, "+
                COL10+" TEXT )";
        db.execSQL(createTable);

        String createTable2 = "CREATE TABLE "+ gTABLE_NAME+
                "( " +
                gCOL1+" TEXT, "+
                gCOL2+" TEXT, "+
                gCOL3+" TEXT )";

        db.execSQL(createTable2);

        Log.d("DatabaseHelper", "I am here in onCreate");

        ContentValues contentValues = new ContentValues();
          for(int i=0;i<4;i++)
        {

            contentValues.put(gCOL1,i);
            contentValues.put(gCOL2,2);
            contentValues.put(gCOL3,3);
            long result = db.insert(gTABLE_NAME, null, contentValues);

            if(result!=-1)
                Log.d("DatabaseHelper", "new row inserted ");

        }




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {  //drops the previous table and creates new one. onCreate again called.
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        db.execSQL("DROP IF TABLE EXISTS " + gTABLE_NAME);

        onCreate(db);
    }

    public boolean addData(String TaskInfo, String CatInfo, String Duration, String DueDate, String Rem1, String Rem2, String Rem3, String week) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, TaskInfo);  // adding data to the columns
        contentValues.put(COL3,CatInfo);
        contentValues.put(COL4,Duration);
        contentValues.put(COL5,DueDate);
        contentValues.put(COL6,Rem1);
        contentValues.put(COL7,Rem2);
        contentValues.put(COL8,Rem3);
        contentValues.put(COL9,week);
        contentValues.put(COL10,userID);


       // Log.d(TAG, "addData: Adding " + TaskInfo+"##"+DueDate+"##"+Rem1+"##"+Rem2+"##"+Rem3 + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Cursor getData(){  // fetching my results in cursor
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+ COL10+ "='" +userID+ "';";
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name +" AND "+COL10 + " = "+userID+ "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor GetWholeData(int pos)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String CorRow= GetCorrectId(pos,db);


        String id=CorRow+"";

        String query= "SELECT * FROM " + TABLE_NAME+
                " Where " +  COL1 + " = "+ id +" AND "+COL10 + " = '"+userID+"';";

        Log.d(TAG, pos+ "GetTime: query: " + query);
        Cursor data= db.rawQuery(query,null);
        return data;
    }

    /**
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     */


    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName +" AND "+COL10 + " = "+userID+ "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    /**
     * Delete from database
     * @param id
     * @param name
     */
    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id +" AND "+COL10 + " = "+userID+ "'" +
                " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }


    String GetCorrectId(int id, SQLiteDatabase db)
    {
        id++;
        String CorrectRow="";

        int cnt=0;
        Cursor data=getData();
        while(data.moveToNext()) {
            cnt++;
            Log.d("DatabaseHelper", "id: "+id+" Results "+data.getString(0)+ " "+data.getString(1)+ " "+data.getString(2)+ " "
                    +data.getString(3)+ " "+data.getString(4)+ " "+data.getString(5)+ " "+data.getString(6)+" "+
                    data.getString(7));

            if(cnt==id)
            {
                CorrectRow= data.getString(0);
                break;
            }

        }

        return CorrectRow;
    }


    public boolean deleteRecord(int id, int isDone)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Delete_row=GetCorrectId(id,db);

        ContentValues contentValues = new ContentValues();


/*
        db.delete("MyTaskTable5", null, null);
        db.delete("graphTable", null, null);
        Log.d("DatabaseHelper", "Successfully deleted");*/


      /*  String createTable2 = "CREATE TABLE "+ gTABLE_NAME+
                "( " +
                gCOL1+" TEXT, "+
                gCOL2+" TEXT, "+
                gCOL3+" TEXT )";

        db.execSQL(createTable2);
*/

     //   db.execSQL("delete from "+ gTABLE_NAME);

      /*  for(int i=0;i<4;i++)
        {

            contentValues.put(gCOL1,i);
            contentValues.put(gCOL2,2);
            contentValues.put(gCOL3,3);
            long result = db.insert(gTABLE_NAME, null, contentValues);

            if(result!=-1)
                Log.d("DatabaseHelper", "new row inserted ");

        }*/





        AddRowToGraph(Delete_row,isDone);

        Log.d("DatabaseHelper", "row: "+Delete_row);
        return  db.delete(TABLE_NAME, COL1+ " = " +Delete_row +" AND "+COL10 + " = '"+userID+"'", null )>0;


    }

    void AddRowToGraph(String row, int isDone)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String query= "SELECT  *  FROM " + TABLE_NAME+
                " Where " +  COL1 + " = "+ row +" AND "+COL10 + " = '"+userID+"';";

        Cursor data= db.rawQuery(query,null);

        String week="";
        String name="";
        String Due="";


        while (data.moveToNext())
        {
            week=data.getString(8);
            name= data.getString(1);
            Due= data.getString(4);
        }

        Log.d("DatabaseHelper", "due: "+ Due + " week: "+week+" "+name);




        query= "Select * from "+gTABLE_NAME+" where "+
                gCOL1+ " = "+ week + ";";

        Cursor data2= db.rawQuery(query,null);


        int DoneNum=0, NotDoneNum=0;

        //From here to
       String Weekindex="";
        while (data2.moveToNext())
        {
            Weekindex= data2.getString(0);
            DoneNum= data2.getInt(1);
            NotDoneNum=data2.getInt(2);
            Log.d("DatabaseHelper", "Week:-> -> "+Weekindex+ " "+DoneNum+ " "+ NotDoneNum);
        }


        if(isDone==1)
            DoneNum++;
        else if(isDone==0) NotDoneNum++;

        //here to clear all data

        ContentValues cv = new ContentValues();
        cv.put(gCOL2,DoneNum);
        cv.put(gCOL3,NotDoneNum);

        int r=db.update(gTABLE_NAME, cv, "Week= "+week, null);

        if(r==-1)
            Log.d("DatabaseHelper", "Not updated");
       else Log.d("DatabaseHelper", "week: "+week+ " Not Done: "+NotDoneNum+ " Done: "+DoneNum);





    }



    public ArrayList<String> RetGraphData()
    {

        ArrayList<String> arrayList= new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query= "Select * from "+gTABLE_NAME;
        Cursor data2= db.rawQuery(query,null);

        int DoneNum,NotDoneNum;
        String weekindex;
        while (data2.moveToNext())
        {
            weekindex=data2.getString(0);
            DoneNum= data2.getInt(1);
            arrayList.add(Integer.toString(DoneNum));
            NotDoneNum=data2.getInt(2);
            arrayList.add(Integer.toString(NotDoneNum));
             Log.d("DatabaseHelper", "Week:-> -> "+weekindex+ " "+DoneNum+ " "+ NotDoneNum);
        }

        return arrayList;

    }



    public  void AddTaskToFeed(int id, Context mContext)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String CorRow= GetCorrectId(id,db);

        String query = "SELECT * FROM " + TABLE_NAME+
                " Where " +  COL1 + " = "+ CorRow +" AND "+COL10 + " = '"+userID+"';";

        Log.d("DatabaseHelper", "que:  "+query);
        Cursor data= db.rawQuery(query,null);

        PostInfoClass postInfo=null;
        String publisher, postDetails, catagory, postdate,duration;
        while (data.moveToNext())
        {
            publisher="Anonymous";
            postDetails= data.getString(1);
            catagory= data.getString(2);
            duration=data.getString(3);
            postdate= data.getString(4);

            postInfo=new PostInfoClass(publisher, postDetails, catagory,  duration, postdate);
        }

        Firebase.setAndroidContext(mContext);
        FDataBaseRef= new Firebase("https://intellitasker-38a1b.firebaseio.com/");

        Firebase UserRef =FDataBaseRef.child("NewsFeed");  //1st layer
        Firebase PostDivisionRef= UserRef.child("TaskFeedClass");  //2nd layer

        String UniquePostString= PostDivisionRef.push().getKey();   // 3rd layer
        Firebase UniquePostRef= PostDivisionRef.child(UniquePostString);
        Firebase PostDetailsRef, CatagoryRef, DateRef, PublisherRef, DurationRef;

        PublisherRef=UniquePostRef.child("Publisher");
        PublisherRef.setValue(postInfo.Publisher);

        PostDetailsRef= UniquePostRef.child("PostDetails");
        PostDetailsRef.setValue(postInfo.PostDetails);

        CatagoryRef= UniquePostRef.child("Catagory");
        CatagoryRef.setValue(postInfo.Catagory);

        DurationRef= UniquePostRef.child("Duration");
        DurationRef.setValue(postInfo.Duration);

        DateRef= UniquePostRef.child("PostedDate");
        DateRef.setValue(postInfo.PostedDate);



        Log.d("ShareResourceActivity", "I think I added: "+postInfo.PostDetails+" "+postInfo.Catagory);


    }

}
