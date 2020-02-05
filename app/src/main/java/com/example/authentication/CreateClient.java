package com.example.authentication;

import android.os.AsyncTask;

import com.example.authentication.commun.Client;

public class CreateClient extends AsyncTask<Void, Void, Void> {

    private String username;
    private String password;

    public CreateClient(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Void doInBackground(Void... voids) {
        new Client("10.0.2.2", 5000, username, password);
        return null;
    }
}