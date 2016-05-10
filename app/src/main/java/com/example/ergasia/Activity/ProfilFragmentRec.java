package com.example.ergasia.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ergasia.Helper.SQLiteHandler;
import com.example.ergasia.Helper.SessionManager;
import com.example.ergasia.R;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfilFragmentRec extends ListFragment {

    private SQLiteHandler db;
    private SessionManager session;

    public static ProfilFragmentRec newInstance() {
        ProfilFragmentRec fragment = new ProfilFragmentRec();
        return fragment;
    }

    public ProfilFragmentRec(){

    }
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savesInstanceState) {
        return inflater.inflate(R.layout.fragment_profil_rec, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = new SQLiteHandler(getActivity().getApplicationContext());

        HashMap<String, String> recruiter = db.getOfferDetails();
        String jobTitle = recruiter.get("job_title");
        System.out.println(jobTitle);

        String[] offers = new String[] {jobTitle};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, offers);
        setListAdapter(adapter);


    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), View_offer_activity.class);
        startActivity(intent);
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
