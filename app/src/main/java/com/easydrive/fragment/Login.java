package com.easydrive.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.easydrive.R;
import com.easydrive.View.MainActivity;

public class Login extends AppCompatActivity {
    EditText email, pass;
    Button login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.etemail1);
        pass = (EditText) findViewById(R.id.etpass1);
        login = (Button) findViewById(R.id.loginbtn);

    }

    public void login(View view) {
        ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading");
        progressDialog.setTitle("Login");
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.dismiss();
        if (email.getText().toString().isEmpty() && pass.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "INVALID CREDENTIALS", Toast.LENGTH_LONG).show();


        } else {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            progressDialog.dismiss();
        }
    }

}
