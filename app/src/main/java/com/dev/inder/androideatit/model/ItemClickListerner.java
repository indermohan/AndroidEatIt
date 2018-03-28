package com.dev.inder.androideatit.model;

import android.view.View;

/**
 * Created by unoiaAndroid on 3/22/2018.
 */

public interface ItemClickListerner {
    void Onclick(View view, int position, boolean isLongClick);
}
