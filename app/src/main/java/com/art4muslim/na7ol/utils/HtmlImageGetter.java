package com.art4muslim.na7ol.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;


import com.art4muslim.na7ol.R;

import java.io.InputStream;
import java.net.URL;

public class HtmlImageGetter implements Html.ImageGetter {
    private Context mContext;
    private View mHtmlView;

    public HtmlImageGetter(Context context, TextView view) {
        this.mContext = context;
        this.mHtmlView = view;
    }

    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable drawable = new LevelListDrawable();
//        Drawable empty = mContext.getResources().getDrawable(R.drawable.empty_image);
        drawable.addLevel(0, 0, null);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), 0);
//        drawable.addLevel(0, 0, empty);
//        drawable.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
        new HtmlImageLoad().execute(source, drawable);
        return drawable;
    }

    class HtmlImageLoad extends AsyncTask<Object, Void, Bitmap> {
        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            Log.e("HtmlImageLoad", "doInBackground source " + source);
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.e("HtmlImageLoad", "onPostExecute mDrawable " + mDrawable);
            Log.e("HtmlImageLoad", "onPostExecute bitmap " + bitmap);
            if (bitmap != null) {
                BitmapDrawable drawable = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, drawable);
                mDrawable.setBounds(0, 0, mHtmlView.getWidth(), bitmap.getHeight() + 100);
                mDrawable.setLevel(1);
                /*htmlView.invalidate();
                if (htmlView.getParent() != null) {
                    htmlView.getParent().requestLayout();
                }*/

                if (mHtmlView instanceof TextView) {
                    TextView htmlView = ((TextView) mHtmlView);
                    CharSequence textChar = htmlView.getText();
                    htmlView.setText(textChar);
                } else if (mHtmlView instanceof RadioButton) {
                    RadioButton htmlView = ((RadioButton) mHtmlView);
                    CharSequence textChar = htmlView.getText();
                    htmlView.setText(textChar);
                } else if (mHtmlView instanceof Button) {
                    Button htmlView = ((Button) mHtmlView);
                    CharSequence textChar = htmlView.getText();
                    htmlView.setText(textChar);
                } else if (mHtmlView instanceof CheckBox) {
                    CheckBox htmlView = ((CheckBox) mHtmlView);
                    CharSequence textChar = htmlView.getText();
                    htmlView.setText(textChar);
                }
            }
        }
    }

    /**
     * This is used with databinding in xml layout
     * e.g. bind:htmlFromString="@{item.answer}"
     *
     * @param view
     * @param value
     */
    public static void loadUsingHtml(View view, String value) {
        if (view != null) {
            if (view instanceof TextView) {
                TextView valueView = (TextView) view;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    valueView.setText(Html.fromHtml(value, Html.FROM_HTML_MODE_COMPACT, new HtmlImageGetter(view.getContext(), valueView), null));
                } else {
                    valueView.setText(Html.fromHtml(value, new HtmlImageGetter(view.getContext(), valueView), null));
                }
            } else if (view instanceof RadioButton) {
                RadioButton valueView = (RadioButton) view;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    valueView.setText(Html.fromHtml(value, Html.FROM_HTML_MODE_COMPACT, new HtmlImageGetter(view.getContext(), valueView), null));
                } else {
                    valueView.setText(Html.fromHtml(value, new HtmlImageGetter(view.getContext(), valueView), null));
                }
            }
        }
    }
}