package com.fortun.credmanmob;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fortun.credmanmob.entity.User;
import com.fortun.credmanmob.httpClient.HTTPClientUsers;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static User user = new User();
    EditText txtLoginUser, txtLoginPassword, txtSignUpPasswordAgain;
    Button btnLoginSignIn, btnLoginSignUp, btnSignUp, btnSignUpCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txtLoginUser = findViewById(R.id.txtLoginUser);
        txtLoginPassword = findViewById(R.id.txtLoginPassword);
        txtSignUpPasswordAgain = findViewById(R.id.txtSignUpPasswordAgain);
        btnLoginSignIn = findViewById(R.id.btnLoginSignIn);
        btnLoginSignUp = findViewById(R.id.btnLoginSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUpCancel = findViewById(R.id.btnSignUpCancel);

        btnLoginSignIn.setOnClickListener(this);
        btnLoginSignUp.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnSignUpCancel.setOnClickListener(this);

        txtSignUpPasswordAgain.setVisibility(View.INVISIBLE);
        btnSignUp.setVisibility(View.INVISIBLE);
        btnSignUpCancel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLoginSignIn) {
            ArrayList<String> users = (ArrayList<String>) HTTPClientUsers.read("findAll", "null").clone();
            if (users.contains(txtLoginUser.getText().toString())) {
                HTTPClientUsers.read("findByName", txtLoginUser.getText().toString());
                String passwordUser = String.valueOf(txtLoginPassword.getText());
                if (passwordUser.equals(MainActivity.user.getPasswordUser())) {
                    txtLoginUser.setText("");
                    txtLoginPassword.setText("");
                    txtSignUpPasswordAgain.setText("");
                    Intent CredentialManager = new Intent(v.getContext(), CredentialManager.class);
                    startActivity(CredentialManager);
                    Toast.makeText(MainActivity.this, "Successful login", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.btnLoginSignUp) {
            txtSignUpPasswordAgain.setVisibility(View.VISIBLE);
            btnSignUp.setVisibility(View.VISIBLE);
            btnSignUpCancel.setVisibility(View.VISIBLE);
            btnLoginSignIn.setVisibility(View.INVISIBLE);
            btnLoginSignUp.setVisibility(View.INVISIBLE);
            txtLoginUser.setText("");
            txtLoginPassword.setText("");
            txtSignUpPasswordAgain.setText("");

        } else if (v.getId() == R.id.btnSignUp) {
            if ((txtLoginUser.getText().length() > 0) && txtLoginPassword.getText().toString().equals(txtSignUpPasswordAgain.getText().toString())) {
                HTTPClientUsers.create(txtLoginUser.getText().toString(), txtLoginPassword.getText().toString());
                Toast.makeText(MainActivity.this, "User signed up", Toast.LENGTH_SHORT).show();
                txtSignUpPasswordAgain.setVisibility(View.INVISIBLE);
                btnSignUp.setVisibility(View.INVISIBLE);
                btnSignUpCancel.setVisibility(View.INVISIBLE);
                btnLoginSignIn.setVisibility(View.VISIBLE);
                btnLoginSignUp.setVisibility(View.VISIBLE);
                txtLoginUser.setText("");
                txtLoginPassword.setText("");
                txtSignUpPasswordAgain.setText("");
            } else {
                Toast.makeText(MainActivity.this, "You have not entered the name or the passwords do not match", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.btnSignUpCancel) {
            txtSignUpPasswordAgain.setVisibility(View.INVISIBLE);
            btnSignUp.setVisibility(View.INVISIBLE);
            btnSignUpCancel.setVisibility(View.INVISIBLE);
            btnLoginSignIn.setVisibility(View.VISIBLE);
            btnLoginSignUp.setVisibility(View.VISIBLE);
            txtLoginUser.setText("");
            txtLoginPassword.setText("");
            txtSignUpPasswordAgain.setText("");

        }
    }
}