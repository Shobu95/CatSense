package com.shobu.catsense.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shobu.catsense.R;
import com.shobu.catsense.model.Store;
import com.shobu.catsense.model.StoreBranch;
import com.shobu.catsense.viewHolder.BranchViewHolder;
import com.shobu.catsense.viewHolder.StoreViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class StoreExpandableRecyclerAdapter extends ExpandableRecyclerViewAdapter<StoreViewHolder, BranchViewHolder>
{
    public StoreExpandableRecyclerAdapter(List<Store> allStores)
    {
        super(allStores);
    }

    @Override
    public StoreViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_list_item_store, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public BranchViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_store_branch, parent, false);
        return new BranchViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(BranchViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final StoreBranch storeBranch = (StoreBranch) group.getItems().get(childIndex);
        holder.bind(storeBranch);
    }

    @Override
    public void onBindGroupViewHolder(StoreViewHolder holder, int flatPosition, ExpandableGroup group) {
        final Store store = (Store) group;
        holder.bind(store);
    }
}
