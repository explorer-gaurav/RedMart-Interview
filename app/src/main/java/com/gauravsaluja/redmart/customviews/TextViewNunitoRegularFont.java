package com.gauravsaluja.redmart.customviews;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.gauravsaluja.redmart.utils.Helpers;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Custom textview of typeface Nunito Regular font
 */

public class TextViewNunitoRegularFont extends AppCompatTextView {

    public TextViewNunitoRegularFont(Context context) {
        super(context);
        setCustomFont(context);
    }

    public TextViewNunitoRegularFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context);
    }

    public TextViewNunitoRegularFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context);
    }

    // set custom font
    private void setCustomFont(Context context) {
        if (context != null && !isInEditMode()) {
            setTypeface(Helpers.getNunitoTypeFaceRegular(context));
        }
    }
}