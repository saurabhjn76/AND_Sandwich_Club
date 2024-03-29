package com.udacity.sandwichclub;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private  ImageView ingredientsIv;
    private TextView placeOforiginTv;
    private TextView alsoKnownTv;
    private TextView descriptionTv;
    private TextView ingredientTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

       ingredientsIv = findViewById(R.id.image_iv);
       placeOforiginTv = findViewById(R.id.origin_tv);
       alsoKnownTv  = findViewById(R.id.also_known_tv);
       descriptionTv = findViewById(R.id.description_tv);
       ingredientTv = findViewById(R.id.ingredients_tv);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setDisplayShowHomeEnabled(true);



        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        Log.d("AfterParsing", sandwich.toString());
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        Log.d("image", sandwich.getImage());
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if(sandwich==null){
            Log.e("Error:","Sandwich data not parsed");
            return ;
        }
        descriptionTv.setText(sandwich.getDescription());
        placeOforiginTv.setText(sandwich.getPlaceOfOrigin());
        ingredientTv.setText( TextUtils.join("\n",sandwich.getIngredients()));
        alsoKnownTv.setText( TextUtils.join("\n", sandwich.getAlsoKnownAs()));




    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
