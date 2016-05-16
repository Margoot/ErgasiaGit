package com.example.ergasia.Activity;

/**
 * Created by simonthome on 03/04/16.
 */

import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ergasia.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CandidateFragmentRec extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private Button likeButton;
    private Button dislikeButton;

    public CandidateFragmentRec() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CandidateFragmentRec newInstance(int sectionNumber) {
        CandidateFragmentRec fragment = new CandidateFragmentRec();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_candidate_main, container, false);




        return rootView;
    }
}
