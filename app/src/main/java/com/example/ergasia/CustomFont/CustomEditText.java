package com.example.ergasia.CustomFont;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.ergasia.Helper.CustomFontHelper;

public class CustomEditText extends EditText {


    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

}
