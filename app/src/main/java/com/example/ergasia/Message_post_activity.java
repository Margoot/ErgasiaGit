package com.example.ergasia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Message_post_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_post_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);



        TextView myTextView = (TextView) findViewById(R.id.messagePostEditText);


        TextView textView1  = (TextView)findViewById(R.id.container);
        TextView textView2  = (TextView)findViewById(R.id.messagePostEditText);
        TextView textView3  = (TextView)findViewById(R.id.chatSendButton2);
        setFont(textView1,"BigCaslon.ttf");
        setFont(textView2,"BigCaslon.ttf");
        setFont(textView3,"BigCaslon.ttf");

    }

    /**
     * function setFont which use to customize the font of the view
     * @param textView
     * @param fontName
     */
    private void setFont(TextView textView, String fontName) {
        if(fontName != null){
            try {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fontName);
                textView.setTypeface(typeface);
            } catch (Exception e) {
                Log.e("FONT", fontName + " not found", e);
            }
        }
    }

}
