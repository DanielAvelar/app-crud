package br.com.AppCrud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.AppCrud.model.Produto;
import br.com.AppCrud.service.ProductsService;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private ProductAdapter mAdapter;
    private ArrayList<Produto> productList;
    private List<Produto> retorno;
    SwipeRefreshLayout pullToRefresh;
    private ProgressDialog mProgress;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    private Boolean exit = false;
    private String[] session = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mProgress = new ProgressDialog(MainActivity.this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        // Start regular onCreate()
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pullToRefresh = findViewById(R.id.pullToRefresh);

        // Now get a handle to any View contained
        // within the main layout you are using
        View someView = findViewById(R.id.activity_main_text);

        // Find the root view"
        View root = someView.getRootView();

        // Set the color
        root.setBackgroundColor(getResources().getColor(android.R.color.black));

        dl = findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.open_drawer, R.string.close_drawer);

        dl.addDrawerListener(t);
        t.syncState();

        if (getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        Toast.makeText(MainActivity.this, "My Account",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "Settings",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        Toast.makeText(MainActivity.this, "Logout",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |    Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    default:
                        return true;
                }
                return true;
            }
        });

        Intent i = getIntent();
        String user = i.getStringExtra("user");
        View headerView = nv.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.textUser);
        navUsername.setText(user);

        if (CarregarListaProdutos()) {
            mProgress.dismiss();
        } else {
            mProgress.dismiss();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }

        //setting an setOnRefreshListener on the SwipeDownLayout
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local
                CarregarListaProdutos();
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (t.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    public boolean CarregarListaProdutos() {
        Intent i = getIntent();
        session[0] = i.getStringExtra("session");

        try {
            String produtos = new CarregarTodosProdutos().execute(session).get();
            JSONObject jsonObj = new JSONObject(produtos);

            // Getting JSON Array node
            JSONArray product = jsonObj.getJSONArray("products");

            retorno = new ArrayList<>();
            // looping through All Products
            for (int contProd = 0; contProd < product.length(); contProd++) {
                JSONObject c = product.getJSONObject(contProd);
                Gson gson = new Gson();
                Produto object = gson.fromJson(c.toString(), Produto.class);
                retorno.add(object);
            }

            ListView listView = findViewById(R.id.product_list);
            productList = new ArrayList<>();

            productList.addAll(retorno);

            //mAdapter.clear();
            mAdapter = new ProductAdapter(MainActivity.this, R.layout.list_item, productList);
            listView.setAdapter(mAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), ItemDetail.class);
                    intent.putExtra("session", session[0]);
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
            return true;
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
            return false;
        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage());
            return false;
        } catch (ExecutionException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public class CarregarTodosProdutos extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            mProgress.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return new ProductsService().getAllProducts(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mProgress.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
            System.exit(0);
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }
}
