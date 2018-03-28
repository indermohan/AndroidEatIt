package com.dev.inder.androideatit.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.inder.androideatit.R;
import com.dev.inder.androideatit.model.ItemClickListerner;

/**
 * Created by unoiaAndroid on 3/23/2018.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static TextView food_name;
    public static ImageView food_image;

    private ItemClickListerner itemClickListerner;


    public FoodViewHolder(View itemView) {
        super(itemView);
       food_name= itemView.findViewById(R.id.menu_name);
       food_image= itemView.findViewById(R.id.menu_image);

       itemView.setOnClickListener(this);
    }

    public void setItemClickListerner(ItemClickListerner itemClickListerner) {
        this.itemClickListerner = itemClickListerner;
    }

    @Override
    public void onClick(View view) {
        itemClickListerner.Onclick(view,getAdapterPosition(),false);

    }
}
