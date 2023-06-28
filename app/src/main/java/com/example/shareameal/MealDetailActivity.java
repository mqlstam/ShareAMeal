package com.example.shareameal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
public class MealDetailActivity extends AppCompatActivity {

    private ImageView image;
    private TextView name;
    private TextView description;

    private TextView price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);


        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String mealName = intent.getStringExtra("name");
        String mealDescription = intent.getStringExtra("description");
        String imageUrl = intent.getStringExtra("imageUrl");
        double mealPrice = intent.getDoubleExtra("price", 0);

        Picasso.get().load(imageUrl).into(image);
        name.setText(mealName);
        description.setText(mealDescription);
        price.setText(String.valueOf(mealPrice));   }
}


