package br.com.AppCrud;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import br.com.AppCrud.model.Autenticacao;
import br.com.AppCrud.service.UserService;

public class Login extends AppCompatActivity {
    private String retorno;
    private EditText username;
    private EditText password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        progressBar = findViewById(R.id.progressBar);

        if (getSupportActionBar() != null) getSupportActionBar().hide();
        LoginButton();
    }

    public void LoginButton() {
        username = findViewById(R.id.user);
        password = findViewById(R.id.password);
        Button login_btn = findViewById(R.id.btnLogin);

        Button register = findViewById(R.id.btnLinkToRegisterScreen);
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
                        String usuario = username.getText().toString();
                        String senha = password.getText().toString();
                        StringBuilder sb = new StringBuilder();
                        sb.append(usuario);
                        sb.append(senha);
                        String[] params = new String[2];
                        params[0] = usuario;
                        params[1] = senha;

                        try {
                            retorno = new ValidarUsuario().execute(params).get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Gson gson = new Gson();
                        Autenticacao dadosAtutenticacao = gson.fromJson(retorno, Autenticacao.class);
                        if (dadosAtutenticacao.getAutenticacao()) {
                            Intent i = new Intent(Login.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            i.putExtra("session", dadosAtutenticacao.getSession());
                            i.putExtra("user", usuario);
                            startActivity(i);
                        } else {
                            Toast.makeText(Login.this, "User and Password is not correct",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
    }

    public class ValidarUsuario extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            return new UserService().returnDataUser(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private Boolean exit = true;

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
