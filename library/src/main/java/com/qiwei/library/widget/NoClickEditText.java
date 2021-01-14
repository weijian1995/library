package com.qiwei.library.widget;

import android.content.Context;
import android.view.MotionEvent;

/**
 * @author LinWJ
 * create at 2020/11/26 17:16
 */
public class NoClickEditText extends androidx.appcompat.widget.AppCompatEditText {

    public NoClickEditText(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isEnabled() && super.onTouchEvent(event);
    }
}
