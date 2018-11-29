package br.com.AppCrud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

public class ItemEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);

        Intent intent = getIntent();
        String session = intent.getStringExtra("session");
        String imageUrl = intent.getStringExtra("image");
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String amount = intent.getStringExtra("amount");
        String idProduct = intent.getStringExtra("id_product");
        String category = intent.getStringExtra("category");
        String price = intent.getStringExtra("price");

        EditText listData = findViewById(R.id.urlImage);
        listData.setText(imageUrl);

        listData = findViewById(R.id.name);
        listData.setText(name);

        listData = findViewById(R.id.description);
        listData.setText(description);

        listData = findViewById(R.id.amount);
        listData.setText(amount);

        listData = findViewById(R.id.idProduct);
        listData.setText(idProduct);

        listData = findViewById(R.id.category);
        listData.setText(category);

        listData = findViewById(R.id.price);
        listData.setText(price);

        //enable back Button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    //getting back to listView
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}