package com.example.ergasia;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class View_offer_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView1  = (TextView)findViewById(R.id.viewJobTextView);
        TextView textView2  = (TextView)findViewById(R.id.viewJobEditText);
        TextView textView3  = (TextView)findViewById(R.id.viewTypeTextView);
        TextView textView4  = (TextView)findViewById(R.id.cdiRadioButton2);
        TextView textView5  = (TextView)findViewById(R.id.cddRadioButton2);
        TextView textView6  = (TextView)findViewById(R.id.internRadioButton2);
        TextView textView7  = (TextView)findViewById(R.id.viewAreaTextView);
        TextView textView8  = (TextView)findViewById(R.id.viewAreaEditText);
        TextView textView9  = (TextView)findViewById(R.id.viewLocationTextView);
        TextView textView10  = (TextView)findViewById(R.id.viewLocationTextEdit);
        TextView textView11  = (TextView)findViewById(R.id.viewWageTextView);
        TextView textView12  = (TextView)findViewById(R.id.salaryEditText2);
        TextView textView13  = (TextView)findViewById(R.id.euro);
        TextView textView14  = (TextView)findViewById(R.id.viewSkillsTextView);
        TextView textView15  = (TextView)findViewById(R.id.viewSkillsTextEdit);

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
