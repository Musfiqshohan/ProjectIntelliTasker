package info.androidhive.intellitasker.Group_Task_Package;

/**
 package com.delaroystudios.carddemo;

 /**
 * Created by Shade on 5/9/2016.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import info.androidhive.intellitasker.R;

public class groupTaskAdapter extends RecyclerView.Adapter<groupTaskAdapter.ViewHolder> {
    private String[] titles = {"signin",
            "firebase",
            "create event",
            "group task",
            "meeting",
            "alarm",
            "settings",
            "profile"
    };
    private String[] start = {
            "12.10.2017 8.00 pm",
            "15.10.2017 7.00 pm",
            "18.10.2017 9.00 pm",
            "20.10.2017 6.00 pm",
            "21.10.2017 7.00 pm",
            "23.10.2017 12.00 pm",
            "01.11.2017 1.00 pm",
            "07.11.2017 3.00 pm",
    };
    private String[] end = {"12.10.2017 12.00 am",
            "16.10.2017 8.00 pm",
            "19.10.2017 10.00 pm",
            "21.10.2017 7.00 pm",
            "21.10.2017 7.00 pm",
            "24.10.2017 3.00 pm",
            "01.10.2017 5.00 pm",
            "07.10.2017 8.00 pm",
    };


    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public TextView itemTitle;
        public TextView itemstarttime;
        public TextView itemendtime;
        public Button itembutton;
        public ViewHolder(final View itemView) {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemstarttime =
                    (TextView)itemView.findViewById(R.id.item_start);
            itemendtime =
                    (TextView) itemView.findViewById(R.id.item_end);
            itembutton =
                    (Button) itemView.findViewById(R.id.donebuton);
            itembutton.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Toast.makeText(v.getContext(),String.valueOf(itemTitle.getText()),Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.groupcard_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemstarttime.setText(start[i]);
        viewHolder.itemendtime.setText(end[i]);

    }
    @Override
    public int getItemCount() {
        return titles.length;
    }
}
