package com.example.authentication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.authentication.commun.Client;

public class CreateClient extends AsyncTask<Void, Void, Void> {

    private String username;
    private String password;
    private Context context;

    public CreateClient(String username, String password, Context context) {
        this.username = username;
        this.password = password;
        this.context = context;
    }

    @Override
    public Void doInBackground(Void... voids) {
        new Client("10.0.2.2", 5000, username, password, context);
        return null;
    }
}