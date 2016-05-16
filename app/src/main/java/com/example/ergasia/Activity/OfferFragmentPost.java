package com.example.ergasia.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.ergasia.R;

/**
 * Created by simonthome on 02/04/16.
 */
public class OfferFragmentPost extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ImageButton likeButton;
    private ImageButton dislikeButton;
    private int count;
    private TextView inputCompany;
    private TextView inputJobTitle;
    private TextView inputAreaActivity;
    private TextView inputType;
    private TextView inputGeolocation;
    private TextView inputSkill;

    public OfferFragmentPost() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static OfferFragmentPost newInstance(int sectionNumber) {
        OfferFragmentPost fragment = new OfferFragmentPost();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);



        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_offer_main, container, false);

        setHasOptionsMenu(true);

        likeButton = (ImageButton) rootView.findViewById(R.id.okButton);
        inputCompany = (TextView) rootView.findViewById(R.id.companySmallText2);
        inputJobTitle = (TextView) rootView.findViewById(R.id.jobTitleSmallTextView);
        inputAreaActivity = (TextView) rootView.findViewById(R.id.areaSmallTextView);
        inputType = (TextView) rootView.findViewById(R.id.typeSmallTextView);
        inputGeolocation = (TextView) rootView.findViewById(R.id.locationSmallTextView);
        inputSkill = (TextView) rootView.findViewById(R.id.skillsSmallTextView);


        count = 0;
        likeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                switch(count) {
                    case 0:
                    inputCompany.setText("France 2");
                    inputJobTitle.setText("Camera man");
                    inputAreaActivity.setText("Media");
                    inputType.setText("CDI");
                    inputGeolocation.setText("Lyon, France");
                    inputSkill.setText("video, son");
                        count++;
                        break;
                    case 1:
                        inputCompany.setText("Vinci");
                        inputJobTitle.setText("chef de chantier");
                        inputAreaActivity.setText("BTP");
                        inputType.setText("CDD");
                        inputGeolocation.setText("Rouen, France");
                        inputSkill.setText("savoir creuser");
                        count++;
                        break;
                    case 2:
                        inputCompany.setText("Sfr");
                        inputJobTitle.setText("Vendeur");
                        inputAreaActivity.setText("Telephonie");
                        inputType.setText("CDD");
                        inputGeolocation.setText("Paris, France");
                        inputSkill.setText("parler");
                    case 3:
                        Intent intent = new Intent(getActivity(), Tie_post_activity.class);
                        getActivity().startActivity(intent);


                }




            }
        });


        dislikeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                inputCompany.setText("Auchun");
                inputJobTitle.setText("Caissier");
                inputAreaActivity.setText("Magasin");
                inputType.setText("CDI");
                inputGeolocation.setText("Lyon, France");
                inputSkill.setText("Manger");
            }
        });

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_modify).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
