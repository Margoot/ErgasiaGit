package com.example.ergasia.Activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.ergasia.R;

public class Tie_rec_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tie_rec_activity);

        TextView textView1  = (TextView)findViewById(R.id.tieTextView);
        TextView textView2  = (TextView)findViewById(R.id.tieNomPostTextView);
        TextView textView3  = (TextView)findViewById(R.id.sendMessageButton);
        TextView textView4  = (TextView)findViewById(R.id.contSearchButton);
        TextView textView5  = (TextView)findViewById(R.id.tieOfferRecTextView);

        setFont(textView1, "BigCaslon.ttf");
        setFont(textView2, "BigCaslon.ttf");
        setFont(textView3, "BigCaslon.ttf");
        setFont(textView4, "BigCaslon.ttf");
        setFont(textView5, "BigCaslon.ttf");
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
