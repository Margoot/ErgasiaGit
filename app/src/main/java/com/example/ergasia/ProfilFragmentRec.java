package com.example.ergasia;

import android.content.Intent;
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
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main_tabbed_activity_rec, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id==R.id.action_add){
            Intent intentMenu = new Intent(getActivity(), New_offer_activity.class);
            startActivity(intentMenu);
        }
        return super.onOptionsItemSelected(item);
    }

}
