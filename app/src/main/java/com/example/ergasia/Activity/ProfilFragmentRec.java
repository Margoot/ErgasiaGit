package com.example.ergasia.Activity;

import android.app.Fragment;
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
import com.example.ergasia.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProfilFragmentRec extends Fragment {

    private ListView myListView;
    private SQLiteHandler db;


    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String[] jobs = {"ingénieur", "avocat", "peintre", "DG", "responsable",
                "vendeur"};
        View view = inflater.inflate(R.layout.fragment_profil_rec, container, false);

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < jobs.length; ++i) {
            list.add(jobs[i]);
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);

        myListView = (ListView) getActivity().findViewById(R.id.offerListView);

        // on assigne l'adapter à notre list
        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // position of the item in our list
                int itemPosition = position;

                // get back the clicked item
                String itemValue = (String) myListView
                        .getItemAtPosition(position);

                // display of the text with a toast ( just for test )
                Toast.makeText(
                        getActivity(),
                        "Position :" + itemPosition + "  ListItem : "
                                + itemValue, Toast.LENGTH_LONG).show();

            }

        });
        return view;
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i=0 ; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }
    }


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
