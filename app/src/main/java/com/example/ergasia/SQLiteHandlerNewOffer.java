package com.example.ergasia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Margot on 19/04/2016.
 */
public class SQLiteHandlerNewOffer extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandlerNewOffer.class.getSimpleName();

    //All Static variables
    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "Ergasia";

    //Login table name
    private static final String TABLE_NEW_OFFER = "recruiters";

    //Login table colunms names
    private static final String KEY_ID = "id";
    private static final String KEY_COMPANY = "company";
    private static final String KEY_JOB_TITLE = "job_title";
    private static final String KEY_AREA_ACTIVITY = "area_activity";
    private static final String KEY_TYPE = "type";
    private static final String KEY_GEOLOCATION = "geolocation";
    private static final String KEY_SKILL = "skill";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    public SQLiteHandlerNewOffer(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NEW_OFFER_TABLE = "CREATE TABLE " + TABLE_NEW_OFFER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COMPANY + " TEXT,"
                + KEY_JOB_TITLE + " TEXT," + KEY_AREA_ACTIVITY + " TEXT,"
                + KEY_TYPE + " TEXT," + KEY_GEOLOCATION + " TEXT,"
                + KEY_SKILL + " TEXT," + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_NEW_OFFER_TABLE);

        Log.d(TAG, "Database tables created");
    }

    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEW_OFFER);

        //Create tables again
        onCreate(db);
    }

    /**
     * add an offer into the database
     * @param company
     * @param jobTitle
     * @param areaActivity
     * @param type
     * @param geolocation
     * @param skill
     * @param uid
     * @param created_at
     */
    public void addOffer(String company, String jobTitle, String areaActivity, String type,
                         String geolocation, String skill, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COMPANY, company);
        values.put(KEY_JOB_TITLE, jobTitle);
        values.put(KEY_AREA_ACTIVITY, areaActivity);
        values.put(KEY_TYPE, type);
        values.put(KEY_GEOLOCATION, geolocation);
        values.put(KEY_SKILL, skill);
        values.put(KEY_UID, uid); //unique id
        values.put(KEY_CREATED_AT, created_at); //created_at
        System.out.println(company + "db");
        System.out.println(jobTitle + "db");
        System.out.println(areaActivity + "db");
        System.out.println(type + "db");
        System.out.println(geolocation +"db");
        System.out.println(skill +"db");
        System.out.println(uid +"db");
        System.out.println(created_at +"db");
        //Inserting Row
        long id = db.insert(TABLE_NEW_OFFER, null, values);
        db.close(); //Closing database connection

        Log.d(TAG, "New offer inserted into sqlite; " + id);
    }

    /**
     * Getting user data from database
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_NEW_OFFER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("company", cursor.getString(1));
            user.put("job_title", cursor.getString(2));
            user.put("area_activity", cursor.getString(3));
            user.put("type", cursor.getString(4));
            user.put("geolocation", cursor.getString(5));
            user.put("skill", cursor.getString(6));
            user.put("uid", cursor.getString(7));
            user.put("created_at", cursor.getString(8));
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
        db.delete(TABLE_NEW_OFFER, null , null);
        db.close();

        Log.d(TAG, "Deleted the offer info from Sqlite");
    }
}
