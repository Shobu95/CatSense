package com.shobu.catsense.viewHolder;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.shobu.catsense.R;
import com.shobu.catsense.model.Store;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class StoreViewHolder extends GroupViewHolder
{
    private TextView storeNameText;
    private ImageView arrowImageView;

    public StoreViewHolder(View itemView) {
        super(itemView);
        storeNameText = itemView.findViewById(R.id.txt_store_name);
        arrowImageView = itemView.findViewById(R.id.img_arrow_down);
    }

    public void bind(Store store)
    {
        storeNameText.setText(store.getStoreName());
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(arrowImageView, "rotation", 180f);
        animation.setDuration(0);
        animation.start();
    }

    private void animateCollapse() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(arrowImageView, "rotation", 0f);
        animation.setDuration(0);
        animation.start();
    }

}
