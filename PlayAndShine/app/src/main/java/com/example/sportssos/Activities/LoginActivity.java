package com.example.sportssos.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sportssos.R;
import com.example.sportssos.Session.SessionInfo;
import com.example.sportssos.Session.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button register_btn;
    CardView card_login;
    EditText login_input_email, login_input_pass;
    TextView warning_txt_login;
    DatabaseReference user_database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initializations
        card_login = findViewById(R.id.card_login);
        register_btn = findViewById(R.id.register_btn);
        login_input_email = findViewById(R.id.login_input_email);
        login_input_pass = findViewById(R.id.login_input_pass);
        warning_txt_login = findViewById(R.id.warning_txt_login);

        // Session
        SessionInfo.initialize();

        // If user logged in then redirect to dashboard
        if(SessionInfo.firebase_instance != null && SessionInfo.user != null) {
            retrieveUserInfo(SessionInfo.user.email);

            Intent home = new Intent(this, DashboardActivity.class);
            startActivity(home);
        }

        // Set Listeners
        card_login.setOnClickListener(this);
        register_btn.setOnClickListener(this);

    }

    private void retrieveUserInfo(String email) {

        String user_email = "";

        for (char email_char : email.toCharArray()) {
            if ((email_char >= 'a' && email_char <= 'z') ||
                    (email_char >= 'A' && email_char <= 'Z') ||
                    (email_char >= '0' && email_char <= '9')) {
                user_email += email_char;
            }
        }

        Log.d("Id for user", user_email);

        DatabaseReference user_db = SessionInfo.firebase_database.getReference(SessionInfo.USER_DB_PATH);
        SessionInfo.user.location = user_db.child(user_email).child("location").getKey();
        SessionInfo.user.phone = user_db.child(user_email).child("phone").getKey();
        SessionInfo.user.type = user_db.child(user_email).child("type").getKey();
        SessionInfo.user.name = user_db.child(user_email).child("name").getKey();

    }

    @Override
    public void onClick(View v) {
        Intent next_activity;

        if(v == findViewById(R.id.card_login)) {
            final User user = new User();
            user.email = login_input_email.getText().toString();
            String password = login_input_pass.getText().toString();

            signInUser(user, password);

            next_activity = null;
        }
        else if(v == findViewById(R.id.register_btn)) {
            next_activity = new Intent(this, RegisterActivity.class);
        }
        else {
            next_activity = null;
        }

        if(next_activity != null) {
            startActivity(next_activity);
        }
    }

    private void signInUser(final User user, String password) {
        if (user.email.equals("") || password.equals("")) {
            if (warning_txt_login != null) {
                warning_txt_login.setText("Email and Password cannot be empty");
                warning_txt_login.setVisibility(View.VISIBLE);
            }
            return;
        }
        if (SessionInfo.firebase_instance != null) {
            SessionInfo.firebase_instance.signInWithEmailAndPassword(user.email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SessionInfo.user = user;
                                retrieveUserInfo(user.email);
                                Intent home = new Intent(getApplicationContext(), DashboardActivity.class);
                                startActivity(home);
                            }
                            else {
                                if (warning_txt_login != null) {
                                    warning_txt_login.setText(task.getException().getLocalizedMessage());
                                    warning_txt_login.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (warning_txt_login != null) {
                                warning_txt_login.setText(e.getLocalizedMessage());
                                warning_txt_login.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {

    }
}
