package me.androidbox.flicks.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by steve on 11/28/16.
 */

public class DividerItemDecorator extends RecyclerView.ItemDecoration {
    private int mDivider;

    public DividerItemDecorator(int mDivider) {
        this.mDivider = mDivider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        /* All rows after the top row will get spaces - avoids double spaces between top and bottom */
        if(parent.getChildAdapterPosition(view) != 0 && parent.getChildAdapterPosition(view) != 1) {
            outRect.top = mDivider;
        }

        /**
         * Create a divider between columns 0 and 1
         * Even column 0 - Odd column 1 */
        if(parent.getChildAdapterPosition(view) % 2 != 0) {
            outRect.left = mDivider;
        }

        if(parent.getChildAdapterPosition(view) % 2 == 0) {
            outRect.right = mDivider;
        }

    }
}
