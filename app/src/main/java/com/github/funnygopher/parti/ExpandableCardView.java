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

import junit.framework.Assert;

public class ExpandableCardView extends CardView {

    private int cardBodyId;
    private ViewGroup cardBody;
    private boolean expanded;

    public ExpandableCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        // Retrieves the attributes and
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ExpandableCardView, 0, 0);
        try {
            cardBodyId = a.getResourceId(R.styleable.ExpandableCardView_card_body, -1);
            expanded = a.getBoolean(R.styleable.ExpandableCardView_card_expanded, false);
        } finally {
            if(a != null) {
                a.recycle();
            }
        }
    }

    private void init() {
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setExpanded(!isExpanded());
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if(cardBodyId == -1) {
            return;
        }

        cardBody = (ViewGroup) findViewById(cardBodyId);
        if(expanded) {
            cardBody.setVisibility(View.VISIBLE);
        } else {
            cardBody.setVisibility(View.GONE);
        }
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expand) {
        expanded = expand;

        if(cardBodyId == -1) {
            return;
        }

        if(expand) {
            expand();
        } else {
            collapse();
        }

        invalidate();
        requestLayout();
    }

    private void expand() {
        cardBody.setVisibility(View.VISIBLE);
        final int widthSpec = ViewGroup.MeasureSpec.makeMeasureSpec(1080, MeasureSpec.EXACTLY);
        final int heightSpec = ViewGroup.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        cardBody.measure(widthSpec, heightSpec);

        int height = cardBody.getMeasuredHeight();
        ValueAnimator animator = slideAnimator(0, cardBody.getMeasuredHeight());
        animator.start();
    }

    private void collapse() {
        int currentHeight = cardBody.getHeight();

        ValueAnimator animator = slideAnimator(currentHeight, 0);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                cardBody.setVisibility(View.GONE);
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
                ViewGroup.LayoutParams layoutParams = cardBody.getLayoutParams();
                layoutParams.height = value;
                cardBody.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
