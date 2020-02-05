package com.example.authentication;

import android.os.AsyncTask;

import com.example.authentication.commun.Client;

public class CreateClient extends AsyncTask<Void, Void, Void> {

    @Override
    public Void doInBackground(Void... voids) {
        new Client("10.0.2.2", 5000);
        return null;
    }
}