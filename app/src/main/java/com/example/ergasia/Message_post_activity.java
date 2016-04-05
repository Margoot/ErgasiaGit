package com.example.ergasia;

import android.app.Activity;
import android.os.Bundle;

public class Message_post_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_post_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
