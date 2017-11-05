package info.androidhive.intellitasker.Group_Task_Package;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.androidhive.intellitasker.R;

import static java.lang.String.valueOf;

/**
 * Created by zaoad on 10/12/17.
 */

public class addMember extends AppCompatActivity {
    int index=0;
    ListView listview;
    EditText addmembertext;
    TextView textview;
    Button addmemberbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.add_member);
       addmembertext=(EditText)findViewById(R.id.addmembertext);
       addmemberbutton=(Button)findViewById(R.id.addmemberButton);
       listview = (ListView)findViewById(R.id.listview);
       final ArrayList<String> arrayList =new ArrayList<String>();
       final ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
       listview.setAdapter(arrayAdapter);
        addmemberbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String member=valueOf(addmembertext.getText());
                if(!member.equals("")) {
                    arrayList.add(index, member);
                    addmembertext.setText("");
                    index++;
                    listview.setAdapter(arrayAdapter);
                }
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String item = (String) listview.getItemAtPosition(position);
                Toast.makeText(addMember.this, "you select: " + item, Toast.LENGTH_LONG).show();
                for(int j=0;j<index;j++) {
                    if (position == j) {
                        Intent i=new Intent(addMember.this,assignTask.class);
                        Bundle b = new Bundle();
                        b.putString("key", item); //Your id
                        i.putExtras(b); //Put your id to your next Intent
                        startActivity(i);
                    }
                }
            }

        });
    }
}
