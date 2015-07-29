package com.anand.mapapp.Classes;

import android.content.Context;
import android.webkit.WebView;

/**
 * Created by user on 21/7/15.
 */
public class GifView extends WebView {
    public GifView(Context context, String path) {
        super(context);
        loadUrl(path);
    }
}
