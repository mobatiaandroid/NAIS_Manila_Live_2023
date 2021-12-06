package com.mobatia.nasmanila.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;


/**
 * Created by gayatri on 24/1/17.
 */

public class CustomFontSansProEditTextNoColor extends androidx.appcompat.widget.AppCompatEditText {
    public CustomFontSansProEditTextNoColor(Context context) {
        super(context);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Regular.otf" );
        this.setTypeface(type);
//        this.setTextColor(context.getResources().getColor(R.color.white));
    }

    public CustomFontSansProEditTextNoColor(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Regular.otf");
        this.setTypeface(type);
//       this.setTextColor(context.getResources().getColor(R.color.white));

    }

    public CustomFontSansProEditTextNoColor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Regular.otf" );
        this.setTypeface(type);
//        this.setTextColor(context.getResources().getColor(R.color.white));

    }


}
