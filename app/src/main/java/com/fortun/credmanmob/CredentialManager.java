package com.fortun.credmanmob;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CredentialManager extends AppCompatActivity implements View.OnClickListener {

    ListView credentialsList;
    ArrayList<String> credentials;
    ArrayAdapter<String> adapter;
    EditText txtUserManagerUser, txtUserManagerPassword, txtUserManagerPasswordAgain;
    Button btnSignOut, btnManageUser, btnUserManagerUpdate, btnUserManagerDelete, btnUserManagerCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credential_manager);

        credentialsList = findViewById(R.id.credentialsList);
        txtUserManagerUser = findViewById(R.id.txtUserManagerUser);
        txtUserManagerPassword = findViewById(R.id.txtUserManagerPassword);
        txtUserManagerPasswordAgain = findViewById(R.id.txtUserManagerPasswordAgain);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnManageUser = findViewById(R.id.btnManageUser);
        btnUserManagerUpdate = findViewById(R.id.btnUserManagerUpdate);
        btnUserManagerDelete = findViewById(R.id.btnUserManagerDelete);
        btnUserManagerCancel = findViewById(R.id.btnUserManagerCancel);

        btnSignOut.setOnClickListener(v -> finish());
        btnManageUser.setOnClickListener(this);
        btnUserManagerUpdate.setOnClickListener(this);
        btnUserManagerDelete.setOnClickListener(this);
        btnUserManagerCancel.setOnClickListener(this);

        txtUserManagerUser.setVisibility(View.INVISIBLE);
        txtUserManagerPassword.setVisibility(View.INVISIBLE);
        txtUserManagerPasswordAgain.setVisibility(View.INVISIBLE);
        btnUserManagerUpdate.setVisibility(View.INVISIBLE);
        btnUserManagerDelete.setVisibility(View.INVISIBLE);
        btnUserManagerCancel.setVisibility(View.INVISIBLE);

        credentials = new ArrayList<>();
        credentials.add("ID     -     NAME     -     USER     -     PASSWORD");
        credentials.add("ID     -     NAME     -     USER     -     PASSWORD");
        credentials.add("ID     -     NAME     -     USER     -     PASSWORD");
        credentials.add("ID     -     NAME     -     USER     -     PASSWORD");
        credentials.add("ID     -     NAME     -     USER     -     PASSWORD");

        // Crear adaptador
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, credentials);

        // Asignar el adaptador a nuestro ListView
        credentialsList.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnManageUser) {
            txtUserManagerUser.setText(MainActivity.user.getNameUser());
            txtUserManagerPassword.setText(MainActivity.user.getPasswordUser());
            txtUserManagerPasswordAgain.setText(MainActivity.user.getPasswordUser());
            btnSignOut.setVisibility(View.INVISIBLE);
            btnManageUser.setVisibility(View.INVISIBLE);
            txtUserManagerUser.setVisibility(View.VISIBLE);
            txtUserManagerPassword.setVisibility(View.VISIBLE);
            txtUserManagerPasswordAgain.setVisibility(View.VISIBLE);
            btnUserManagerUpdate.setVisibility(View.VISIBLE);
            btnUserManagerDelete.setVisibility(View.VISIBLE);
            btnUserManagerCancel.setVisibility(View.VISIBLE);

        } else if (v.getId() == R.id.btnUserManagerUpdate) {
            btnSignOut.setVisibility(View.VISIBLE);
            btnManageUser.setVisibility(View.VISIBLE);
            txtUserManagerUser.setVisibility(View.INVISIBLE);
            txtUserManagerPassword.setVisibility(View.INVISIBLE);
            txtUserManagerPasswordAgain.setVisibility(View.INVISIBLE);
            btnUserManagerUpdate.setVisibility(View.INVISIBLE);
            btnUserManagerDelete.setVisibility(View.INVISIBLE);
            btnUserManagerCancel.setVisibility(View.INVISIBLE);

        } else if (v.getId() == R.id.btnUserManagerDelete) {
            finish();

        } else if (v.getId() == R.id.btnUserManagerCancel) {
            btnSignOut.setVisibility(View.VISIBLE);
            btnManageUser.setVisibility(View.VISIBLE);
            txtUserManagerUser.setVisibility(View.INVISIBLE);
            txtUserManagerPassword.setVisibility(View.INVISIBLE);
            txtUserManagerPasswordAgain.setVisibility(View.INVISIBLE);
            btnUserManagerUpdate.setVisibility(View.INVISIBLE);
            btnUserManagerDelete.setVisibility(View.INVISIBLE);
            btnUserManagerCancel.setVisibility(View.INVISIBLE);

        }
    }
}