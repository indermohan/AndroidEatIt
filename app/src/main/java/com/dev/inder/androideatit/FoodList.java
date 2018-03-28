package com.dev.inder.androideatit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.dev.inder.androideatit.model.Food;
import com.dev.inder.androideatit.model.ItemClickListerner;
import com.dev.inder.androideatit.viewholder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId;
    LinearLayoutManager layoutManager;

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_food_list);

        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");

        recyclerView = findViewById(R.id.recyclerview_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

            categoryId = getIntent().getStringExtra("CategoryId");
                loadListFood(categoryId);

    }

    private void loadListFood(final String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,R.layout.menu_item,FoodViewHolder.class,foodList.orderByChild("MenuId").equalTo(categoryId)) {

            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                    viewHolder.food_name.setText(model.getName());
                Glide.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);

                final Food local = model;

                viewHolder.setItemClickListerner(new ItemClickListerner() {
                    @Override
                    public void Onclick(View view, int position, boolean isLongClick) {
                        Intent foodDeatil = new Intent(getApplication(),FoodDetail.class);
                        foodDeatil.putExtra("foodId",adapter.getRef(position).getKey());
                        foodDeatil.putExtra("categoryid",categoryId);
                        startActivity(foodDeatil);

                    }
                });
            }
        };

        //setAdapter
        Log.d("tag",""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
    }
}
