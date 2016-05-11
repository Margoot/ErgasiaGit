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

        //Intent intent = getIntent();
        //int position = intent.getIntExtra("position", 0);



        TextView myTextView = (TextView) findViewById(R.id.message);


        TextView textView1  = (TextView)findViewById(R.id.container);
        TextView textView2  = (TextView)findViewById(R.id.message);
        TextView textView3  = (TextView)findViewById(R.id.btn_send);
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
