package com.shobu.catsense.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.shobu.catsense.R;
import com.shobu.catsense.model.Brand;

import java.util.ArrayList;

public class BrandsRecyclerAdapter extends RecyclerView.Adapter<BrandsRecyclerAdapter.ViewHolder>
{
    Context context;
    ArrayList<Brand> allBrandsList;
    private final OnItemClickListener itemClickListener;
    private final OnLongClickListener longClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView BrandNameText;


        public ViewHolder(View view)
        {
            super(view);
            BrandNameText = view.findViewById(R.id.txt_brand_name);
        }
    }

    public BrandsRecyclerAdapter(Context context, ArrayList<Brand> allBrandsList,
                                      OnItemClickListener itemClickListener, OnLongClickListener longClickListener)
    {
        this.context = context;
        this.allBrandsList = allBrandsList;
        this.itemClickListener = itemClickListener;
        this.longClickListener = longClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View brandItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_brand, parent, false);
        ViewHolder myViewHolder = new ViewHolder(brandItem);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final Brand brand = allBrandsList.get(position);
        holder.BrandNameText.setText(brand.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(brand);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickListener.onLongClick(brand);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return allBrandsList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Brand brand);
    }

    public interface OnLongClickListener{
        void onLongClick(Brand brand);
    }

}
