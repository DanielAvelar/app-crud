package br.com.AppCrud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

import br.com.AppCrud.model.ReturnServices;
import br.com.AppCrud.service.ProductsService;

public class ItemDetail extends AppCompatActivity {
    private String idProduct;
    private String session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_item);

        Intent intent = getIntent();
        session = intent.getStringExtra("session");
        final String imageUrl = intent.getStringExtra("image");
        final String name = intent.getStringExtra("name");
        final String description = intent.getStringExtra("description");
        final String amount = intent.getStringExtra("amount");
        idProduct = intent.getStringExtra("id_product");
        final String category = intent.getStringExtra("category");
        final String price = intent.getStringExtra("price");

        ImageView image = findViewById((R.id.image));
        Glide.with(this)
                .load(imageUrl) // Image URL
                .centerCrop() // Image scale type
                .crossFade()
                .override(800, 500) // Resize image
                .placeholder(R.drawable.ic_launcher_background) // Place holder image
                .error(R.drawable.ic_launcher_foreground) // On error image
                .into(image);// ImageView to display image

        TextView listData = findViewById(R.id.name);
        listData.setText(getString(R.string.lbl_nome, name));

        listData = findViewById(R.id.description);
        listData.setText(getString(R.string.lbl_descricao, description));

        listData = findViewById(R.id.amount);
        listData.setText(getString(R.string.lbl_quantidade, amount));

        listData = findViewById(R.id.idProduct);
        listData.setText(getString(R.string.lbl_id_produto, idProduct));

        listData = findViewById(R.id.category);
        listData.setText(getString(R.string.lbl_categoria, category));

        listData = findViewById(R.id.price);
        listData.setText(getString(R.string.lbl_preco, price));

        //enable back Button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Button btnEdit = findViewById(R.id.btn_edit);
        btnEdit.setMovementMethod(LinkMovementMethod.getInstance());
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetail.this, ItemEdit.class);
                intent.putExtra("session", session);
                intent.putExtra("image", imageUrl);
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                intent.putExtra("amount", amount);
                intent.putExtra("id_product", idProduct);
                intent.putExtra("category", category);
                intent.putExtra("price", price);
                startActivity(intent);
            }
        });

        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setMovementMethod(LinkMovementMethod.getInstance());
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ItemDetail.this);
                alertDialogBuilder.setTitle("Alerta");
                alertDialogBuilder
                        .setMessage("Tem certeza que deseja excluir esse produto?")
                        .setCancelable(false)
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String retornDeleteProduct = null;
                                String[] params = new String[2];
                                params[0] = idProduct;
                                params[1] = session;

                                try {
                                    retornDeleteProduct = new deletarProduto().execute(params).get();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                                Gson gson = new Gson();
                                ReturnServices retornoDeleteProduto = gson.fromJson(retornDeleteProduct, ReturnServices.class);
                                if (retornoDeleteProduto.getRetorno()) {
                                    dialog.cancel();
                                    Toast.makeText(ItemDetail.this, "Produto excluído com sucesso!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ItemDetail.this, MainActivity.class);
                                    intent.putExtra("session", session);
                                    startActivity(intent);
                                } else {
                                    dialog.cancel();
                                    Toast.makeText(ItemDetail.this, "Erro ao excluir o produto!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public static class deletarProduto extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return new ProductsService().deleteProduct(params[0], params[1]);
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