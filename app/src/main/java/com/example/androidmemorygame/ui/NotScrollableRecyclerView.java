package com.example.androidmemorygame.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

public class NotScrollableRecyclerView extends RecyclerView {

    public NotScrollableRecyclerView(Context context) {
        super(context);
    }

    public NotScrollableRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotScrollableRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }

}