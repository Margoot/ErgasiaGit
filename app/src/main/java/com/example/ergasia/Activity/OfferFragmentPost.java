package com.example.ergasia.Activity;

import android.support.v4.app.Fragment;
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

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_modify).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}
