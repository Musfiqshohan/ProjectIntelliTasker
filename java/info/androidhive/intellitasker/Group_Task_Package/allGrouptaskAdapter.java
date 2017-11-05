package info.androidhive.intellitasker.Group_Task_Package;
/**
 * Created by Shade on 5/9/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class allGrouptaskAdapter extends RecyclerView.Adapter<allGrouptaskAdapter.ViewHolder> {

    Context context;
    ViewHolder temp;
    allGrouptaskAdapter(Context context)
    {
        this.context=context;
        titles=new ArrayList<String>();
        details=new ArrayList<String>();
        //titles.add("App");
        //details.add("1 task remaining");
    }
    public static List<String>titles;
    public static List<String> details;
    /*public static String[] details = {"2 incomplete tasks",
            "no incomplete task","next meeting date 12.09.2017"
            };*/
    //hold the elements of the recycler element
    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public TextView itemTitle;
        public TextView itemDetail;
        public Button button;
        public ViewHolder(final View itemView) {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail =
                    (TextView)itemView.findViewById(R.id.item_detail);
            button=(Button) itemView.findViewById(R.id.delbuton);
            button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    String titlesname=String.valueOf(itemTitle.getText());
                    Log.d("check delete ", String.valueOf(itemTitle.getText()));
                    Deletefromlist(getkey(String.valueOf(itemTitle.getText())));
                    /*DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference(global.username);
                    DatabaseReference ref2, ref3, ref4;
                    ref2 = ref1.child("grouptask");
                    ref2.child(titlesname).setValue(null);
                    /*ref2.child(titlesname).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Result will be holded Here
                            int i=0;
                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                String membersname=dsp.getKey();
                                Log.d("hey there ",membersname);
                                DatabaseReference ref5 = FirebaseDatabase.getInstance().getReference(membersname);
                                DatabaseReference ref6, ref3, ref4;
                                ref6 = ref5.child("grouptask");
                                if(!membersname.equals("zaoad"))
                                ref6.child(titlesname).removeValue();

                            }
                            //adapter.Removefirst();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    ref2.child(titlesname).removeValue();*/

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent i = new Intent(context, AllMembersView.class);
                    Bundle b = new Bundle();
                    String titlename=String.valueOf(itemTitle.getText());
                    b.putString("key",titlename); //Your id
                    i.putExtras(b);
                    context.startActivity(i);
                }
            });
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
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
        Log.d("allGrouptaskAdapter", "Adding: "+str1+" "+str2);
        this.notifyDataSetChanged();
        //onBindViewHolder(temp,titles.size()-2);
    }
    public void Deletefromlist(int  pos)
    {
        titles.remove(pos);
        details.remove(pos);
        this.notifyDataSetChanged();
    }
    public int getkey(String itemName)
    {
        for (int i = 0; i < titles.size(); i++)
        {
            String auction = titles.get(i);
            if (itemName.equals(auction))
            {
                return i;
            }
        }
        return -1;
    }
}

