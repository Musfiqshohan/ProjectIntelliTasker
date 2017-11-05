package info.androidhive.intellitasker.NewsFeedPackage;
import info.androidhive.intellitasker.R;
/**
 * Created by musfiq on 10/3/17.
 */

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

  //  public  int cnt=0;

    ArrayList<String> titles,details;
    ViewHolder temp;




   RecyclerAdapter()
   {
       titles=new ArrayList<>();
       details=new ArrayList<>();

   }



    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail =(TextView)itemView.findViewById(R.id.item_detail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
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
                .inflate(R.layout.card_layout_newsfeed, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles.get(i));
        viewHolder.itemDetail.setText(details.get(i));

        temp=viewHolder;
        Log.d("RecyclerAdapter", "Adding: "+ titles.get(i)+ " "+ details.get(i));

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }


    public void updatePost(PostInfoClass postInfo) // Adding any new item to recyclerview
    {
        String str1= postInfo.Publisher + "has shared a post on "+postInfo.Catagory+" on "+postInfo.PostedDate;
        String str2= postInfo.PostDetails;
        titles.add(str1);
        details.add(str2);

        Log.d("RecyclerAdapter", "Adding: "+str1+" "+str2);
        //onBindViewHolder(temp,titles.size()-1);  //Again calling Onbindviewholder to add new data
    }

    public  void updatePost2(TaskFeedPostInfo Taskinfo)
    {
        String str1= Taskinfo.Publisher + " has is spending "+Taskinfo.Duration+" on "+Taskinfo.Catagory;
        String str2= Taskinfo.PostDetails;
        titles.add(str1);
        details.add(str2);

    }

    public void ClearList()
    {
        titles.clear();
        details.clear();
    }


}