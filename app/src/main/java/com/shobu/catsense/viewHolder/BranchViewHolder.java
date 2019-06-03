package com.shobu.catsense.viewHolder;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shobu.catsense.R;
import com.shobu.catsense.model.StoreBranch;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class BranchViewHolder extends ChildViewHolder
{
    private TextView branchNameText;

    public BranchViewHolder(View itemView) {
        super(itemView);
        branchNameText = itemView.findViewById(R.id.txt_branch_name);
    }

    public void bind(final StoreBranch storeBranch)
    {
        branchNameText.setText(storeBranch.getBranchName());
        branchNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), storeBranch.getBranchName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
