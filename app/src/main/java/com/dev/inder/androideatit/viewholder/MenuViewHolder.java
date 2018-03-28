package com.dev.inder.androideatit.viewholder;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.inder.androideatit.R;
import com.dev.inder.androideatit.model.ItemClickListerner;

/**
 * Created by unoiaAndroid on 3/22/2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static TextView textViewname;
    public static ImageView imageView;

    private ItemClickListerner itemClickListerner;


    public MenuViewHolder(View itemView) {
        super(itemView);

        textViewname = itemView.findViewById(R.id.menu_name);
        imageView = itemView.findViewById(R.id.menu_image);

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
