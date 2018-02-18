package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    private ImageView ingredientsIv;
    private TextView tvAlsoKnown;
    private TextView tvOrigin;
    private TextView tvDescription;
    private TextView tvIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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

        initializeViews();

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        setTitle(sandwich.getMainName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        loadImage(sandwich.getImage());

        this.tvDescription.setText(sandwich.getDescription());

        this.tvOrigin.setText(sandwich.getPlaceOfOrigin());

        StringBuilder alsoKnownAs = new StringBuilder();

        for(int i = 0; i < sandwich.getAlsoKnownAs().size(); i++){
            alsoKnownAs.append(sandwich.getAlsoKnownAs().get(i)).append("\n");
        }

        if(alsoKnownAs.length() > 1) {
            this.tvAlsoKnown.setText(alsoKnownAs.substring(0, alsoKnownAs.length() - 1));
        }

        StringBuilder ingredients = new StringBuilder();

        for(int i = 0; i < sandwich.getIngredients().size(); i++){
            ingredients.append(sandwich.getIngredients().get(i)).append("\n");
        }

        if(ingredients.length() > 1) {
            this.tvIngredients.setText(ingredients.substring(0, ingredients.length() - 1));
        }

    }

    private void loadImage(String image){
        Picasso.with(this)
                .load(image)
                .into(this.ingredientsIv);
    }

    private void initializeViews(){
        this.ingredientsIv = findViewById(R.id.image_iv);
        this.tvAlsoKnown = findViewById(R.id.also_known_tv);
        this.tvOrigin = findViewById(R.id.origin_tv);
        this.tvDescription = findViewById(R.id.description_tv);
        this.tvIngredients = findViewById(R.id.ingredients_tv);
    }

}
