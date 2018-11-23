package br.com.AppCrud;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import br.com.AppCrud.model.RetornoCriarUsuario;
import br.com.AppCrud.service.UserService;

public class Registration extends AppCompatActivity {
    private static String retorno;
    private static EditText username;
    private static EditText password;
    private static EditText txtEmail;
    private static CheckBox txtAdministrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        getSupportActionBar().hide();

        username = findViewById(R.id.user);
        password = findViewById(R.id.password);
        txtEmail = findViewById(R.id.email);
        txtAdministrator = findViewById(R.id.administrator);

        TextView login = findViewById(R.id.btnLinkToLoginScreen);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Registration.this, Login.class);
                startActivity(i);
            }
        });

        TextView register = findViewById(R.id.btnLinkToRegisterScreen);
        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = username.getText().toString();
                String senha = password.getText().toString();
                String email = txtEmail.getText().toString();
                String administrator = txtAdministrator.getText().toString();

                StringBuilder sb = new StringBuilder();
                sb.append(usuario);
                sb.append(senha);
                String[] params = new String[4];
                params[0] = usuario;
                params[1] = senha;
                params[2] = email;
                params[3] = administrator;

                try {
                    retorno = new CriarUsuario().execute(params).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                RetornoCriarUsuario dadosCriarUsuario = gson.fromJson(retorno, RetornoCriarUsuario.class);
                if (dadosCriarUsuario.getRetorno()) {
                    Intent i = new Intent(Registration.this, Login.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Registration.this, dadosCriarUsuario.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class CriarUsuario extends AsyncTask<String, Void, String> {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            return new UserService().createUser(params[0], params[1], params[2], params[3]);
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}