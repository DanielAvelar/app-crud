package br.com.AppCrud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ItemDetailView extends AppCompatActivity  {
    TextView listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        Intent intent = getIntent();
        String imageUrl =  intent.getStringExtra("image");
        String name =  intent.getStringExtra("name");
        String description =  intent.getStringExtra("description");
        String amount =  intent.getStringExtra("amount");
        String idProduct =  intent.getStringExtra("id_product");
        String category =  intent.getStringExtra("category");
        String price =  intent.getStringExtra("price");

        ImageView image = findViewById((R.id.image));
        Glide.with(this)
                .load(imageUrl) // Image URL
                .centerCrop() // Image scale type
                .crossFade()
                .override(800, 500) // Resize image
                .placeholder(R.drawable.ic_launcher_background) // Place holder image
                .error(R.drawable.ic_launcher_foreground) // On error image
                .into(image);// ImageView to display image

        listData = findViewById(R.id.name);
        listData.setText("Nome: " + name);

        listData = findViewById(R.id.description);
        listData.setText("Descrição: " + description);

        listData = findViewById(R.id.amount);
        listData.setText("Quantidade: " + amount);

        listData = findViewById(R.id.idProduct);
        listData.setText("Id do Produto: " + idProduct);

        listData = findViewById(R.id.category);
        listData.setText("Categoria: " + category);

        listData = findViewById(R.id.price);
        listData.setText("Preço: " + price);

        //enable back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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