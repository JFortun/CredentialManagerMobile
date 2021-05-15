package com.fortun.credmanmob;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fortun.credmanmob.httpClient.HTTPClientCredentials;
import com.fortun.credmanmob.httpClient.HTTPClientUsers;

import java.util.ArrayList;

public class CredentialManager extends AppCompatActivity implements View.OnClickListener {

    final Handler handler = new Handler(Looper.getMainLooper());
    ListView credentialsList;
    ArrayList<String> credentials;
    ArrayAdapter<String> adapter;
    EditText txtUserManagerUser, txtUserManagerPassword, txtUserManagerPasswordAgain, txtCredentialManagerName, txtCredentialManagerUser, txtCredentialManagerPassword;
    Button btnSignOut, btnManageUser, btnUserManagerUpdate, btnUserManagerDelete, btnUserManagerCancel, btnCredentialManagerNew, btnCredentialManagerCreate, btnCredentialManagerUpdate, btnCredentialManagerCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credential_manager);

        credentialsList = findViewById(R.id.credentialsList);
        txtUserManagerUser = findViewById(R.id.txtUserManagerUser);
        txtUserManagerPassword = findViewById(R.id.txtUserManagerPassword);
        txtUserManagerPasswordAgain = findViewById(R.id.txtUserManagerPasswordAgain);
        txtCredentialManagerName = findViewById(R.id.txtCredentialManagerName);
        txtCredentialManagerUser = findViewById(R.id.txtCredentialManagerUser);
        txtCredentialManagerPassword = findViewById(R.id.txtCredentialManagerPassword);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnManageUser = findViewById(R.id.btnManageUser);
        btnUserManagerUpdate = findViewById(R.id.btnUserManagerUpdate);
        btnUserManagerDelete = findViewById(R.id.btnUserManagerDelete);
        btnUserManagerCancel = findViewById(R.id.btnUserManagerCancel);
        btnCredentialManagerNew = findViewById(R.id.btnCredentialManagerNew);
        btnCredentialManagerCreate = findViewById(R.id.btnCredentialManagerCreate);
        btnCredentialManagerUpdate = findViewById(R.id.btnCredentialManagerUpdate);
        btnCredentialManagerCancel = findViewById(R.id.btnCredentialManagerCancel);

        btnSignOut.setOnClickListener(v -> finish());
        btnManageUser.setOnClickListener(this);
        btnUserManagerUpdate.setOnClickListener(this);
        btnUserManagerDelete.setOnClickListener(this);
        btnUserManagerCancel.setOnClickListener(this);
        btnCredentialManagerNew.setOnClickListener(this);
        btnCredentialManagerCreate.setOnClickListener(this);
        btnCredentialManagerUpdate.setOnClickListener(this);
        btnCredentialManagerCancel.setOnClickListener(this);

        txtUserManagerUser.setVisibility(View.INVISIBLE);
        txtUserManagerPassword.setVisibility(View.INVISIBLE);
        txtUserManagerPasswordAgain.setVisibility(View.INVISIBLE);
        txtCredentialManagerName.setVisibility(View.INVISIBLE);
        txtCredentialManagerUser.setVisibility(View.INVISIBLE);
        txtCredentialManagerPassword.setVisibility(View.INVISIBLE);
        btnUserManagerUpdate.setVisibility(View.INVISIBLE);
        btnUserManagerDelete.setVisibility(View.INVISIBLE);
        btnUserManagerCancel.setVisibility(View.INVISIBLE);
        btnCredentialManagerCreate.setVisibility(View.INVISIBLE);
        btnCredentialManagerUpdate.setVisibility(View.INVISIBLE);
        btnCredentialManagerCancel.setVisibility(View.INVISIBLE);

        credentials = HTTPClientCredentials.read("findAll", "null");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, credentials);

        credentialsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        credentialsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                btnCredentialManagerNew.setVisibility(View.INVISIBLE);
                btnCredentialManagerUpdate.setVisibility(View.VISIBLE);
                btnCredentialManagerCancel.setVisibility(View.VISIBLE);
                txtCredentialManagerName.setVisibility(View.VISIBLE);
                txtCredentialManagerUser.setVisibility(View.VISIBLE);
                txtCredentialManagerPassword.setVisibility(View.VISIBLE);

                String[] credentialSplit = credentialsList.getItemAtPosition(position).toString().split("     ");
                MainActivity.credential.setIdCredential(Long.valueOf(credentialSplit[0]));
                txtCredentialManagerName.setText(credentialSplit[1]);
                txtCredentialManagerUser.setText(credentialSplit[2]);
                txtCredentialManagerPassword.setText(credentialSplit[3]);
            }
        });

        credentialsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CredentialManager.this);
                builder.setMessage("Are you sure you want to delete this credential? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String[] credentialSplit = credentialsList.getItemAtPosition(position).toString().split("     ");
                                MainActivity.credential.setIdCredential(Long.valueOf(credentialSplit[0]));
                                HTTPClientCredentials.delete(MainActivity.credential.getIdCredential().intValue());
                                Toast.makeText(getApplicationContext(), "Credential deleted", Toast.LENGTH_SHORT).show();

                                credentials.clear();
                                credentials.addAll(HTTPClientCredentials.read("findAll", "null"));
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }
        });

        handler.postDelayed(new Runnable() {
            public void run() {

                credentials.clear();
                credentials.addAll(HTTPClientCredentials.read("findAll", "null"));
                adapter.notifyDataSetChanged();

                handler.postDelayed(this, 5000);
            }
        }, 5000);

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
            if ((txtUserManagerUser.getText().length() > 0) && txtUserManagerPassword.getText().toString().equals(txtUserManagerPasswordAgain.getText().toString())) {
                HTTPClientUsers.update(MainActivity.user.getIdUser().intValue(), txtUserManagerUser.getText().toString(), txtUserManagerPassword.getText().toString());
                Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show();
                HTTPClientUsers.read("findByName", txtUserManagerUser.getText().toString());
                btnSignOut.setVisibility(View.VISIBLE);
                btnManageUser.setVisibility(View.VISIBLE);
                txtUserManagerUser.setVisibility(View.INVISIBLE);
                txtUserManagerPassword.setVisibility(View.INVISIBLE);
                txtUserManagerPasswordAgain.setVisibility(View.INVISIBLE);
                btnUserManagerUpdate.setVisibility(View.INVISIBLE);
                btnUserManagerDelete.setVisibility(View.INVISIBLE);
                btnUserManagerCancel.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(this, "You have not entered the name or the passwords do not match", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.btnUserManagerDelete) {
            HTTPClientUsers.delete(MainActivity.user.getIdUser().intValue());
            Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
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

        } else if (v.getId() == R.id.btnCredentialManagerNew) {
            btnCredentialManagerNew.setVisibility(View.INVISIBLE);
            btnCredentialManagerCreate.setVisibility(View.VISIBLE);
            btnCredentialManagerCancel.setVisibility(View.VISIBLE);
            txtCredentialManagerName.setVisibility(View.VISIBLE);
            txtCredentialManagerUser.setVisibility(View.VISIBLE);
            txtCredentialManagerPassword.setVisibility(View.VISIBLE);

        } else if (v.getId() == R.id.btnCredentialManagerCreate) {
            HTTPClientCredentials.create(txtCredentialManagerName.getText().toString(), txtCredentialManagerUser.getText().toString(), txtCredentialManagerPassword.getText().toString(), String.valueOf(MainActivity.user.getIdUser()));
            Toast.makeText(this, "Credential created", Toast.LENGTH_SHORT).show();
            btnCredentialManagerNew.setVisibility(View.VISIBLE);
            btnCredentialManagerCreate.setVisibility(View.INVISIBLE);
            btnCredentialManagerUpdate.setVisibility(View.INVISIBLE);
            btnCredentialManagerCancel.setVisibility(View.INVISIBLE);
            txtCredentialManagerName.setVisibility(View.INVISIBLE);
            txtCredentialManagerUser.setVisibility(View.INVISIBLE);
            txtCredentialManagerPassword.setVisibility(View.INVISIBLE);

            credentials.clear();
            credentials.addAll(HTTPClientCredentials.read("findAll", "null"));
            adapter.notifyDataSetChanged();

        } else if (v.getId() == R.id.btnCredentialManagerUpdate) {
            Toast.makeText(this, "Credential updated", Toast.LENGTH_SHORT).show();
            HTTPClientCredentials.update(MainActivity.credential.getIdCredential().intValue(), txtCredentialManagerName.getText().toString(), txtCredentialManagerUser.getText().toString(), txtCredentialManagerPassword.getText().toString(), String.valueOf(MainActivity.user.getIdUser()));
            btnCredentialManagerNew.setVisibility(View.VISIBLE);
            btnCredentialManagerCreate.setVisibility(View.INVISIBLE);
            btnCredentialManagerUpdate.setVisibility(View.INVISIBLE);
            btnCredentialManagerCancel.setVisibility(View.INVISIBLE);
            txtCredentialManagerName.setVisibility(View.INVISIBLE);
            txtCredentialManagerUser.setVisibility(View.INVISIBLE);
            btnCredentialManagerCreate.setVisibility(View.INVISIBLE);
            btnCredentialManagerUpdate.setVisibility(View.INVISIBLE);
            btnCredentialManagerCancel.setVisibility(View.INVISIBLE);
            txtCredentialManagerName.setVisibility(View.INVISIBLE);
            txtCredentialManagerUser.setVisibility(View.INVISIBLE);
            txtCredentialManagerPassword.setVisibility(View.INVISIBLE);

            credentials.clear();
            credentials.addAll(HTTPClientCredentials.read("findAll", "null"));
            adapter.notifyDataSetChanged();

        }
    }
}