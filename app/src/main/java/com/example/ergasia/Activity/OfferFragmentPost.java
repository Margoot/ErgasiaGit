package com.example.ergasia.Activity;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

        TextView textView1 = (TextView) rootView.findViewById(R.id.companyTextView2);
        TextView textView2 = (TextView) rootView.findViewById(R.id.companySmallText2);
        TextView textView3 = (TextView) rootView.findViewById(R.id.typeTextView);
        TextView textView4 = (TextView) rootView.findViewById(R.id.typeSmallTextView);
        TextView textView7 = (TextView) rootView.findViewById(R.id.areaTextView);
        TextView textView8 = (TextView) rootView.findViewById(R.id.areaSmallTextView);
        TextView textView9 = (TextView) rootView.findViewById(R.id.jobTextView);
        TextView textView10 = (TextView) rootView.findViewById(R.id.locationSmallTextView);
        TextView textView11 = (TextView) rootView.findViewById(R.id.jobTitleTextView2);
        TextView textView12 = (TextView) rootView.findViewById(R.id.jobTitleSmallTextView);
        TextView textView14 = (TextView) rootView.findViewById(R.id.skillsTextView);
        TextView textView15 = (TextView) rootView.findViewById(R.id.skillsSmallTextView);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BigCaslon.ttf");
        textView1.setTypeface(font);
        textView2.setTypeface(font);
        textView3.setTypeface(font);
        textView4.setTypeface(font);
        textView7.setTypeface(font);
        textView8.setTypeface(font);
        textView9.setTypeface(font);
        textView10.setTypeface(font);
        textView11.setTypeface(font);
        textView12.setTypeface(font);
        textView14.setTypeface(font);
        textView15.setTypeface(font);

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_modify).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
