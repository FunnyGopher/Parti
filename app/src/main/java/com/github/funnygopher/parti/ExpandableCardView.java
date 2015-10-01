package com.github.funnygopher.parti;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ExpandableCardView extends CardView {
    private LinearLayout expandableLayout;
    private boolean expanded;

    public ExpandableCardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ExpandableCardView, 0, 0);
        try {
            expanded = a.getBoolean(R.styleable.ExpandableCardView_card_expanded, false);
        } finally {
            a.recycle();
        }

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setExpanded(!isExpanded());
            }
        });
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expand) {
        expanded = expand;
        if(expand) {
            expand();
        } else {
            collapse();
        }

        invalidate();
        requestLayout();
    }

    private void expand() {
        expandableLayout = (LinearLayout) findViewById(R.id.expandable);
        expandableLayout.setVisibility(View.VISIBLE);
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        expandableLayout.measure(widthSpec, heightSpec);

        ValueAnimator animator = slideAnimator(0, expandableLayout.getMeasuredHeight());
        animator.start();
    }

    private void collapse() {
        expandableLayout = (LinearLayout) findViewById(R.id.expandable);
        int finalHeight = expandableLayout.getHeight();

        ValueAnimator animator = slideAnimator(finalHeight, 0);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                expandableLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private ValueAnimator slideAnimator(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        expandableLayout = (LinearLayout) findViewById(R.id.expandable);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = expandableLayout.getLayoutParams();
                layoutParams.height = value;
                expandableLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
