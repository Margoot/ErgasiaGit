package com.example.ergasia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Margot on 17/04/2016.
 */
public class SQLiteHandlerNewCandidate extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandlerNewCandidate.class.getSimpleName();

    //All Static variables
    //Database version
    private static final int DATABASE_VERSION = 2;

    //Database name
    private static final String DATABASE_NAME = "Ergasia";

    //Login table name
    private static final String TABLE_NEW_CANDIDATE = "candidates";

    //Login table colunms names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_TRAINING = "training";
    private static final String KEY_AREA_ACTIVITY = "area_activity";
    private static final String KEY_TYPE = "type";
    private static final String KEY_LANGUAGE1 = "language1";
    private static final String KEY_LEVEL_LANGUAGE1 = "level_language1";
    private static final String KEY_LANGUAGE2 = "language2";
    private static final String KEY_LEVEL_LANGUAGE2 = "level_language2";
    private static final String KEY_LANGUAGE3 = "language3";
    private static final String KEY_LEVEL_LANGUAGE3 = "level_language3";
    private static final String KEY_SKILL = "skill";
    private static final String KEY_GEOLOCATION = "geolocation";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    public SQLiteHandlerNewCandidate(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NEW_CANDIDATE_TABLE = "CREATE TABLE " + TABLE_NEW_CANDIDATE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_FIRSTNAME + " TEXT," + KEY_TRAINING + " TEXT,"
                + KEY_AREA_ACTIVITY + " TEXT," + KEY_TYPE + " TEXT,"
                + KEY_LANGUAGE1 + " TEXT," + KEY_LEVEL_LANGUAGE1 + " TEXT,"
                + KEY_LANGUAGE2 + " TEXT," + KEY_LEVEL_LANGUAGE2 + " TEXT,"
                + KEY_LANGUAGE3 + " TEXT," + KEY_LEVEL_LANGUAGE3 + " TEXT,"
                + KEY_SKILL + " TEXT," + KEY_GEOLOCATION + " TEXT,"
                + KEY_UID + " TEXT," + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_NEW_CANDIDATE_TABLE);

        Log.d(TAG, "Database tables created");
    }

    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEW_CANDIDATE);

        //Create tables again
        onCreate(db);
    }


    /**
     *
     * @param name
     * @param firstname
     * @param training
     * @param area_activity
     * @param type
     * @param language1
     * @param level_language1
     * @param language2
     * @param level_language2
     * @param language3
     * @param level_language3
     * @param skill
     * @param geolocation
     * @param uid
     * @param created_at
     */
    public void addNewCandidate(String name, String firstname, String training, String area_activity, String type,
                        String language1, String level_language1, String language2, String level_language2,
                        String language3, String level_language3, String skill, String geolocation,
                        String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_FIRSTNAME, firstname);
        values.put(KEY_TRAINING, training);
        values.put(KEY_AREA_ACTIVITY, area_activity);
        values.put(KEY_TYPE, type);
        values.put(KEY_LANGUAGE1, language1);
        values.put(KEY_LEVEL_LANGUAGE1, level_language1);
        values.put(KEY_LANGUAGE2, language2);
        values.put(KEY_LEVEL_LANGUAGE2, level_language2);
        values.put(KEY_LANGUAGE3, language3);
        values.put(KEY_LEVEL_LANGUAGE3, level_language3);
        values.put(KEY_SKILL, skill);
        values.put(KEY_GEOLOCATION, geolocation);
        values.put(KEY_UID, uid); //unique id
        values.put(KEY_CREATED_AT, created_at); //created_at
        System.out.println(name + "db");
        System.out.println(firstname + "db");
        System.out.println(training + "db");
        System.out.println(area_activity + "db");
        System.out.println(type +"db");
        System.out.println(language1 +"db");
        System.out.println(level_language1 + "db");
        System.out.println(language2 +"db");
        System.out.println(level_language2 +"db");
        System.out.println(language3 + "db");
        System.out.println(level_language3 +"db");
        System.out.println(skill +"db");
        System.out.println(geolocation + "db");
        System.out.println(uid +"db");
        System.out.println(created_at +"db");
        //Inserting Row
        long id = db.insert(TABLE_NEW_CANDIDATE, null, values);
        db.close(); //Closing database connection

        Log.d(TAG, "New candidate inserted into sqlite; " + id);
    }

    /**
     * Getting candidate data from database
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_NEW_CANDIDATE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("firstname", cursor.getString(2));
            user.put("training", cursor.getString(3));
            user.put("area_activity", cursor.getString(4));
            user.put("type", cursor.getString(5));
            user.put("language1", cursor.getString(6));
            user.put("level_language1", cursor.getString(7));
            user.put("language2", cursor.getString(8));
            user.put("level_language2", cursor.getString(9));
            user.put("language3", cursor.getString(10));
            user.put("level_language3", cursor.getString(11));
            user.put("skill", cursor.getString(12));
            user.put("geolocation", cursor.getString(13));
            user.put("uid", cursor.getString(14));
            user.put("created_at", cursor.getString(15));
        }
        cursor.close();
        db.close();
        //Return user
        Log.d(TAG, "Fetching new candidate from Sqlite: " + user.toString());
        return user;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        //Delete all rows
        db.delete(TABLE_NEW_CANDIDATE, null , null);
        db.close();

        Log.d(TAG, "Deleted all candidate info from Sqlite");
    }
}
