package br.com.AppCrud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.AppCrud.service.HttpServiceProduto;

public class Login extends AppCompatActivity {
    private static String retorno;
    private static EditText username;
    private static EditText password;
    private static Button login_btn;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getSupportActionBar().hide();

        LoginButton();
    }

    public void LoginButton() {
        username = findViewById(R.id.txtUser);
        password = findViewById(R.id.txtPassword);
        login_btn = findViewById(R.id.btnLogin);

        mProgress = new ProgressDialog(Login.this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        TextView register = findViewById(R.id.lnkRegister);
        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProgress.show();
                        if (username.getText().toString().equals("user") &&
                                password.getText().toString().equals("pass")) {
                            new Login.CarregaDadosAsync().execute();
                        } else {
                            mProgress.dismiss();
                            Toast.makeText(Login.this, "User and Password is not correct",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
    }

    private class CarregaDadosAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            retorno = new HttpServiceProduto().execute();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mProgress.dismiss();
            Intent i = new Intent(Login.this, MainActivity.class);
            i.putExtra("produtos", retorno);
            startActivity(i);
            finish();
        }
    }
}
