package com.example.ergasia;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.content.pm.PackageInstaller;

/**
 * Created by Margot on 11/04/2016.
 */
public class SessionManager {
    //LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    //Shared pref mode
    int PRIVATE_MODE = 0;

    //Shared preferences file name
    public static final String PREF_NAME = "Ergasia";
    public static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_IS_CANDIDATE = "isCandidate";
    public static final String KEY_IS_RECRUITER = "isRecruiter";

    public SessionManager (Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE); //need multiple shared preferences
        editor = pref.edit(); //to write to a shared preferences file
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn); //Set a boolean value in the preferences editor

        //commit changes
        editor.commit();
        Log.d(TAG, "User login session modified");
    }

    public boolean isLoggedIn () {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setEmail(String email) {
        editor.putString(KEY_EMAIL, email);

        //commit changes
        editor.commit();
        Log.d(TAG, "Email stored");

    }

    public String getEmail () {
        return pref.getString(KEY_EMAIL, "");
    }


    public void setCandidate(boolean isCandidate) {
        editor.putBoolean(KEY_IS_CANDIDATE, isCandidate);

        //commit changes
        editor.commit();
        Log.d(TAG, "User is a candidate");
    }

    public boolean isCandidate() {
        return pref.getBoolean(KEY_IS_CANDIDATE, false);
    }

    public void setRecruiter(boolean isRecruiter) {
        editor.putBoolean(KEY_IS_RECRUITER, isRecruiter);

        //commit changes
        editor.commit();
        Log.d(TAG, "User is a recruiter");
    }

    public boolean isRecruiter() {
        return pref.getBoolean(KEY_IS_RECRUITER, false);
    }
}
