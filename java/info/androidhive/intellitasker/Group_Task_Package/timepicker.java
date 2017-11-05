package info.androidhive.intellitasker.Group_Task_Package;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import info.androidhive.intellitasker.R;

public class timepicker extends AppCompatActivity {
    TimePicker simpleTimePicker;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timepicker);
        // initiate the date picker and a button
        simpleTimePicker = (TimePicker) findViewById(R.id.simpleTimePicker);
        submit = (Button) findViewById(R.id.submitButton);
        // perform click event on submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // get the values for day of month , month and year from a date picker
                String hour = "Day = " + simpleTimePicker.getHour();
                String minute = "Month = " +simpleTimePicker.getMinute();
                //String am="am "+simpleTimePicker.get;
                // display the values by using a toast
                Toast.makeText(getApplicationContext(), hour + "\n" + minute+"\n" , Toast.LENGTH_LONG).show();
            }
        });
    }
}