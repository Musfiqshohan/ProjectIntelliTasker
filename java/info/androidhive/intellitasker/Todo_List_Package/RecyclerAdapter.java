package info.androidhive.intellitasker.Todo_List_Package;
import info.androidhive.intellitasker.R;
/**
 * Created by musfiq on 10/3/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

  //  public  int cnt=0;

    ArrayList<String> titles,details;
    ViewHolder temp;

    DatabaseHelper mDatabaseHelper;

    private Context mCtx;


   RecyclerAdapter(Context mCtx)
   {

       this.mCtx=mCtx;
       titles=new ArrayList<>();
       details=new ArrayList<>();
       mDatabaseHelper= new DatabaseHelper(mCtx);
   }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;
        public TextView buttonViewOption;


        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail =(TextView)itemView.findViewById(R.id.item_detail);
            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout_todo, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.itemTitle.setText(titles.get(i));
        viewHolder.itemDetail.setText(details.get(i));


        viewHolder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.d("RecyclerAdapter", "clicking on option"+i);
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mCtx, viewHolder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.EditMenu:


                                Intent intent = new Intent(mCtx, EditFieldClass.class);
                                ((MainActivity)mCtx).startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE);
                                DeleteFromList(i,2);
                                break;

                            case R.id.DoneMenu:
                                DeleteFromList(i,1);
                                Intent intentx = new Intent(mCtx, GraphDrawActivity.class);
                                ((MainActivity)mCtx).startActivityForResult(intentx, Intent_Constants.INTENT_GRAPH_CODE);

                                Toast.makeText(mCtx, "Task finished", Toast.LENGTH_SHORT).show();

                                break;

                            case R.id.DeleteMenu:
                                DeleteFromList(i,0);
                                 intentx = new Intent(mCtx, GraphDrawActivity.class);
                                ((MainActivity)mCtx).startActivityForResult(intentx, Intent_Constants.INTENT_GRAPH_CODE);

                                Toast.makeText(mCtx, "Task not finished", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.PublicMenu:
                                mDatabaseHelper.AddTaskToFeed(i, mCtx);
                                break;

                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();


            }
        });


        temp=viewHolder;
       // Log.d("RecyclerAdapter", "Adding: "+ titles.get(i)+ " "+ details.get(i));

    }


    public  void DeleteFromList(int pos, int isDone)
    {
        Log.d("RecyclerAdapter","in Rec want to delete "+pos);
        boolean r=mDatabaseHelper.deleteRecord(pos,isDone);
        Log.d("RecyclerAdapter" ,"Yes in Recycle, deleted? ->"+r);
        titles.remove(pos);
        details.remove(pos);

        this.notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return titles.size();
    }
    public String getItemDetails(int position) { return titles.get(position); }


    public void updatePost(String MainTask, String Date) // Adding any new item to recyclerview
    {
        String str1= MainTask;
        String str2= Date;
        titles.add(str1);
        details.add(str2);

        Log.d("RecyclerAdapter", "Adding: "+str1+" "+str2);
       // onBindViewHolder(temp,titles.size()-1);  //Again calling Onbindviewholder to add new data
    }

    public void ClearList()
    {
        titles.clear();
        details.clear();

    }



}