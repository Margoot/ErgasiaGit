package com.example.ergasia.Activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ergasia.R;
import com.example.ergasia.Helper.SQLiteHandler;
import com.example.ergasia.Helper.SessionManager;
import com.example.ergasia.app.AppConfig;
import com.example.ergasia.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

/**
 * Created by simonthome on 02/04/16.
 */
public class ProfilFragmentPost extends Fragment {

    private static final String TAG = ProfilFragmentPost.class.getSimpleName();
    private SQLiteHandler db;
    private SessionManager session;

    private static EditText inputName;
    private static EditText inputFirstname;
    private static TextView inputTraining;
    private static Spinner spinnerTraining;
    private static EditText inputAreaActivity;
    private static EditText inputType;
    private static RadioGroup inputTypeGroup;
    private static EditText inputLanguage1;
    private static RatingBar inputLevelLanguage1;
    private static EditText inputLanguage2;
    private static RatingBar inputLevelLanguage2;
    private static EditText inputLanguage3;
    private static RatingBar inputLevelLanguage3;
    private static EditText inputSkill;
    private static EditText inputLocation;
    private static Button finishButton;
    private static ProgressDialog pDialog;
    private String uidCandidate;


    public static ProfilFragmentPost newInstance() {
        ProfilFragmentPost fragment = new ProfilFragmentPost();
        return fragment;
    }

    public ProfilFragmentPost(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profil_post, container, false);

        inputName = (EditText) rootView.findViewById(R.id.editName);
        inputFirstname = (EditText) rootView.findViewById(R.id.editFirstName);
        inputTraining = (EditText) rootView.findViewById(R.id.trainingEditText);
        inputAreaActivity = (EditText) rootView.findViewById(R.id.editArea);
        inputType = (EditText) rootView.findViewById(R.id.editType);
        inputLanguage1 = (EditText) rootView.findViewById(R.id.editLanguage1);
        inputLevelLanguage1 = (RatingBar) rootView.findViewById(R.id.ratingBar1);
        inputLanguage2 = (EditText) rootView.findViewById(R.id.editLanguage2);
        inputLevelLanguage2 = (RatingBar) rootView.findViewById(R.id.ratingBar2);
        inputLanguage3 = (EditText) rootView.findViewById(R.id.editLanguage3);
        inputLevelLanguage3 = (RatingBar) rootView.findViewById(R.id.ratingBar3);
        inputSkill = (EditText) rootView.findViewById(R.id.skillsEditText);
        inputLocation = (EditText) rootView.findViewById(R.id.locationEditText);
        finishButton = (Button) rootView.findViewById(R.id.finishModification);
        inputTypeGroup = (RadioGroup) rootView.findViewById(R.id.radioButtonType);

        spinnerTraining = (Spinner) rootView.findViewById(R.id.trainingSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.training_array,
                android.R.layout.simple_spinner_item);
        spinnerTraining.setAdapter(adapter);
        //spinnerTraining.setOnItemSelectedListener(this);

        //Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        db = new SQLiteHandler(getActivity().getApplicationContext());
        session = new SessionManager(getActivity().getApplicationContext());
        //Fetching candidate details from SQlite
        HashMap<String, String> candidate = db.getCandidateDetails();
        String name = candidate.get("name");
        String firstname = candidate.get("firstname");
        String training = candidate.get("training");
        String areaActivity = candidate.get("area_activity");
        String type = candidate.get("type");
        String language1 = candidate.get("language1");
        String levelLanguage1 = candidate.get("level_language1");
        String language2 = candidate.get("language2");
        String levelLanguage2 = candidate.get("level_language2");
        String language3 = candidate.get("language3");
        String levelLanguage3 = candidate.get("level_language3");
        String skill = candidate.get("skill");
        String geolocation = candidate.get("geolocation");
        uidCandidate = candidate.get("uidCandidate");

