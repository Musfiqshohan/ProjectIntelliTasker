package info.androidhive.intellitasker.Todo_List_Package;
import info.androidhive.intellitasker.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class GraphDrawActivity extends AppCompatActivity {

    BarChart barChart;

    DatabaseHelper mDatabaseHelper;
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);


        mDatabaseHelper= new DatabaseHelper(this);

        arrayList= mDatabaseHelper.RetGraphData();

        barChart = (BarChart) findViewById(R.id.bargraph);
        ArrayList<BarEntry> barEntries= new ArrayList<>();


      /*  val= Integer.parseInt(arrayList.get(0));
        barEntries.add(new BarEntry(val,0));
        val= Integer.parseInt(arrayList.get(1));
        barEntries.add(new BarEntry(val,1));

        barEntries.add(new BarEntry(22,2));
        barEntries.add(new BarEntry(44f,3));

        barEntries.add(new BarEntry(44f,4));
        barEntries.add(new BarEntry(55f,5));

        barEntries.add(new BarEntry(44f,6));
        barEntries.add(new BarEntry(12f,7));
*/




        for(int i=0;i<arrayList.size();i++)
        {
            int val= Integer.parseInt(arrayList.get(i));
            barEntries.add(new BarEntry(val,i));

            Log.d("GraphDrawActivity", "at index "+i+ " val"+val);

        }


        BarDataSet barDataSet= new BarDataSet(barEntries, "Dates");
        barDataSet.setColors(new int[] {Color.GREEN, Color.RED, Color.GREEN, Color.RED,
                                        Color.GREEN, Color.RED, Color.GREEN, Color.RED});


        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("FirstWeek");
        theDates.add("FirstWeek");
        theDates.add("SecondWeek");
        theDates.add("SecondWeek");
        theDates.add("ThirdWeek");
        theDates.add("ThirdWeek");
        theDates.add("FourthWeek");
        theDates.add("FourthWeek");


        BarData theData = new BarData(theDates, barDataSet);
        barChart.setData(theData);

        barChart.setTouchEnabled(false);
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(true);

    }


    public void onBackPressed() {
        finish();
    }




}