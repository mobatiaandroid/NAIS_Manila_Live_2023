package com.mobatia.nasmanila.manager;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by gayatri on 24/1/17.
 */

public class CustomFontSansProTextBoldWithoutColor extends androidx.appcompat.widget.AppCompatTextView {
    public CustomFontSansProTextBoldWithoutColor(Context context) {
        super(context);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Bold.otf" );
        this.setTypeface(type);
    }

    public CustomFontSansProTextBoldWithoutColor(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Bold.otf");
        this.setTypeface(type);

    }

    public CustomFontSansProTextBoldWithoutColor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/SourceSansPro-Bold.otf" );
        this.setTypeface(type);

    }


}