        inputName.setText(name);
        inputFirstname.setText(firstname);
        inputTraining.setText(training);
        inputAreaActivity.setText(areaActivity);
        inputType.setText(type);
        inputLanguage1.setText(language1);
        inputLevelLanguage1.setRating(Float.parseFloat(levelLanguage1));
        inputLanguage2.setText(language2);
        inputLevelLanguage2.setRating(Float.parseFloat(levelLanguage2));
        inputLanguage3.setText(language3);
        inputLevelLanguage3.setRating(Float.parseFloat(levelLanguage3));
        inputSkill.setText(skill);
        inputLocation.setText(geolocation);

        //Create button click event
        finishButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputName.getText().toString().trim();
                String firstname = inputFirstname.getText().toString().trim();
                String training = spinnerTraining.getSelectedItem().toString();
                String areaActivity = inputAreaActivity.getText().toString().trim();
                String language1 = inputLanguage1.getText().toString().trim();
                String levelLanguage1 = String.valueOf(inputLevelLanguage1.getRating());
                String language2 = inputLanguage2.getText().toString().trim();
                String levelLanguage2 = String.valueOf(inputLevelLanguage2.getRating());
                String language3 = inputLanguage3.getText().toString().trim();
                String levelLanguage3 = String.valueOf(inputLevelLanguage3.getRating());
                String skill = inputSkill.getText().toString().trim();
                String geolocation = inputLocation.getText().toString().trim();
                //if condition to check if the user has chosen the type
                if (inputTypeGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getActivity().getApplicationContext(), "Veuillez choisir le type de contrat", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton selectedButton = (RadioButton) getView().findViewById(inputTypeGroup.getCheckedRadioButtonId());
                    String type = selectedButton.getText().toString();

                    //dans le else car !empty is verified

                    if (!name.isEmpty() && !firstname.isEmpty() && !training.isEmpty() && !areaActivity.isEmpty() &&
                            !language1.isEmpty() && !levelLanguage1.isEmpty() &&
                            !skill.isEmpty() && !geolocation.isEmpty()) {
                        updatedNewCandidate(uidCandidate,name, firstname, training, areaActivity, type,
                                language1, levelLanguage1, language2, levelLanguage2,
                                language3, levelLanguage3, skill, geolocation);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Veuillez entrer tous les champs !",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        return rootView;
    }

    public static void modifyCandidate() {
        inputType.setVisibility(View.GONE);
        inputTraining.setVisibility(View.GONE);
        inputTypeGroup.setVisibility(View.VISIBLE);
        spinnerTraining.setVisibility(View.VISIBLE);
        finishButton.setVisibility(View.VISIBLE);

        inputName.setFocusableInTouchMode(true);
        inputFirstname.setFocusableInTouchMode(true);
        inputTraining.setFocusableInTouchMode(true);
        inputAreaActivity.setFocusableInTouchMode(true);
        inputType.setFocusableInTouchMode(true);
        inputLanguage1.setFocusableInTouchMode(true);
        inputLevelLanguage1.setFocusableInTouchMode(true);
        inputLevelLanguage1.setIsIndicator(false);
        inputLanguage2.setFocusableInTouchMode(true);
        inputLevelLanguage2.setFocusableInTouchMode(true);
        inputLevelLanguage2.setIsIndicator(false);
        inputLanguage3.setFocusableInTouchMode(true);
        inputLevelLanguage3.setFocusableInTouchMode(true);
        inputLevelLanguage3.setIsIndicator(false);
        inputSkill.setFocusableInTouchMode(true);
        inputLocation.setFocusableInTouchMode(true);

    }

    private void updatedNewCandidate(final String uidCandidate,final String name, final String firstname, final String training,
                                      final String areaActivity, final String type,
                                      final String language1, final String levelLanguage1, final String language2,
                                      final String levelLanguage2, final String language3, final String levelLanguage3,
                                      final String skill, final String geolocation) {

        //Tag used to cancel the request
        String tag_string_req = "req_update_candidate";
        pDialog.setMessage("Enregistrement...");
        showDialog();

        //Request a string response from the provided URL
        StringRequest strReq = new StringRequest(POST,
                AppConfig.URL_UPDATE_CANDIDATE, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "New Candidate Update Response: " + response.toString());
                hideDialog();


                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        //the new candidate succesfully stored in MySQL
                        //Now store the candidate in Sqlite

                        JSONObject jUpdateCandidate = jObj.getJSONObject("candidate");

                        String name = jUpdateCandidate.getString("name");
                        String firstname = jUpdateCandidate.getString("firstname");
                        String training = jUpdateCandidate.getString("training");
                        String areaActivity = jUpdateCandidate.getString("area_activity");
                        String type = jUpdateCandidate.getString("type");
                        String language1 = jUpdateCandidate.getString("language1");
                        String levelLanguage1 = jUpdateCandidate.getString("level_language1");
                        String language2 = jUpdateCandidate.getString("language2");
                        String levelLanguage2 = jUpdateCandidate.getString("level_language2");
                        String language3 = jUpdateCandidate.getString("language3");
                        String levelLanguage3 = jUpdateCandidate.getString("level_language3");
                        String skill = jUpdateCandidate.getString("skill");
                        String geolocation = jUpdateCandidate.getString("geolocation");

                        //Inserting row in candidates table
                        db.updateCandidate(name, firstname, training, areaActivity, type,
                                language1, levelLanguage1, language2, levelLanguage2,
                                language3, levelLanguage3, skill, geolocation);
                        Toast.makeText(getActivity().getApplicationContext(), "Votre candidature a été mise à jour avec succès ! "
                                , Toast.LENGTH_LONG).show();

                        //display the new data from the database
                        showModifications(training,type);

                    } else {
                        //Error occured in registration. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity().getApplicationContext(), errorMsg,
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Update Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to new candidate url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", session.getEmail());
                params.put("unique_id_candidates", uidCandidate);
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

    public void showModifications(String training, String type) {

        inputTraining.setVisibility(View.VISIBLE);
        inputType.setVisibility(View.VISIBLE);

        inputTraining.setText(training);
        inputType.setText(type);

        finishButton.setVisibility(View.GONE);
        inputTypeGroup.setVisibility(View.GONE);
        spinnerTraining.setVisibility(View.GONE);

        inputName.setFocusableInTouchMode(false);
        inputFirstname.setFocusableInTouchMode(false);
        inputTraining.setFocusableInTouchMode(false);
        inputAreaActivity.setFocusableInTouchMode(false);
        inputType.setFocusableInTouchMode(false);
        inputLanguage1.setFocusableInTouchMode(false);
        inputLevelLanguage1.setFocusableInTouchMode(false);
        inputLanguage2.setFocusableInTouchMode(false);
        inputLevelLanguage2.setFocusableInTouchMode(false);
        inputLanguage3.setFocusableInTouchMode(false);
        inputLevelLanguage3.setFocusableInTouchMode(false);
        inputSkill.setFocusableInTouchMode(false);
        inputLocation.setFocusableInTouchMode(false);

        inputName.setFocusable(false);
        inputFirstname.setFocusable(false);
        inputTraining.setFocusable(false);
        inputAreaActivity.setFocusable(false);
        inputType.setFocusable(false);
        inputLanguage1.setFocusable(false);
        inputLevelLanguage1.setFocusable(false);
        inputLevelLanguage1.setIsIndicator(true);
        inputLanguage2.setFocusable(false);
        inputLevelLanguage2.setFocusable(false);
        inputLevelLanguage2.setIsIndicator(true);
        inputLanguage3.setFocusable(false);
        inputLevelLanguage3.setFocusable(false);
        inputLevelLanguage3.setIsIndicator(true);
        inputSkill.setFocusable(false);
        inputLocation.setFocusable(false);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}

