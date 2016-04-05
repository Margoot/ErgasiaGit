package com.example.ergasia;

import android.app.Activity;
import android.os.Bundle;

public class View_offer_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
