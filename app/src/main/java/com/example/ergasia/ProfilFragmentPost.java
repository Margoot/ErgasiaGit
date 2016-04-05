package com.example.ergasia;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return rootView;
    }
}
