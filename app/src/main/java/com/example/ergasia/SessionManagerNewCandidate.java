package com.example.ergasia;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Margot on 17/04/2016.
 */
public class SessionManagerNewCandidate {

    //LogCat tag
    //private static String TAG = SessionManagerNewCandidate.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    //Shared pref mode
    int PRIVATE_MODE = 0;

    //Shared preferences file name
    public static final String PREF_NAME = "ErgasiaNewCandidate";

    public SessionManagerNewCandidate (Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE); //need multiple shared preferences
        editor = pref.edit(); //to write to a shared preferences file
    }
}
