package info.androidhive.intellitasker.Todo_List_Package;
import info.androidhive.intellitasker.R;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//This class is for creating a task where input is:  task info , date and time
// We can set duetime for the task and add 3 reminders for that reason this class has 3 reminder buttons
public class EditFieldClass extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button b_datepick, b_reminder1, b_reminder2, b_reminder3;
    int Which_button_flag = -1;

    int year, day, month, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    long StartTime, EndingTime;
    String TaskInfoMessage, CatInfoMessage, date_string_format, dateStringOnButton, WeekNo;

    String Duration, DueDatetime, Reminder1time, Reminder2time, Reminder3time;

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_layout);


        mDatabaseHelper = new DatabaseHelper(this);

        b_datepick = (Button) findViewById(R.id.b_pick);
        b_reminder1 = (Button) findViewById(R.id.b_reminder1);
        b_reminder2 = (Button) findViewById(R.id.b_reminder2);
        b_reminder3 = (Button) findViewById(R.id.b_reminder3);


        b_datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);




                DatePickerDialog datePickerDialog = new DatePickerDialog(EditFieldClass.this, EditFieldClass.this,
                        year, month, day);
                datePickerDialog.show();


                Which_button_flag = 0;
                Log.d("EditFieldClass", "DayWeek: "+dayFinal);


            }
        });

        b_reminder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditFieldClass.this, EditFieldClass.this,
                        year, month, day);
                datePickerDialog.show();

                Which_button_flag = 1;


            }
        });
        b_reminder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditFieldClass.this, EditFieldClass.this,
                        year, month, day);
                datePickerDialog.show();

                Which_button_flag = 2;


            }
        });
        b_reminder3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditFieldClass.this, EditFieldClass.this,
                        year, month, day);
                datePickerDialog.show();



                Which_button_flag = 3;


            }
        });


    }


    public void SaveButtonClicked(View v) {
        TaskInfoMessage = ((EditText) findViewById(R.id.TaskInfoField)).getText().toString();
        CatInfoMessage = ((EditText) findViewById(R.id.CatField)).getText().toString();
        Duration = ((EditText) findViewById(R.id.DurField)).getText().toString();

        if (TaskInfoMessage.equals("")) {

        } else {


            AddDataToDatabase();  //When user clicks on save button then my whole task data will add to firebase


            Intent intent = new Intent();
            intent.putExtra(Intent_Constants.INTENT_MESSAGE_FIELD1, TaskInfoMessage);
            intent.putExtra(Intent_Constants.INTENT_MESSAGE_FIELD2, DueDatetime);
            setResult(Intent_Constants.INTENT_RESULT_CODE, intent);
            finish();
        }

    }

    //This method is for  picking Date
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1 + 1;
        dayFinal = i2;


        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);


        TimePickerDialog timePickerDialog = new TimePickerDialog(EditFieldClass.this, EditFieldClass.this,
                hour, minute, android.text.format.DateFormat.is24HourFormat(this));

        timePickerDialog.show();

    }


    //This method  is for  picking time
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        // Log.d("EditFieldClass", "DayWeek: "+day);
        // StartTime = i * 60 + i1;


        hourFinal = i;
        minuteFinal = i1;
        int hourFinal2 = i;


        String am_pm;
        if (hourFinal >= 12) {
            am_pm = "pm";
            if (hourFinal > 12) hourFinal %= 12;
        } else {
            am_pm = "am";
            if (hourFinal == 0) hourFinal = 12;
        }


        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date MyDate = null;

        try {
            MyDate = newDateFormat.parse(dayFinal + "/" + monthFinal + "/" + yearFinal);
            newDateFormat.applyPattern("EEEE d MMM yyyy");
            String MyDatex = newDateFormat.format(MyDate);
            dateStringOnButton = "On " + MyDatex + " at " + hourFinal + " " + minuteFinal + " " + am_pm;
            SetButtonText(dateStringOnButton);  //Setting the chosen time on button to show as String


            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date Curdate = new Date();  //The current date


            date_string_format = " " + yearFinal + "-" + monthFinal + "-" + dayFinal + " " + hourFinal2 + ":" + minuteFinal + ":" + "00.0";
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date nextDate = dt.parse(date_string_format);  //The Due date for Task

            long seconds = (nextDate.getTime() - Curdate.getTime()) / 1000;
            StartTime = nextDate.getTime();  //getting the difference between two dates in seconds


            String paraText = ((EditText) findViewById(R.id.TaskInfoField)).getText().toString();
            Toast.makeText(this, "msg:" + paraText, Toast.LENGTH_LONG).show();


            paraText = "Your Task is: " + paraText + "   " + dateStringOnButton;


            TaskStringClass.SpeakitLoud = paraText;  // This string will spoken instead of default alarm
            int pos = 102;
            Intent alrm = new Intent(EditFieldClass.this, alarmRing.class);  //setting an alarm
            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), Which_button_flag, alrm, 0);
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + seconds * 1000, pi);


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    void SetButtonText(String str) { // Setting times on buttons
        if (Which_button_flag == 0) {
            {
                b_datepick.setText("Due: " + str);
                DueDatetime = str;
                WeekNo = "" + (dayFinal / 7);
                Log.d("EditFieldClass", "DayWeek: "+dayFinal);

            }
        } else if (Which_button_flag == 1) {
            b_reminder1.setText("R1: " + str);
            Reminder1time = str;
        } else if (Which_button_flag == 2) {
            b_reminder2.setText("R2: " + str);
            Reminder2time = str;
        } else if (Which_button_flag == 3) {
            b_reminder3.setText("R3: " + str);
            Reminder3time = str;
        }

    }

    void AddDataToDatabase()  //Adding data in offline sqlite database
    {
        // Log.d("EditFieldClass", "1:"+TaskInfoMessage+"  2: "+DueDatetime+"  3: "+Reminder1time+"  4: "+Reminder2time+"  5: "+Reminder3time);
        boolean insertData = mDatabaseHelper.addData(TaskInfoMessage, CatInfoMessage, Duration, DueDatetime, Reminder1time, Reminder2time, Reminder3time, WeekNo);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong during inserting");
        }


        AddDataforMeetFrnd();

    }


    void AddDataforMeetFrnd() {
        if (Duration.length() == 0)
            Duration = "" + 2;

        Log.d("EditFieldClass", "Dur " + Duration);

        EndingTime = Integer.parseInt(Duration);
        EndingTime = EndingTime * 60*60*1000 + StartTime;

        Firebase.setAndroidContext(this);
        Firebase FDataBaseRef = new Firebase("https://intellitasker-38a1b.firebaseio.com/");
        //  Firebase FDataBaseRef = new Firebase("https://intellitasker-38a1b.firebaseio.com/");


        // my code

        String userID="11";
        try {
            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        catch (Exception e)
        {

        }
        if(userID=="11")
        {
            userID = "azxcv";
        }


        Firebase taskRef = FDataBaseRef.child("tasks");

        Firebase UserRef= taskRef.child(userID);
        UserRef.child(TaskInfoMessage+CatInfoMessage+Duration).setValue(StartTime + "#" + EndingTime);


    }


    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}