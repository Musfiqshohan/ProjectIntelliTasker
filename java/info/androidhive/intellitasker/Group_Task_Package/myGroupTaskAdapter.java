package info.androidhive.intellitasker.Group_Task_Package;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.intellitasker.R;
import info.androidhive.intellitasker.Todo_List_Package.EditFieldClass;

/**
 * Created by zaoad on 10/27/17.
 */
public class myGroupTaskAdapter extends RecyclerView.Adapter<myGroupTaskAdapter.ViewHolder> {

    Context context;
    ViewHolder temp;
    String name,groupname;
    List<String> members;
    myGroupTaskAdapter(Context context,String name,String groupname,List<String> members)
    {
        this.context=context;
        this.name=name;
        this.groupname=groupname;
        this.members=members;
        titles=new ArrayList<String>();
        details=new ArrayList<String>();
        firebaseid=new ArrayList<String>();
        firebasebutton=new ArrayList<Boolean>();
        //titles.add("task");
        //details.add("1 task remaining");
        //firebaseid.add("nothinggggl");
    }
    public static List<String> titles;
    public static List<String> details;
    public static List<String> firebaseid;
    public static List<Boolean>firebasebutton;
    /*public static String[] details = {"2 incomplete tasks",
            "no incomplete task","next meeting date 12.09.2017"
            };*/
    //hold the elements of the recycler element
    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public TextView itemTitle;
        public TextView itemDetail,itemfirebaseid;
        public Button button;
        public Button addtotask;
        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemfirebaseid = (TextView)itemView.findViewById(R.id.fid);
            itemDetail = (TextView)itemView.findViewById(R.id.item_detail);
            button=(Button)itemView.findViewById(R.id.donebuton);
            addtotask=(Button)itemView.findViewById(R.id.addbuton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Log.d("done button click",button.getText()+"#");
                    if(button.getText().equals("undo")) {
                        button.setTextColor(Color.BLUE);
                        button.setText("done");
                        String fid = String.valueOf(itemfirebaseid.getText());
                        for (int i = 0; i < members.size(); i++) {
                            String username = String.valueOf(members.get(i));
                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("grouptasks").child(username);
                            DatabaseReference ref1, ref2, ref3;
                            ref1 = myRef.child("grouptask");
                            ref2 = ref1.child(groupname);
                            ref3 = ref2.child(name);//give an unique key
                            Log.d("alltask e likha ", username + " " + fid);
                            String item = String.valueOf(itemTitle.getText());
                            String endtime = String.valueOf(itemDetail.getText());
                            task t = new task(item, endtime, false, fid);
                            ref3.child(fid).setValue(t);
                        }
                    }
                    else{
                        button.setTextColor(Color.BLACK);
                        button.setText("undo");
                        //button.setClickable(false);
                        String fid = String.valueOf(itemfirebaseid.getText());
                        for (int i = 0; i < members.size(); i++) {
                            String username = String.valueOf(members.get(i));
                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("grouptasks").child(username);
                            DatabaseReference ref1, ref2, ref3;
                            ref1 = myRef.child("grouptask");
                            ref2 = ref1.child(groupname);
                            ref3 = ref2.child(name);//give an unique key
                            Log.d("alltask e likha ", username + " " + fid);
                            String item = String.valueOf(itemTitle.getText());
                            String endtime = String.valueOf(itemDetail.getText());
                            task t = new task(item, endtime, true, fid);
                            ref3.child(fid).setValue(t);
                        }
                    }
                }
            });
            addtotask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, EditFieldClass.class);
                    context.startActivity(i);
                }
            });
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_grouptask_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemfirebaseid.setText(firebaseid.get(i));
        viewHolder.itemTitle.setText(titles.get(i));
        viewHolder.itemDetail.setText(details.get(i));
        if(!firebasebutton.get(i))
        {
            viewHolder.button.setText("done");
            viewHolder.button.setTextColor(Color.BLUE);
        }
        else
        {
            viewHolder.button.setText("undo");
            viewHolder.button.setTextColor(Color.BLACK);
        }
        temp=viewHolder;
    }
    @Override
    public int getItemCount() {
        return titles.size();
    }
    ///add a new group to the recycler view
    public void updatePost(String str1, String idm,String str2,boolean flag)
    {
        firebaseid.add(idm);
        titles.add(str1);
        details.add(str2);
        firebasebutton.add(flag);
        Log.d("myGroupTaskAdapter", "Adding: "+str1+" "+str2);
        this.notifyDataSetChanged();
        //onBindViewHolder(temp,titles.size()-2);
    }
}