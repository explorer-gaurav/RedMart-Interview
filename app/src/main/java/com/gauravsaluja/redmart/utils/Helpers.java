package com.gauravsaluja.redmart.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.gauravsaluja.redmart.customviews.CustomSpannable;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Helper class
 */

public class Helpers {

    // get typeface nunito bold
    public static Typeface getNunitoTypeFaceBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/nunito_bold.ttf");
    }

    // get typeface nunito regular
    public static Typeface getNunitoTypeFaceRegular(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/nunito_regular.ttf");
    }

    // get typeface nunito light
    public static Typeface getNunitoTypeFaceLight(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/nunito_light.ttf");
    }

    // remove global layout listener
    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    // allow the text view to be resizable by checking if current line count is greater than MAX_LINES
    // add spannable string and click listener on the textview
    public static void makeTextViewResizable(final Context context, final TextView tv, final int maxLine,
                                             final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }

        final ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                if (tv != null) {
                    Layout l = tv.getLayout();
                    if (l != null) {
                        int lines = l.getLineCount();
                        if (lines > Constants.MAX_LINES) {
                            if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                                int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                                String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                                tv.setText(text);
                                tv.setMovementMethod(LinkMovementMethod.getInstance());
                                tv.setText(addClickablePartTextViewResizable(context, Html.fromHtml(tv.getText().toString()),
                                        tv, expandText, viewMore), TextView.BufferType.SPANNABLE);
                            } else {
                                int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                                String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                                tv.setText(text);
                                tv.setMovementMethod(LinkMovementMethod.getInstance());
                                tv.setText(addClickablePartTextViewResizable(context, Html.fromHtml(tv.getText().toString()),
                                        tv, expandText, viewMore), TextView.BufferType.SPANNABLE);
                            }
                        }
                    }

                    removeOnGlobalLayoutListener(tv, this);
                }
            }
        });
    }

    // setup spannable string and add click listener on textview
    private static SpannableStringBuilder addClickablePartTextViewResizable(final Context context, final Spanned strSpanned, final TextView tv,
                                                                            final String spannableText, final boolean viewMore) {

        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (!TextUtils.isEmpty(spannableText) && str.contains(spannableText)) {
            ssb.setSpan(new CustomSpannable(context, false) {
                @Override
                public void onClick(View widget) {

                }
            }, str.indexOf(spannableText), str.indexOf(spannableText) + spannableText.length(), 0);

            ssb.setSpan(new RelativeSizeSpan(1.1f), str.indexOf(spannableText), str.indexOf(spannableText) + spannableText.length(), 0);
        }

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDescription(context, tv, viewMore);
            }
        });

        return ssb;
    }

    // click logic of textview
    private static void onClickDescription(Context context, TextView tv, boolean viewMore) {
        tv.setLayoutParams(tv.getLayoutParams());
        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
        tv.invalidate();

        if (viewMore) {
            makeTextViewResizable(context, tv, Integer.MAX_VALUE, "", false);
        } else {
            makeTextViewResizable(context, tv, Constants.MAX_LINES, Constants.READ_MORE_STRING, true);
        }
    }
}