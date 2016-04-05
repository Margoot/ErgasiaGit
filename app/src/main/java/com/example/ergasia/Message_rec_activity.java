package com.example.ergasia;

import android.os.Bundle;
import android.app.Activity;

public class Message_rec_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_rec_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
