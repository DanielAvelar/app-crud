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
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

import br.com.AppCrud.model.ReturnServices;
import br.com.AppCrud.service.ProductsService;

public class ItemEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);

        Intent intent = getIntent();
        final String session = intent.getStringExtra("session");
        final String imageUrl = intent.getStringExtra("image");
        final String name = intent.getStringExtra("name");
        final String description = intent.getStringExtra("description");
        final String amount = intent.getStringExtra("amount");
        final String idProduct = intent.getStringExtra("id_product");
        final String category = intent.getStringExtra("category");
        final String price = intent.getStringExtra("price");

        EditText listData = findViewById(R.id.urlImage);
        listData.setText(imageUrl);

        listData = findViewById(R.id.name);
        listData.setText(name);

        listData = findViewById(R.id.description);
        listData.setText(description);

        listData = findViewById(R.id.amount);
        listData.setText(amount);

        listData = findViewById(R.id.category);
        listData.setText(category);

        listData = findViewById(R.id.price);
        listData.setText(price);

        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setMovementMethod(LinkMovementMethod.getInstance());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemEdit.this, ItemDetail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("session", session);
                intent.putExtra("image", imageUrl);
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                intent.putExtra("amount", amount);
                intent.putExtra("id_product", idProduct);
                intent.putExtra("category", category);
                intent.putExtra("price", price);
                startActivity(intent);
                finish(); // Call once you redirect to another activity
            }
        });

        Button btnSave = findViewById(R.id.btn_save);
        btnSave.setMovementMethod(LinkMovementMethod.getInstance());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ItemEdit.this);
                alertDialogBuilder.setTitle("Alerta");
                alertDialogBuilder
                        .setMessage("Tem certeza que deseja salvar os valores editados para esse produto?")
                        .setCancelable(false)
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String returnEditProduct = null;
                                EditText urlImage = findViewById(R.id.urlImage);
                                EditText txtName = findViewById(R.id.name);
                                EditText txtDescription = findViewById(R.id.description);
                                EditText txtAmount = findViewById(R.id.amount);
                                EditText txtCategory = findViewById(R.id.category);
                                EditText txtPrice = findViewById(R.id.price);

                                String[] params = new String[8];
                                params[0] = session;
                                params[1] = idProduct;
                                params[2] = urlImage.getText().toString();
                                params[3] = txtName.getText().toString();
                                params[4] = txtDescription.getText().toString();
                                params[5] = txtAmount.getText().toString();
                                params[6] = txtCategory.getText().toString();
                                params[7] = txtPrice.getText().toString();

                                try {
                                    returnEditProduct = new editProduct().execute(params).get();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                }
                                Gson gson = new Gson();
                                ReturnServices responseEditProduct = gson.fromJson(returnEditProduct, ReturnServices.class);
                                if (responseEditProduct.getRetorno()) {
                                    dialog.cancel();
                                    Toast.makeText(ItemEdit.this, "Produto atualizado com sucesso!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ItemEdit.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("session", session);
                                    startActivity(intent);
                                    finish(); // Call once you redirect to another activity
                                } else {
                                    dialog.cancel();
                                    Toast.makeText(ItemEdit.this, "Erro ao atualizar o produto!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        //enable back Button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class editProduct extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return new ProductsService().editProduct(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
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