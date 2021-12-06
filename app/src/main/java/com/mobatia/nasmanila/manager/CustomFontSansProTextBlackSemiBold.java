package com.mobatia.nasmanila.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by gayatri on 24/1/17.
 */

public class CustomFontSansProTextBlackSemiBold extends androidx.appcompat.widget.AppCompatTextView {
    public CustomFontSansProTextBlackSemiBold(Context context) {
        super(context);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Semibold.otf" );
        this.setTypeface(type);
        //this.setTextColor(context.getResources().getColor(R.color.black));
    }

    public CustomFontSansProTextBlackSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Semibold.otf");
        this.setTypeface(type);
       //this.setTextColor(context.getResources().getColor(R.color.black));

    }

    public CustomFontSansProTextBlackSemiBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Semibold.otf" );
        this.setTypeface(type);
       // this.setTextColor(context.getResources().getColor(R.color.black));

    }


}
