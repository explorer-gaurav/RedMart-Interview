package com.gauravsaluja.redmart.customviews;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.gauravsaluja.redmart.R;
import com.gauravsaluja.redmart.utils.Helpers;

/**
 * Created by Gaurav Saluja on 22-Apr-18.
 *
 * Custom clickable span to remove underline
 */

public class CustomSpannable extends ClickableSpan {

    private Context context;
    private boolean isUnderline;

    public CustomSpannable(Context context, boolean isUnderline) {
        this.context = context;
        this.isUnderline = isUnderline;
    }

    // set color and typeface
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(isUnderline);
        ds.setColor(context.getResources().getColor(R.color.colorPrimary));
        ds.setTypeface(Helpers.getNunitoTypeFaceRegular(context));
    }

    @Override
    public void onClick(View widget) {

    }
}