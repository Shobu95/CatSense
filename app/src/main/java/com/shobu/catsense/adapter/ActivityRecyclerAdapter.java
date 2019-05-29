package com.shobu.catsense.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shobu.catsense.R;
import com.shobu.catsense.model.Activity;
import com.shobu.catsense.model.Activity;

import java.util.ArrayList;

public class ActivityRecyclerAdapter extends RecyclerView.Adapter<ActivityRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<Activity> allActivityList;
    private final OnItemClickListener itemClickListener;
    private final OnLongClickListener longClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ActivityNameText;


        public ViewHolder(View view) {
            super(view);
            ActivityNameText = view.findViewById(R.id.txt_activity_name);
        }
    }

    public ActivityRecyclerAdapter(Context context, ArrayList<Activity> allActivityList,
                                   OnItemClickListener itemClickListener, OnLongClickListener longClickListener) {
        this.context = context;
        this.allActivityList = allActivityList;
        this.itemClickListener = itemClickListener;
        this.longClickListener = longClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View activityItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_activity, parent, false);
        ViewHolder myViewHolder = new ViewHolder(activityItem);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Activity activity = allActivityList.get(position);
        holder.ActivityNameText.setText(activity.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(activity);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickListener.onLongClick(activity);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return allActivityList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Activity activity);
    }

    public interface OnLongClickListener {
        void onLongClick(Activity activity);
    }

}
