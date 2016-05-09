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

        TextView textView1 = (TextView) rootView.findViewById(R.id.recDiplomaTextView);
        TextView textView2 = (TextView) rootView.findViewById(R.id.recAreaTextView);
        TextView textView3 = (TextView) rootView.findViewById(R.id.recAreaSmallTextView);
        TextView textView4 = (TextView) rootView.findViewById(R.id.recTypeView);
        TextView textView5 = (TextView) rootView.findViewById(R.id.recTypeSmallTextView);
        TextView textView6 = (TextView) rootView.findViewById(R.id.recLanguageTextView);
        TextView textView7 = (TextView) rootView.findViewById(R.id.recLanguageSmallTextView);
        TextView textView8 = (TextView) rootView.findViewById(R.id.recSkillsTextView);
        TextView textView9 = (TextView) rootView.findViewById(R.id.recSkillsSmallTextView);
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

        return rootView;
    }
}
