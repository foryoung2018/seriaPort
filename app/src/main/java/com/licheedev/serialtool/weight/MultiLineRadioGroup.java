package com.licheedev.serialtool.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MultiLineRadioGroup extends RadioGroup {

    private OnCheckedChangeListener mOnCheckedChangeListener;

    public MultiLineRadioGroup(Context context) {
        super(context);
    }

    public MultiLineRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof LinearLayout) {
            int childCount = ((LinearLayout) child).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = ((LinearLayout) child).getChildAt(i);
                if (view instanceof RadioButton) {
                    final RadioButton button = (RadioButton) view;

                    button.setOnTouchListener(new OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            button.setChecked(true);
                            checkRadioButton(button);
                            if (mOnCheckedChangeListener != null) {
                                mOnCheckedChangeListener.onCheckedChanged(MultiLineRadioGroup.this, button.getId());
                            }
                            return true;
                        }
                    });
                }
            }
        }
        super.addView(child, index, params);
    }

    private void checkRadioButton(RadioButton button) {
        View child;
        int radioCount = getChildCount();
        for (int i = 0; i < radioCount; i++) {
            child = getChildAt(i);
            if (child instanceof RadioButton && child != button) {
                ((RadioButton) child).setChecked(false);
            } else if (child instanceof LinearLayout) {
                int childCount = ((LinearLayout) child).getChildCount();
                for (int j = 0; j < childCount; j++) {
                    View view = ((LinearLayout) child).getChildAt(j);
                    if (view instanceof RadioButton) {
                        final RadioButton rb = (RadioButton) view;
                        if (rb != button) {
                            ((RadioButton) view).setChecked(false);
                        }
                    }
                }
            }
        }
    }

    /**
     * 清除所有的点选效果
     */
    public void clearAllButtonChecked() {
        View child;
        int radioCount = getChildCount();
        for (int i = 0; i < radioCount; i++) {
            child = getChildAt(i);
            int childCount = ((LinearLayout) child).getChildCount();
            for (int j = 0; j < childCount; j++) {
                View view = ((LinearLayout) child).getChildAt(j);
                ((RadioButton) view).setChecked(false);
            }

        }
    }

}
