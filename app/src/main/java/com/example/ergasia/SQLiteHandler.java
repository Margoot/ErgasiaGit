package com.example.ergasia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * This class takes care of storing the user data in SQLite database.
 * Whenever we needs to get the logged in user information,
 * we fetch from SQLite instead of making request to server.
 */
public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    //All Static variables
    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "Ergasia";

    //Login table name
    private static final String TABLE_USER = "users";

    //Login table colunms names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_FIRSTNAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        //Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     *
     * @param name
     * @param firstname
     * @param email
     * @param uid
     * @param created_at
     */
    public void addUser(String name, String firstname, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); //Name
        values.put(KEY_FIRSTNAME, firstname); //firstname
        values.put(KEY_EMAIL, email); //email
        values.put(KEY_UID, uid); //unique id
        values.put(KEY_CREATED_AT, created_at); //created_at
        System.out.println(name + "db");
        System.out.println(firstname +"db");
        System.out.println(email +"db");
        System.out.println(uid +"db");
        System.out.println(created_at +"db");
        //Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); //Closing database connection

        Log.d(TAG, "New user inserted into sqlite; " + id);
    }

    /**
     * Getting user data from database
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("firstname", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("uid", cursor.getString(4));
            user.put("created_at", cursor.getString(5));
        }

        cursor.close();
        db.close();
        //Return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
        return user;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        //Delete all rows
        db.delete(TABLE_USER, null , null);
        db.close();

        Log.d(TAG, "Deleted all user info from Sqlite");
    }
}





