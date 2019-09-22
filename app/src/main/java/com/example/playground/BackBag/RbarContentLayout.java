package com.example.playground.BackBag;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;

import com.example.playground.R;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class RbarContentLayout extends LinearLayout implements ContentViewCallback {
    private TextView messageView;
    private Button actionView;
    private int maxWidth;
    private int maxInlineActionWidth;
    public RbarContentLayout(Context context) {
        super(context);
    }

    public RbarContentLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.rbar_layout);
        this.maxWidth = a.getDimensionPixelSize(R.styleable.SnackbarLayout_android_maxWidth, -1);
        this.maxInlineActionWidth = a.getDimensionPixelSize(R.styleable.rbar_layout_maxActionInlineWidth, -1);
        a.recycle();
    }

    public RbarContentLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RbarContentLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.messageView = (TextView)this.findViewById(R.id.snackbar_text);
        this.actionView = (Button)this.findViewById(R.id.snackbar_action);
    }

    public TextView getMessageView() {
        return this.messageView;
    }

    public Button getActionView() {
        return this.actionView;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.maxWidth > 0 && this.getMeasuredWidth() > this.maxWidth) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(this.maxWidth, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        int multiLineVPadding = this.getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical_2lines);
        int singleLineVPadding = this.getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical);
        boolean isMultiLine = this.messageView.getLayout().getLineCount() > 1;
        boolean remeasure = false;
        if (isMultiLine && this.maxInlineActionWidth > 0 && this.actionView.getMeasuredWidth() > this.maxInlineActionWidth) {
            if (this.updateViewsWithinLayout(1, multiLineVPadding, multiLineVPadding - singleLineVPadding)) {
                remeasure = true;
            }
        } else {
            int messagePadding = isMultiLine ? multiLineVPadding : singleLineVPadding;
            if (this.updateViewsWithinLayout(0, messagePadding, messagePadding)) {
                remeasure = true;
            }
        }

        if (remeasure) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    private boolean updateViewsWithinLayout(int orientation, int messagePadTop, int messagePadBottom) {
        boolean changed = false;
        if (orientation != this.getOrientation()) {
            this.setOrientation(orientation);
            changed = true;
        }

        if (this.messageView.getPaddingTop() != messagePadTop || this.messageView.getPaddingBottom() != messagePadBottom) {
            updateTopBottomPadding(this.messageView, messagePadTop, messagePadBottom);
            changed = true;
        }

        return changed;
    }

    private static void updateTopBottomPadding(View view, int topPadding, int bottomPadding) {
        if (ViewCompat.isPaddingRelative(view)) {
            ViewCompat.setPaddingRelative(view, ViewCompat.getPaddingStart(view), topPadding, ViewCompat.getPaddingEnd(view), bottomPadding);
        } else {
            view.setPadding(view.getPaddingLeft(), topPadding, view.getPaddingRight(), bottomPadding);
        }

    }

    public void animateContentIn(int delay, int duration) {
        this.messageView.setAlpha(0.0F);
        this.messageView.animate().alpha(1.0F).setDuration((long)duration).setStartDelay((long)delay).start();
        if (this.actionView.getVisibility() == VISIBLE) {
            this.actionView.setAlpha(0.0F);
            this.actionView.animate().alpha(1.0F).setDuration((long)duration).setStartDelay((long)delay).start();
        }

    }

    public void animateContentOut(int delay, int duration) {
        this.messageView.setAlpha(1.0F);
        this.messageView.animate().alpha(0.0F).setDuration((long)duration).setStartDelay((long)delay).start();
        if (this.actionView.getVisibility() == VISIBLE) {
            this.actionView.setAlpha(1.0F);
            this.actionView.animate().alpha(0.0F).setDuration((long)duration).setStartDelay((long)delay).start();
        }

    }
}
