package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.authentication.commun.Client;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Client client = new Client("localhost",5000);
    }
}
