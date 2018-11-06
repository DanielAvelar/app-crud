package br.com.AppCrud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.AppCrud.model.Produto;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ProductAdapter mAdapter;
    private ArrayList<Produto> productList;
    private List<Produto> retorno;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Produtos"); // set the top title

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = getIntent();
                String produtos = i.getStringExtra("produtos");

                try {
                    JSONObject jsonObj = new JSONObject(produtos);

                    // Getting JSON Array node
                    JSONArray product = jsonObj.getJSONArray("Products");

                    retorno = new ArrayList<>();
                    // looping through All Products
                    for (int contProd = 0; contProd < product.length(); contProd++) {
                        JSONObject c = product.getJSONObject(contProd);
                        Gson gson = new Gson();
                        Produto object = gson.fromJson(c.toString(), Produto.class);
                        retorno.add(object);
                    }

                    listView = findViewById(R.id.product_list);
                    productList = new ArrayList<>();

                    for(Produto produto: retorno) {
                        productList.add(produto);
                    }

                    mAdapter = new ProductAdapter(MainActivity.this, productList);
                    listView.setAdapter(mAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(getApplicationContext(),ItemDetailView.class);
                            intent.putExtra("image", productList.get(i).getUrlImage());
                            intent.putExtra("name", productList.get(i).getName());
                            intent.putExtra("description", productList.get(i).getDescription());
                            intent.putExtra("amount", productList.get(i).getAmount());
                            intent.putExtra("id_product", productList.get(i).getIdProduct());
                            intent.putExtra("category", productList.get(i).getCategory());
                            intent.putExtra("price", productList.get(i).getPrice());
                            startActivity(intent);
                        }
                    });

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            }
        }, 3000);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("Bem vindo(a)!");
        alertDialogBuilder
                .setMessage("Aplicativo CRUD")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
