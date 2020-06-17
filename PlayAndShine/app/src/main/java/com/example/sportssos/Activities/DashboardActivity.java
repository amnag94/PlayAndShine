package com.example.sportssos.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportssos.R;
import com.example.sportssos.Session.SessionInfo;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseUser active_user;
    TextView txt_profile_name, txt_profile_email;
    ImageButton btn_profile_user;
    CardView btn_card_logout;
    LinearLayout profile_detail_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initializations
        txt_profile_name = findViewById(R.id.txt_profile_name);
        txt_profile_email = findViewById(R.id.txt_profile_email);
        btn_profile_user = findViewById(R.id.btn_profile_user);
        btn_card_logout = findViewById(R.id.btn_card_logout);
        profile_detail_layout = findViewById(R.id.profile_detail_layout);

        // Session
        if (SessionInfo.firebase_instance != null) {
            active_user = SessionInfo.firebase_instance.getCurrentUser();
        }

        if (active_user == null || SessionInfo.user == null) {
            Toast.makeText(this, "Something went wrong, Login Again!", Toast.LENGTH_LONG).show();
            Intent login_page = new Intent(this, LoginActivity.class);
            startActivity(login_page);
            finish();
        }
        else {
            txt_profile_name.setText(SessionInfo.user.email);
            txt_profile_email.setText(SessionInfo.user.email);
        }

        // Set Listeners
        btn_profile_user.setOnClickListener(this);
        btn_card_logout.setOnClickListener(this);

    }

    private void signOut(FirebaseUser user) {
        //Toast.makeText(this, "Signing out user : " + active_user.getEmail(), Toast.LENGTH_LONG).show();
        SessionInfo.firebase_instance.signOut();
        Intent login_page = new Intent(this, LoginActivity.class);
        startActivity(login_page);
        finish();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.btn_profile_user)) {
            if (profile_detail_layout.getVisibility() == View.VISIBLE) {
                profile_detail_layout.setVisibility(View.GONE);
            }
            else {
                profile_detail_layout.setVisibility(View.VISIBLE);
            }
        }
        else if (v == findViewById(R.id.btn_card_logout)) {
            if (active_user != null) {
                signOut(active_user);
            }
            //else {
                //finish();
            //}
        }
        else {

        }
    }
}
