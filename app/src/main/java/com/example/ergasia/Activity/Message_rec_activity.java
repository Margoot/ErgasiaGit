package com.example.ergasia.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.example.ergasia.R;

public class Message_rec_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_rec_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
