package com.example.ergasia;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by simonthome on 02/04/16.
 */
public class ProfilFragmentPost extends Fragment {

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
        TextView textView6 = (TextView) rootView.findViewById(R.id.job);
        TextView textView7 = (TextView) rootView.findViewById(R.id.editJob);
        TextView textView8 = (TextView) rootView.findViewById(R.id.language);
        TextView textView9 = (TextView) rootView.findViewById(R.id.editLanguage);
        TextView textView10 = (TextView) rootView.findViewById(R.id.skills);
        TextView textView11 = (TextView) rootView.findViewById(R.id.skillsEditText);
        TextView textView12 = (TextView) rootView.findViewById(R.id.geolocation);
        TextView textView13 = (TextView) rootView.findViewById(R.id.geolocSwitch);
        TextView textView14 = (TextView) rootView.findViewById(R.id.autoCompleteTextView);
        TextView textView15 = (TextView) rootView.findViewById(R.id.name);
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

        return rootView;
    }
}
