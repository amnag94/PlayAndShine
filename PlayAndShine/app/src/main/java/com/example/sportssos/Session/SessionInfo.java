package com.example.sportssos.Session;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SessionInfo {
    public static String USER_DB_PATH = "User";

    public static FirebaseAuth firebase_instance;
    public static FirebaseDatabase firebase_database;
    public static User user;
    public static String sports = " Archery, Badminton, Football, Shooting, Swimming, Diving, Wrestling, Boxing, Tennis, Squash, Weightlifting, Gymnastics, Athletics, Table tennis, Basketball, Volleyball, Cycling";
    public static int position = -1;

    public static void initialize() {
        firebase_instance = FirebaseAuth.getInstance();
        firebase_database = FirebaseDatabase.getInstance();

        FirebaseUser current_user = firebase_instance.getCurrentUser();

        if(current_user != null) {
            user = new User();
            user.email = current_user.getEmail();
            user.name = current_user.getDisplayName();
        }
        else {
            user = null;
        }
    }

    public static String getSport() {
        if (position >= sports.length()) {
            return null;
        }

        int start = position + 2;
        int end = sports.indexOf(",", start);

        // If last one
        end = end == -1 ? sports.length() : end;

        position = end;
        return sports.substring(start, end);
    }
}
