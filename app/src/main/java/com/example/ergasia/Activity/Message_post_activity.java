package com.example.ergasia.Activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ergasia.R;

public class Message_post_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_chat_rooms);
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
