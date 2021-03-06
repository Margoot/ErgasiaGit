package com.example.ergasia.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ergasia.GPS.FilterWithSpaceAdapter;
import com.example.ergasia.GPS.GPSTracker;
import com.example.ergasia.GPS.PlaceJSONParser;
import com.example.ergasia.app.AppConfig;
import com.example.ergasia.app.AppController;
import com.example.ergasia.R;
import com.example.ergasia.Helper.SQLiteHandler;
import com.example.ergasia.Helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MulticastSocket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class New_post_activity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private static final String TAG = New_post_activity.class.getSimpleName();
    private SessionManager session;
    private ImageButton addLanguageButton;

    private TableRow tableRow2;
    private TableRow tableRow3;

    private int timesClick;

    private EditText inputName;
    private EditText inputFirstName;
    private Spinner spinnerTraining;
    private EditText inputAreaActivity;
    private RadioGroup inputType;
    private EditText inputLanguage1;
    private RatingBar inputLevelLanguage1;
    private EditText inputLanguage2;
    private RatingBar inputLevelLanguage2;
    private EditText inputLanguage3;
    private RatingBar inputLevelLanguage3;
    private EditText inputSkill;
    private EditText inputGeolocation;
    private Button validateButton;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private Toolbar toolbar;


    private Switch switchGeoloc;
    private AutoCompleteTextView geolocationEditText;
    GPSTracker gps;
    private ArrayList<String> addr;
    private String addStr;

    private SeekBar searchRadius;
    private TextView viewKm;
    private PlacesTask placesTask;
    private ParserTask parserTask;
    private boolean geolocActiv = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView1 = (TextView) findViewById(R.id.locationEditText);
        TextView textView2 = (TextView) findViewById(R.id.geolocSwitch);
        setFont(textView1, "BigCaslon.ttf");
        setFont(textView2, "BigCaslon.ttf");


        inputName = (EditText) findViewById(R.id.editName);
        inputFirstName = (EditText) findViewById(R.id.editFirstName);
        spinnerTraining = (Spinner) findViewById(R.id.trainingSpinner);
        inputAreaActivity = (EditText) findViewById(R.id.editArea);
        inputType = (RadioGroup) findViewById(R.id.radioButtonType);
        inputLanguage1 = (EditText) findViewById(R.id.editLanguage1);
        inputLevelLanguage1 = (RatingBar) findViewById(R.id.ratingBar1);
        inputLanguage2 = (EditText) findViewById(R.id.editLanguage2);
        inputLevelLanguage2 = (RatingBar) findViewById(R.id.ratingBar2);
        inputLanguage3 = (EditText) findViewById(R.id.editLanguage3);
        inputLevelLanguage3 = (RatingBar) findViewById(R.id.ratingBar3);
        inputSkill = (EditText) findViewById(R.id.skillsEditText);
        inputGeolocation = (EditText) findViewById(R.id.locationEditText);
        validateButton = (Button) findViewById(R.id.createButton);

        spinnerTraining = (Spinner) findViewById(R.id.trainingSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.training_array,
                android.R.layout.simple_spinner_item);
        spinnerTraining.setAdapter(adapter);
        spinnerTraining.setOnItemSelectedListener(this);

        //Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //Session manager
        session = new SessionManager(getApplicationContext()); //return the context for the entire application

        //SQlite database handler
        db = new SQLiteHandler(getApplicationContext());


        //Create button click event
        validateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputName.getText().toString().trim();
                String firstname = inputFirstName.getText().toString().trim();
                String training = spinnerTraining.getSelectedItem().toString();
                String areaActivity = inputAreaActivity.getText().toString().trim();

                //if condition to check if the user has chosen the type
                if (inputType.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Veuillez choisir le type de contrat", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton selectedButton = (RadioButton) findViewById(inputType.getCheckedRadioButtonId());
                    String type = selectedButton.getText().toString();

                    String language1 = inputLanguage1.getText().toString().trim();
                    String levelLanguage1 = String.valueOf(inputLevelLanguage1.getRating());
                    String language2 = inputLanguage2.getText().toString().trim();
                    String levelLanguage2 = String.valueOf(inputLevelLanguage2.getRating());
                    String language3 = inputLanguage3.getText().toString().trim();
                    String levelLanguage3 = String.valueOf(inputLevelLanguage3.getRating());
                    String skill = inputSkill.getText().toString().trim();
                    String geolocation = inputGeolocation.getText().toString().trim();

                    //dans le else car !empty is verified

                    if (!name.isEmpty() && !firstname.isEmpty() && !training.isEmpty() && !areaActivity.isEmpty() &&
                            !language1.isEmpty() && !levelLanguage1.isEmpty() &&
                            !skill.isEmpty() && !geolocation.isEmpty()) {
                        registerNewCandidate(name, firstname, training, areaActivity, type,
                                language1, levelLanguage1, language2, levelLanguage2,
                                language3, levelLanguage3, skill, geolocation);
                    } else {
                        Toast.makeText(getApplicationContext(), "Veuillez entrer tous les champs !",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        addListenerOnButton();

        //switch button to set the geolocation
        //= (TextView) findViewById(R.id.switch);
        switchGeoloc = (Switch) findViewById(R.id.geolocSwitch);
        geolocationEditText = (AutoCompleteTextView) findViewById(R.id.locationEditText);
        addr = new ArrayList<String>();

        //attach a listener to check for changes in state
        switchGeoloc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    geolocActiv = true;
                    //creation of class GPSTracker
                    gps = new GPSTracker(New_post_activity.this);

                    //check if GPS enabled
                    if (gps.canGetLocation()) {
                        addr = gps.getAddr();

                        addStr = "";
                        for (String elem : addr) {
                            addStr += elem;
                        }
                        geolocationEditText.setText(addStr);

                        geolocationEditText.setFocusable(false);
                        geolocationEditText.setFocusableInTouchMode(false);


                    } else {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }

                }
            }
        });

        if (geolocActiv == false) {
            geolocationEditText.setThreshold(1);
            geolocationEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    placesTask = new PlacesTask();
                    placesTask.execute(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            geolocationEditText.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event){
                    geolocationEditText.showDropDown();
                    return false;
                }
            });

        }


        searchRadius = (SeekBar) findViewById(R.id.seekBar);
        viewKm = (TextView) findViewById(R.id.kmTextView);
        searchRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;
            String numberKm="";
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                numberKm = String.valueOf(progressChanged) + " Km";
                viewKm.setText(numberKm);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();


            br.close();

        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches all places from GooglePlaces AutoComplete Web Service
    private class PlacesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... place) {
            // For storing data from web service
            String data = "";

            // Obtain browser key from https://code.google.com/apis/console
            String key = "key=AIzaSyCFELTNRhBDnrkOb7xibRodvn5FmPDHjQI";

            String input = "";

            try {
                input = "input=" + URLEncoder.encode(place[0], "utf-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            // place type to be searched
            String types = "types=geocode";

            // Sensor enabled
            String sensor = "sensor=false";

            // Building the parameters to the web service
            String parameters = input + "&" + types + "&" + sensor + "&" + key;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/place/autocomplete/" + output + "?" + parameters;
            try {
                // Fetching the data from we service
                data = downloadUrl(url);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Creating ParserTask
            parserTask = new ParserTask();

            // Starting Parsing the JSON string returned by Web Service
            parserTask.execute(result);
        }
    }
    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

        JSONObject jObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;

            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                // Getting the parsed data as a List construct
                places = placeJsonParser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {

            String[] from = new String[] { "description"};
            int[] to = new int[] { android.R.id.text1 };

            // Creating a SimpleAdapter for the AutoCompleteTextView
            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), result, android.R.layout.simple_dropdown_item_1line,from, to);

            // Setting the adapter
            geolocationEditText.setAdapter(adapter);
        }
    }







    /**
     * function setFont which use to customize the font of the view
     *
     * @param textView
     * @param fontName
     */
    private void setFont(TextView textView, String fontName) {
        if (fontName != null) {
            try {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fontName);
                textView.setTypeface(typeface);
            } catch (Exception e) {
                Log.e("FONT", fontName + " not found", e);
            }
        }
    }

    /**
     * Function to add new tableRow if the user clicks on the button "add"
     * We instantiate the variable timesClick to know if the user clicks for the first time
     * or the second time.
     */
    private void addListenerOnButton() {

        addLanguageButton = (ImageButton) findViewById(R.id.addLanguage);
        tableRow2 = (TableRow) findViewById(R.id.tableRow2);
        tableRow3 = (TableRow) findViewById(R.id.tableRow3);
        timesClick = 0;


        addLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (timesClick == 0) {
                    tableRow2.setVisibility(TableRow.VISIBLE);
                    inputLanguage2.setVisibility(RatingBar.VISIBLE);
                    inputLevelLanguage2.setVisibility(EditText.VISIBLE);
                    timesClick = 1;
                } else {
                    tableRow3.setVisibility(TableRow.VISIBLE);
                    inputLanguage3.setVisibility(RatingBar.VISIBLE);
                    inputLevelLanguage3.setVisibility(EditText.VISIBLE);
                }
            }
        });
    }


    private void registerNewCandidate(final String name, final String firstname, final String training,
                                      final String areaActivity, final String type,
                                      final String language1, final String levelLanguage1, final String language2,
                                      final String levelLanguage2, final String language3, final String levelLanguage3,
                                      final String skill, final String geolocation) {
        //Tag used to cancel the request
        String tag_string_req = "req_new_candidate";
        pDialog.setMessage("Enregistrement...");
        showDialog();

        //Request a string response from the provided URL
        StringRequest strReq = new StringRequest(POST,
                AppConfig.URL_NEW_CANDIDATE, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "New Candidate Register Response: " + response.toString());
                hideDialog();


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        //the new candidate succesfully stored in MySQL
                        //Now store the candidate in Sqlite

                        session.setCandidate(true);

                        //String uidCandidate = jObj.getString("uidCandidate");

                        JSONObject candidate = jObj.getJSONObject("candidate");
                        String uidCandidate = candidate.getString("uidCandidate");
                        String name = candidate.getString("name");
                        String firstname = candidate.getString("firstname");
                        String training = candidate.getString("training");
                        String areaActivity = candidate.getString("area_activity");
                        String type = candidate.getString("type");
                        String language1 = candidate.getString("language1");
                        String levelLanguage1 = candidate.getString("level_language1");
                        String language2 = candidate.getString("language2");
                        String levelLanguage2 = candidate.getString("level_language2");
                        String language3 = candidate.getString("language3");
                        String levelLanguage3 = candidate.getString("level_language3");
                        String skill = candidate.getString("skill");
                        String geolocation = candidate.getString("geolocation");
                        int id_users_fk = candidate.getInt("id_users_fk");
                        String created_at = candidate.getString("created_at");

                       // session.setCandidateName(firstname);

                        //Inserting row in candidates table
                        db.addNewCandidate(name, firstname, training, areaActivity, type,
                                language1, levelLanguage1, language2, levelLanguage2,
                                language3, levelLanguage3, skill, geolocation,
                                uidCandidate, created_at);
                        Toast.makeText(getApplicationContext(), "Votre candidature a été enregistré avec succès ! "
                                , Toast.LENGTH_LONG).show();

                        //Launch MainTabbedActivityPost activity
                        Intent intent = new Intent(New_post_activity.this, MainTabbedActivityPost.class);
                        startActivity(intent);
                        finish();
                    } else {
                        //Error occured in registration. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg,
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to new candidate url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", session.getEmail());
                params.put("name", name);
                params.put("firstname", firstname);
                params.put("training", training);
                params.put("area_activity", areaActivity);
                params.put("type", type);
                params.put("language1", language1);
                params.put("level_language1", levelLanguage1);
                params.put("language2", language2);
                params.put("level_language2", levelLanguage2);
                params.put("language3", language3);
                params.put("level_language3", levelLanguage3);
                params.put("skill", skill);
                params.put("geolocation", geolocation);
                return params;
            }
        };

        //Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }






    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    //Logout function
    private void logout() {
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Etes-vous sûr de vouloir vous déconnecter ?");
        alertDialogBuilder.setPositiveButton("Oui",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(session.PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(session.KEY_IS_LOGGEDIN, false);

                        //Saving the sharedpreferences
                        editor.commit();
                        db.deleteUsers();
                        db.deleteCandidates();
                        AppController.getInstance().getPrefManager().clear();

                        //Starting login activity
                        Intent intent = new Intent(New_post_activity.this, Post_rec_activity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("Non",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_offer_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView myText = (TextView) view;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
