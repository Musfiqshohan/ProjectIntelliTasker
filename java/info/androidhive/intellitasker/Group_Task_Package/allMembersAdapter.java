package info.androidhive.intellitasker.Group_Task_Package;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.intellitasker.R;

/**
 * Created by zaoad on 10/26/17.
 */
public class allMembersAdapter extends RecyclerView.Adapter<allMembersAdapter.ViewHolder> {
    Context context;
    String groupname;
    ViewHolder temp;
    allMembersAdapter(Context context, String groupname)
    {
        this.context=context;
        this.groupname=groupname;
        titles=new ArrayList<String>();
        details=new ArrayList<String>();
        //titles.add("zaoad");
        //details.add("1 task remaining");
    }
    public static List<String> titles;
    public static List<String> details;
    /*public static String[] details = {"2 incomplete tasks",
            "no incomplete task","next meeting date 12.09.2017"
            };*/
    //hold the elements of the recycler element
    class ViewHolder extends RecyclerView.ViewHolder{
        public int currentItem;
        public TextView itemTitle;
        public TextView itemDetail;
        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail =
                    (TextView)itemView.findViewById(R.id.item_detail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    String titlename=String.valueOf(itemTitle.getText());
                    if(titlename.equals(global.username)) {
                       // Log.d("zaoad task","start");
                        Intent i = new Intent(context, myTaskView.class);
                        Bundle b = new Bundle();
                        b.putString("key", titlename); //Your id
                        b.putString("group", groupname); //Your id
                        i.putExtras(b);
                        i.putExtra("memberlist", (ArrayList<String>) titles);
                        context.startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(context, allTasknameView.class);
                        Bundle b = new Bundle();
                        b.putString("key", titlename); //Your id
                        b.putString("group", groupname); //Your id
                        i.putExtras(b);
                        i.putExtra("memberlist", (ArrayList<String>) titles);
                        context.startActivity(i);
                    }
                }
            });
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.all_members_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles.get(i));
        viewHolder.itemDetail.setText(details.get(i));
        temp=viewHolder;
    }
    @Override
    public int getItemCount() {
        return titles.size();
    }
    ///add a new group to the recycler view
    public void updatePost(String str1, String str2)
    {
        titles.add(str1);
        details.add(str2);
        Log.d("allMembersAdapter", "Adding: "+str1+" "+str2);
        this.notifyDataSetChanged();
        //onBindViewHolder(temp,titles.size()-2);
    }
    public List<String> getmemberlist()
    {
        return titles;
    }
}