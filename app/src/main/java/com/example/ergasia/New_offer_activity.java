package com.example.ergasia;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

public class New_offer_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView1  = (TextView)findViewById(R.id.cdiRadioButton);
        TextView  textView2  = (TextView)findViewById(R.id.cddRadioButton);
        TextView  textView3  = (TextView)findViewById(R.id.internRadioButton);
        TextView  textView4  = (TextView)findViewById(R.id.newAreaTextView);
        TextView  textView5  = (TextView)findViewById(R.id.newAreaEditText);
        TextView  textView6  = (TextView)findViewById(R.id.newLocationTextView);
        TextView  textView7  = (TextView)findViewById(R.id.newLocationTextEdit);
        TextView  textView8  = (TextView)findViewById(R.id.newWageTextView);
        TextView  textView9  = (TextView)findViewById(R.id.newWageTextEdit);
        TextView  textView10  = (TextView)findViewById(R.id.euro);
        TextView  textView11  = (TextView)findViewById(R.id.newSkillsTextView);
        TextView  textView12  = (TextView)findViewById(R.id.newSkillsTextEdit);
        TextView  textView13  = (TextView)findViewById(R.id.newJobTextView);
        TextView  textView14  = (TextView)findViewById(R.id.newJobEditText);
        TextView  textView15  = (TextView)findViewById(R.id.newTypeTextView);


        setFont(textView1, "BigCaslon.ttf");
        setFont(textView2, "BigCaslon.ttf");
        setFont(textView3, "BigCaslon.ttf");
        setFont(textView4, "BigCaslon.ttf");
        setFont(textView5, "BigCaslon.ttf");
        setFont(textView6, "BigCaslon.ttf");
        setFont(textView7, "BigCaslon.ttf");
        setFont(textView8, "BigCaslon.ttf");
        setFont(textView9, "BigCaslon.ttf");
        setFont(textView10, "BigCaslon.ttf");
        setFont(textView11, "BigCaslon.ttf");
        setFont(textView12, "BigCaslon.ttf");
        setFont(textView13, "BigCaslon.ttf");
        setFont(textView14, "BigCaslon.ttf");
        setFont(textView15, "BigCaslon.ttf");
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
