package info.androidhive.intellitasker.Group_Task_Package;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import info.androidhive.intellitasker.R;

import static java.lang.String.valueOf;

/**
 * Created by zaoad on 10/12/17.
 */

public class assignTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    String name,tasktitle,tasktime,group,firebaseid;
    int i=0;
    ListView listview;
    int year,day,month,week_day, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    EditText title,duration;
    TextView textview;
    Button addtask,time,durationbutton;
    String groupname,eventtime="",durationtime="";
    List<String> members;
    public void setonfirebase(String name,String group,String title ,String endtime)
    {
        for(int i=0;i<members.size();i++) {
            String username=String.valueOf(members.get(i));
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("grouptasks").child(username);
            DatabaseReference ref1, ref2,ref3;
            ref1 = myRef.child("grouptask");
            ref2 = ref1.child(group);
            ref3=ref2.child(name);
            if(i==0)
            firebaseid = ref3.push().getKey();//give an unique key
            task t = new task(title, endtime, false, firebaseid);
            ref3.child(firebaseid).setValue(t);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        name = b.getString("key");
        group=b.getString("groupname");
        members= (ArrayList<String>) getIntent().getSerializableExtra("memberlist");
        Log.d("name ",name);
        setContentView(R.layout.assign_task);
        title=(EditText)findViewById(R.id.title);
        time=(Button) findViewById(R.id.time);
        addtask=(Button)findViewById(R.id.addtask);
        textview=(TextView) findViewById(R.id.name);
        listview = (ListView)findViewById(R.id.listview);
        final ArrayList<String> arrayList =new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listview.setAdapter(arrayAdapter);
        textview.setText(name);
        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasktitle=valueOf(title.getText());

                    //i++;
                    //tasktime=valueOf(time.getText());
                    //arrayList.add(i, tasktitle + "\n" + eventtime + " duration: " + durationtime + " hours");
                   // i++;
                    //listview.setAdapter(arrayAdapter);

                title.setText("");
                setonfirebase(name,group,tasktitle,eventtime);
                //duration.setText("0");
                ////send group tame and details to allGrouptaskView
                Intent intent= new Intent();
                intent.putExtra("tasktitle",tasktitle);
                intent.putExtra("endtime",eventtime);
                intent.putExtra("firebaseid",firebaseid);
                //intent.putExtra(FeedIntentConstraint.Datecode,curDate);
                setResult(12,intent);
                finish();

            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c= Calendar.getInstance();
                year=  c.get(Calendar.YEAR);
                month=  c.get(Calendar.MONTH);
                day=  c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(assignTask.this, assignTask.this,
                        year,month, day);
                datePickerDialog.show();
            }
        });
    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal=i;
        monthFinal=i1+1;
        dayFinal=i2;
        Calendar c=  Calendar.getInstance();
        hour= c.get(Calendar.HOUR_OF_DAY);
        minute=c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(assignTask.this, assignTask.this,
                hour, minute, android.text.format.DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal=i;
        minuteFinal=i1;
        int hourFinal2=i;
        String am_pm;
        if(hourFinal>=12){  am_pm="pm";  if(hourFinal>12) hourFinal%=12; }
        else { am_pm="am"; if(hourFinal==0) hourFinal=12; }
        eventtime=dayFinal+"/"+monthFinal+"/"+yearFinal+"_"+hourFinal+":"+minuteFinal+" "+am_pm;
        Toast.makeText(assignTask.this,eventtime,Toast.LENGTH_LONG).show();
    }
}
