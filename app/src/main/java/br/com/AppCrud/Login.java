package br.com.AppCrud;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.AppCrud.service.HttpServiceProduto;


public class Login extends AppCompatActivity {
    private String retorno;
    private static EditText username;
    private static EditText password;
    private static TextView attempts;
    private static Button login_btn;
    int attempt_counter = 5;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton();
    }

    public  void LoginButton() {
        username = findViewById(R.id.editText_user);
        password = findViewById(R.id.editText_password);
        attempts = findViewById(R.id.textView_attemt_Count);
        login_btn = findViewById(R.id.button_login);

        attempts.setText(Integer.toString(attempt_counter));

        login_btn.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(username.getText().toString().equals("user") &&
                            password.getText().toString().equals("pass")  ) {
                        Toast.makeText(Login.this,"User and Password is correct",
                                Toast.LENGTH_SHORT).show();
                        new Login.CarregaDadosAsync().execute();
                    } else {
                        Toast.makeText(Login.this,"User and Password is not correct",
                                Toast.LENGTH_SHORT).show();
                        attempt_counter--;
                        attempts.setText(Integer.toString(attempt_counter));
                        if(attempt_counter == 0){
                            login_btn.setEnabled(false);
                        }
                    }

                }
            }
        );
    }

    private class CarregaDadosAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {super.onPreExecute();}

        @Override
        protected Void doInBackground(Void... arg0) {
            retorno = new HttpServiceProduto().execute();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Intent i = new Intent(Login.this, MainActivity.class);
            i.putExtra("produtos", retorno);
            startActivity(i);
            finish();
        }
    }
}
