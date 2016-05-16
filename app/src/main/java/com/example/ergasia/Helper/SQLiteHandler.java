package com.example.ergasia.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInstaller;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ergasia.Activity.View_offer_activity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class takes care of storing the user data in SQLite database.
 * Whenever we needs to get the logged in user information,
 * we fetch from SQLite instead of making request to server.
 */
public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private String emailLogged;
    private String uidCandidatePrivate;
    private String uidRecruiterPrivate;


    //All Static variables
    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "Ergasia";

    //Login table name
    private static final String TABLE_USER = "users";

    //Candidate table name
    private static final String TABLE_NEW_CANDIDATE = "candidates";

    //Recruiter table name
    private static final String TABLE_NEW_OFFER = "recruiters";

    //Login table colunms names
    private static final String KEY_ID = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uidUser";
    private static final String KEY_CREATED_AT = "created_at";

    private static final String KEY_ID_CANDIDATE = "id";
    private static final String KEY_NAME_CANDIDATE = "name";
    private static final String KEY_FIRSTNAME_CANDIDATE = "firstname";
    private static final String KEY_TRAINING_CANDIDATE = "training";
    private static final String KEY_AREA_ACTIVITY_CANDIDATE = "area_activity";
    private static final String KEY_TYPE_CANDIDATE = "type";
    private static final String KEY_LANGUAGE1_CANDIDATE = "language1";
    private static final String KEY_LEVEL_LANGUAGE1_CANDIDATE = "level_language1";
    private static final String KEY_LANGUAGE2_CANDIDATE = "language2";
    private static final String KEY_LEVEL_LANGUAGE2_CANDIDATE = "level_language2";
    private static final String KEY_LANGUAGE3_CANDIDATE = "language3";
    private static final String KEY_LEVEL_LANGUAGE3_CANDIDATE = "level_language3";
    private static final String KEY_SKILL_CANDIDATE = "skill";
    private static final String KEY_GEOLOCATION_CANDIDATE = "geolocation";
    private static final String KEY_UID_CANDIDATE = "uidCandidate";
    private static final String KEY_CREATED_AT_CANDIDATE = "created_at";
    private static final String KEY_ID_USERS_FK_CANDIDATE = "id_users_fk";

    //Recruiter table colunms names
    private static final String KEY_ID_RECRUITER = "id";
    private static final String KEY_COMPANY_RECRUITER = "company";
    private static final String KEY_JOB_TITLE_RECRUITER = "job_title";
    private static final String KEY_AREA_ACTIVITY_RECRUITER = "area_activity";
    private static final String KEY_TYPE_RECRUITER = "type";
    private static final String KEY_GEOLOCATION_RECRUITER = "geolocation";
    private static final String KEY_SKILL_RECRUITER = "skill";
    private static final String KEY_UID_RECRUITER = "uidOffer";
    private static final String KEY_CREATED_AT_RECRUITER = "created_at";
    private static final String KEY_ID_USERS_FK_RECRUITER = "id_users_fk";

    private View_offer_activity viewOfferActivity = new View_offer_activity();

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println(db.getVersion());



        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_UID + " TEXT," + KEY_NAME + " TEXT,"
                + KEY_FIRSTNAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);



        String CREATE_NEW_CANDIDATE_TABLE = "CREATE TABLE " + TABLE_NEW_CANDIDATE + "("
                + KEY_ID_CANDIDATE + " INTEGER PRIMARY KEY," +  KEY_NAME_CANDIDATE + " TEXT,"
                + KEY_FIRSTNAME_CANDIDATE + " TEXT," + KEY_TRAINING_CANDIDATE + " TEXT,"
                + KEY_AREA_ACTIVITY_CANDIDATE + " TEXT," + KEY_TYPE_CANDIDATE + " TEXT,"
                + KEY_LANGUAGE1_CANDIDATE + " TEXT," + KEY_LEVEL_LANGUAGE1_CANDIDATE + " TEXT,"
                + KEY_LANGUAGE2_CANDIDATE + " TEXT," + KEY_LEVEL_LANGUAGE2_CANDIDATE + " TEXT,"
                + KEY_LANGUAGE3_CANDIDATE+ " TEXT," + KEY_LEVEL_LANGUAGE3_CANDIDATE + " TEXT,"
                + KEY_SKILL_CANDIDATE + " TEXT," + KEY_GEOLOCATION_CANDIDATE + " TEXT,"
                + KEY_UID_CANDIDATE + " TEXT," + KEY_ID_USERS_FK_CANDIDATE + " INTEGER,"
                + KEY_CREATED_AT_CANDIDATE + " TEXT," + " FOREIGN KEY ("+KEY_ID_USERS_FK_CANDIDATE+") REFERENCES " + TABLE_USER+ " ("+KEY_ID+") ON UPDATE CASCADE)";
        db.execSQL(CREATE_NEW_CANDIDATE_TABLE);

        String CREATE_NEW_OFFER_TABLE = "CREATE TABLE " + TABLE_NEW_OFFER + "("
                + KEY_ID_RECRUITER + " INTEGER PRIMARY KEY," + KEY_COMPANY_RECRUITER + " TEXT,"
                + KEY_JOB_TITLE_RECRUITER + " TEXT," + KEY_AREA_ACTIVITY_RECRUITER + " TEXT,"
                + KEY_TYPE_RECRUITER + " TEXT," + KEY_GEOLOCATION_RECRUITER + " TEXT,"
                + KEY_SKILL_RECRUITER + " TEXT," + KEY_UID_RECRUITER + " TEXT,"
                + KEY_CREATED_AT_RECRUITER + " TEXT," + KEY_ID_USERS_FK_RECRUITER + " INTEGER," + " FOREIGN KEY ("+ KEY_ID_USERS_FK_RECRUITER +") REFERENCES " + TABLE_USER+ " (" + KEY_ID + ") ON UPDATE CASCADE)";
        db.execSQL(CREATE_NEW_OFFER_TABLE);

        Log.d(TAG, "Database tables created");


    }

    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEW_CANDIDATE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEW_OFFER);


            //Create tables again
            onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db,int oldVersion, int newVersion) {
        //Drop older table if existed

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEW_CANDIDATE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEW_OFFER);

            //Create tables again
            onCreate(db);
    }

    /**
     * Storing user details in database
     *
     * @param name
     * @param firstname
     * @param email
     * @param uidUser
     * @param created_at
     */
    public void addUser(String name, String firstname, String email, String uidUser, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UID, uidUser); //unique id
        values.put(KEY_NAME, name); //Name
        values.put(KEY_FIRSTNAME, firstname); //firstname
        values.put(KEY_EMAIL, email); //email
        values.put(KEY_CREATED_AT, created_at); //created_at
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
                user.put("uidUser", cursor.getString(4));
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
     * @param uidCandidate
     * @param created_at
     */
    public void addNewCandidate(String name, String firstname, String training, String area_activity, String type,
                                String language1, String level_language1, String language2, String level_language2,
                                String language3, String level_language3, String skill, String geolocation,
                                String uidCandidate, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");

        ContentValues values = new ContentValues();

        values.put(KEY_NAME_CANDIDATE, name);
        values.put(KEY_FIRSTNAME_CANDIDATE, firstname);
        values.put(KEY_TRAINING_CANDIDATE, training);
        values.put(KEY_AREA_ACTIVITY_CANDIDATE, area_activity);
        values.put(KEY_TYPE_CANDIDATE, type);
        values.put(KEY_LANGUAGE1_CANDIDATE, language1);
        values.put(KEY_LEVEL_LANGUAGE1_CANDIDATE, level_language1);
        values.put(KEY_LANGUAGE2_CANDIDATE, language2);
        values.put(KEY_LEVEL_LANGUAGE2_CANDIDATE, level_language2);
        values.put(KEY_LANGUAGE3_CANDIDATE, language3);
        values.put(KEY_LEVEL_LANGUAGE3_CANDIDATE, level_language3);
        values.put(KEY_SKILL_CANDIDATE, skill);
        values.put(KEY_GEOLOCATION_CANDIDATE, geolocation);
        values.put(KEY_UID_CANDIDATE, uidCandidate); //unique id
        values.put(KEY_CREATED_AT_CANDIDATE, created_at); //created_at
        //Inserting Row
        long id = db.insert(TABLE_NEW_CANDIDATE, null, values);
        db.close(); //Closing database connection

        Log.d(TAG, "New candidate inserted into sqlite; " + id);
        uidCandidatePrivate = uidCandidate;
    }

    public String getUidCandidatePrivate() {
        return uidCandidatePrivate;
    }

    public void updateCandidate(String name, String firstname, String training, String area_activity, String type,
                                String language1, String level_language1, String language2, String level_language2,
                                String language3, String level_language3, String skill, String geolocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");

        ContentValues values = new ContentValues();

        values.put(KEY_NAME_CANDIDATE, name);
        values.put(KEY_FIRSTNAME_CANDIDATE, firstname);
        values.put(KEY_TRAINING_CANDIDATE, training);
        values.put(KEY_AREA_ACTIVITY_CANDIDATE, area_activity);
        values.put(KEY_TYPE_CANDIDATE, type);
        values.put(KEY_LANGUAGE1_CANDIDATE, language1);
        values.put(KEY_LEVEL_LANGUAGE1_CANDIDATE, level_language1);
        values.put(KEY_LANGUAGE2_CANDIDATE, language2);
        values.put(KEY_LEVEL_LANGUAGE2_CANDIDATE, level_language2);
        values.put(KEY_LANGUAGE3_CANDIDATE, language3);
        values.put(KEY_LEVEL_LANGUAGE3_CANDIDATE, level_language3);
        values.put(KEY_SKILL_CANDIDATE, skill);
        values.put(KEY_GEOLOCATION_CANDIDATE, geolocation);
        //Inserting Row
        long id=db.update(TABLE_NEW_CANDIDATE, values, KEY_UID_CANDIDATE+ "=" + getUidCandidatePrivate() , null);

        db.close(); //Closing database connection

        Log.d(TAG, "Candidate updated into sqlite; " + id);
    }

    /**
     * Getting candidate data from database
     */
    public HashMap<String, String> getCandidateDetails() {

        HashMap<String, String> candidate = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM "+TABLE_NEW_CANDIDATE;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Move to first row
        cursor.moveToFirst();
        System.out.println(cursor.getCount());

        if (cursor.getCount()>0) {
            candidate.put("name", cursor.getString(1));
            candidate.put("firstname", cursor.getString(2));
            candidate.put("training", cursor.getString(3));
            candidate.put("area_activity", cursor.getString(4));
            candidate.put("type", cursor.getString(5));
            candidate.put("language1", cursor.getString(6));
            candidate.put("level_language1", cursor.getString(7));
            candidate.put("language2", cursor.getString(8));
            candidate.put("level_language2", cursor.getString(9));
            candidate.put("language3", cursor.getString(10));
            candidate.put("level_language3", cursor.getString(11));
            candidate.put("skill", cursor.getString(12));
            candidate.put("geolocation", cursor.getString(13));
            candidate.put("uidCandidate", cursor.getString(14));
            candidate.put("created_at", cursor.getString(15));

        }
        cursor.close();
        db.close();

        //Return user
        Log.d(TAG, "Fetching new candidate from Sqlite: " + candidate.toString());
        return candidate;
    }

    public void deleteCandidates() {
        SQLiteDatabase db = this.getWritableDatabase();
        //Delete all rows
        db.delete(TABLE_NEW_CANDIDATE, null , null);
        db.close();

        Log.d(TAG, "Deleted all candidate info from Sqlite");
    }

    /**
     * add an offer into the database
     * @param company
     * @param jobTitle
     * @param areaActivity
     * @param type
     * @param geolocation
     * @param skill
     * @param uidOffer
     * @param created_at
     */
    public void addOffer(String company, String jobTitle, String areaActivity, String type,
                         String geolocation, String skill, String uidOffer,String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");

        ContentValues values = new ContentValues();
        values.put(KEY_COMPANY_RECRUITER, company);
        values.put(KEY_JOB_TITLE_RECRUITER, jobTitle);
        values.put(KEY_AREA_ACTIVITY_RECRUITER, areaActivity);
        values.put(KEY_TYPE_RECRUITER, type);
        values.put(KEY_GEOLOCATION_RECRUITER, geolocation);
        values.put(KEY_SKILL_RECRUITER, skill);
        values.put(KEY_UID_RECRUITER, uidOffer); //unique id
        values.put(KEY_CREATED_AT_RECRUITER, created_at); //created_at
        //Inserting Row
        long id = db.insert(TABLE_NEW_OFFER, null, values);
        db.close(); //Closing database connection

        uidRecruiterPrivate = uidOffer;
        Log.d(TAG, "New offer inserted into sqlite; " + id);
    }

    public String getUidRecruiterPrivate() {
        return uidRecruiterPrivate;
    }

    public void updateRecruiter(String company, String jobTitle, String area_activity, String type,
                                String geolocation, String skill) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");

        ContentValues values = new ContentValues();

        values.put(KEY_COMPANY_RECRUITER, company);
        values.put(KEY_JOB_TITLE_RECRUITER, jobTitle);
        values.put(KEY_AREA_ACTIVITY_CANDIDATE, area_activity);
        values.put(KEY_TYPE_CANDIDATE, type);
        values.put(KEY_GEOLOCATION_CANDIDATE, geolocation);
        values.put(KEY_SKILL_CANDIDATE, skill);

        //Inserting Row
        long id=db.update(TABLE_NEW_OFFER, values, KEY_UID_RECRUITER+ "=" + getUidRecruiterPrivate(), null);

        db.close(); //Closing database connection

        Log.d(TAG, "Recruiter updated into sqlite; " + id);
    }

    /**
     * Getting user data from database
     */
    public Cursor getOfferDetails(String jobChoosen) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NEW_OFFER
                +" WHERE "+ KEY_JOB_TITLE_RECRUITER+ " = " + "'" +jobChoosen+"'" ;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor getJobTitles() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + KEY_JOB_TITLE_RECRUITER + " FROM " + TABLE_NEW_OFFER;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    /**
     * delete offers
     */
    public void deleteOffers() {
        SQLiteDatabase db = this.getWritableDatabase();
        //Delete all rows
        db.delete(TABLE_NEW_OFFER, null , null);
        db.close();

        Log.d(TAG, "Deleted the offer info from Sqlite");
    }

    public String getKeyJobTitleRecruiter() {
        return KEY_JOB_TITLE_RECRUITER;
    }
}





