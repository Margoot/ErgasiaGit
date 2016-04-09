package com.example.ergasia;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuInflater;
import android.widget.TextView;

/**
 * Created by simonthome on 02/04/16.
 */
public class ProfilFragmentRec extends Fragment {

    public static ProfilFragmentRec newInstance() {
        ProfilFragmentRec fragment = new ProfilFragmentRec();
        return fragment;
    }

    public ProfilFragmentRec(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profil_rec, container, false);

        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.offer_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            //Do whatever you want to do
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
