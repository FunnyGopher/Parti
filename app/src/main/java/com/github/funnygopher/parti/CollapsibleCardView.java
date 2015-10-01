package com.github.funnygopher.parti;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class CollapsibleCardView extends CardView {
    private RelativeLayout expandableLayout;

    public CollapsibleCardView(Context context, CardView cardView) {
        super(context);
        expandableLayout = (RelativeLayout) cardView.findViewById(R.id.expandable);
        expandableLayout.setVisibility(View.GONE);

        RelativeLayout headerLayout = (RelativeLayout) cardView.findViewById(R.id.header);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableLayout.getVisibility() == View.GONE) {
                    expand();
                } else {
                    collapse();
                }
            }
        });
    }

    private void expand() {
        expandableLayout.setVisibility(View.VISIBLE);
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        expandableLayout.measure(widthSpec, heightSpec);

        ValueAnimator animator = slideAnimator(0, expandableLayout.getMeasuredHeight());
        animator.start();
    }

    private void collapse() {
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
