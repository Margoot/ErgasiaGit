package com.example.ergasia;

import android.os.Bundle;
import android.app.Activity;

public class New_offer_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
