package com.dev.inder.androideatit;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.dev.inder.androideatit.model.Food;
import com.dev.inder.androideatit.model.ItemClickListerner;
import com.dev.inder.androideatit.viewholder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class FoodDetail extends AppCompatActivity {

    TextView food_name,food_price,desciption;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btncart;
    ElegantNumberButton numberButton;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);
        food_image = findViewById(R.id.food_image);
        desciption = findViewById(R.id.food_description);

        collapsingToolbarLayout=findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpanededAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        recyclerView = findViewById(R.id.recyclerview_similar_item);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        getDeailFood(getIntent().getStringExtra("foodId"));
        loadListFood(getIntent().getStringExtra("categoryid"));
    }
    private void loadListFood(final String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class, R.layout.menu_item, FoodViewHolder.class, foods.orderByChild("MenuId").equalTo(categoryId)) {

            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Glide.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);
                final Food local = model;
                viewHolder.setItemClickListerner(new ItemClickListerner() {
                    @Override
                    public void Onclick(View view, int position, boolean isLongClick) {
                        Intent foodDeatil = new Intent(getApplication(), FoodDetail.class);
                        foodDeatil.putExtra("foodId", adapter.getRef(position).getKey());
                        foodDeatil.putExtra("categoryid", categoryId);
                        startActivity(foodDeatil);

                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void getDeailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);

                Glide.with(getBaseContext()).load(food.getImage()).into(food_image);

                collapsingToolbarLayout.setTitle(food.getName());

                food_price.setText(food.getPrice());

                food_name.setText(food.getName());

                desciption.setText(food.getDescription());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
