package com.example.ergasia.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ergasia.R;
import com.example.ergasia.Helper.SQLiteHandler;
import com.example.ergasia.Helper.SessionManager;

import java.util.HashMap;

/**
 * Created by simonthome on 02/04/16.
 */
public class ProfilFragmentPost extends Fragment {

    private SQLiteHandler db;
    private SessionManager session;

    private EditText inputName;
    private EditText inputFirstname;
    private TextView inputTraining;
    private EditText inputAreaActivity;
    private EditText inputType;
    private EditText inputLanguage1;
    private RatingBar inputLevelLanguage1;
    private EditText inputLanguage2;
    private RatingBar inputLevelLanguage2;
    private EditText inputLanguage3;
    private RatingBar inputLevelLanguage3;
    private EditText inputSkill;
    private EditText inputLocation;


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

        TextView textView1 = (TextView) rootView.findViewById(R.id.editName);
        TextView textView2 = (TextView) rootView.findViewById(R.id.firstName);
        TextView textView3 = (TextView) rootView.findViewById(R.id.editFirstName);
        TextView textView4 = (TextView) rootView.findViewById(R.id.training);
        TextView textView5 = (TextView) rootView.findViewById(R.id.area);
        TextView textView6 = (TextView) rootView.findViewById(R.id.type);
        TextView textView7 = (TextView) rootView.findViewById(R.id.editType);
        TextView textView8 = (TextView) rootView.findViewById(R.id.language);
        TextView textView9 = (TextView) rootView.findViewById(R.id.editLanguage1);
        TextView textView10 = (TextView) rootView.findViewById(R.id.skills);
        TextView textView11 = (TextView) rootView.findViewById(R.id.skillsEditText);
        TextView textView12 = (TextView) rootView.findViewById(R.id.geolocation);
        TextView textView13 = (TextView) rootView.findViewById(R.id.geolocSwitch);
        TextView textView14 = (TextView) rootView.findViewById(R.id.locationEditText);
        TextView textView15 = (TextView) rootView.findViewById(R.id.name);
        TextView textView16 = (TextView) rootView.findViewById(R.id.editArea);
        TextView textView17 = (TextView) rootView.findViewById(R.id.editLanguage2);
        TextView textView18 = (TextView) rootView.findViewById(R.id.editLanguage3);
        TextView textView19 = (TextView) rootView.findViewById(R.id.trainingEditText);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BigCaslon.ttf");
        textView1.setTypeface(font);
        textView2.setTypeface(font);
        textView3.setTypeface(font);
        textView4.setTypeface(font);
        textView5.setTypeface(font);
        textView6.setTypeface(font);
        textView7.setTypeface(font);
        textView8.setTypeface(font);
        textView9.setTypeface(font);
        textView10.setTypeface(font);
        textView11.setTypeface(font);
        textView12.setTypeface(font);
        textView13.setTypeface(font);
        textView14.setTypeface(font);
        textView15.setTypeface(font);
        textView16.setTypeface(font);
        textView17.setTypeface(font);
        textView18.setTypeface(font);
        textView19.setTypeface(font);

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

        db = new SQLiteHandler(getActivity().getApplicationContext());
        session = new SessionManager(getActivity().getApplicationContext());
        db.getOfferDetails();
        //Fetching candidate details from SQlite
        HashMap<String, String> candidate = db.getCandidateDetails();
        String name = candidate.get("name");
        //System.out.println(name);
        String firstname = candidate.get("firstname");
        String training = candidate.get("training");
        String areaActivity = candidate.get("area_activity");
        String type = candidate.get("type");
        String language1 = candidate.get("language1");
        //String levelLanguage1 = candidate.get("level_language1");
        String language2 = candidate.get("language2");
        //String levelLanguage2 = candidate.get("level_language2");
        String language3 = candidate.get("language3");
        //String levelLanguage3 = candidate.get("level_language3");
        String skill = candidate.get("skill");
        String geolocation = candidate.get("geolocation");

        inputName.setText(name);
        inputFirstname.setText(firstname);
        inputTraining.setText(training);
        inputAreaActivity.setText(areaActivity);
        inputType.setText(type);
        inputLanguage1.setText(language1);
        //inputLevelLanguage1.setRating(Float.parseFloat(levelLanguage1));
        inputLanguage2.setText(language2);
        //inputLevelLanguage2.setRating(Float.parseFloat(levelLanguage2));
        inputLanguage3.setText(language3);
        //inputLevelLanguage3.setRating(Float.parseFloat(levelLanguage3));
        inputSkill.setText(skill);
        inputLocation.setText(geolocation);

        return rootView;
    }
}
