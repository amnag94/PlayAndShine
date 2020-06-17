package com.example.sportssos.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.sportssos.Activities.LoginActivity;
import com.example.sportssos.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent home_page = new Intent(this, LoginActivity.class);
        startActivity(home_page);
    }
}
