package info.androidhive.intellitasker.Group_Task_Package;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.intellitasker.R;

/**
 * Created by zaoad on 10/27/17.
 */
public class allTaskAdapter extends RecyclerView.Adapter<allTaskAdapter.ViewHolder> {

    Context context;
    ViewHolder temp;
    String name,groupname;
    List<String> members;
    allTaskAdapter(Context context, String name, String groupname, List<String> members)
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
        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemfirebaseid = (TextView)itemView.findViewById(R.id.fid);
            itemDetail = (TextView)itemView.findViewById(R.id.item_detail);
            button=(Button)itemView.findViewById(R.id.donebuton);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.all_task_adapter, viewGroup, false);
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
            viewHolder.button.setText("undone");
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
        Log.d("allTaskAdapter", "Adding: "+str1+" "+str2);
        this.notifyDataSetChanged();
        //onBindViewHolder(temp,titles.size()-2);
    }
}