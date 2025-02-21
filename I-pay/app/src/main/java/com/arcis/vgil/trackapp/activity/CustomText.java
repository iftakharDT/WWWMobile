package com.arcis.vgil.trackapp.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomText extends TextView{

    private Context c;
    public CustomText (Context c) {
        super(c);
        this.c = c;
        Typeface face=Typeface.createFromAsset(c.getAssets(), "acmesab.TTF"); 
        setTypeface(face);

    }
    public CustomText (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.c = context;
        Typeface face=Typeface.createFromAsset(c.getAssets(), "acmesab.TTF"); 
        setTypeface(face);
    }

    public CustomText (Context context, AttributeSet attrs) {
        super(context, attrs);
        this.c = context;
		Typeface face=Typeface.createFromAsset(c.getAssets(), "acmesab.TTF"); 
        setTypeface(face);

    }


}