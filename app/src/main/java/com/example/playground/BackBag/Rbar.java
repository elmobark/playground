package com.example.playground.BackBag;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


import com.example.playground.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class Rbar extends RBottomLayout<Rbar> {
    private final AccessibilityManager accessibilityManager;
    private boolean hasAction;
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_SHORT = -1;
    public static final int LENGTH_LONG = 0;
    private static final int[] SNACKBAR_BUTTON_STYLE_ATTR;
    @Nullable
    private BaseCallback<Rbar> callback;
    protected Rbar(@NonNull ViewGroup parent, @NonNull View content, @NonNull com.example.playground.BackBag.ContentViewCallback contentViewCallback) {
        super(parent, content, contentViewCallback);
        this.accessibilityManager = (AccessibilityManager)parent.getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
    }
    public void show() {
        super.show();
    }

    public void dismiss() {
        super.dismiss();
    }

    public boolean isShown() {
        return super.isShown();
    }

    @NonNull
    public static Rbar make(@NonNull View view, @NonNull CharSequence text, int duration) {
        ViewGroup parent = findSuitableParent(view);
        if (parent == null) {
            throw new IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.");
        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            RbarContentLayout content = (RbarContentLayout)inflater.inflate(hasRbarButtonStyleAttr(parent.getContext()) ? R.layout.mtrl_layout_rbar : R.layout.design_layout_rbar, parent, false);
            Rbar snackbar = new Rbar(parent, content, content);
            snackbar.setText(text);
            snackbar.setDuration(duration);
            return snackbar;
        }
    }

    protected static boolean hasRbarButtonStyleAttr(Context context) {
        TypedArray a = context.obtainStyledAttributes(SNACKBAR_BUTTON_STYLE_ATTR);
        int RbarButtonStyleResId = a.getResourceId(0, -1);
        a.recycle();
        return RbarButtonStyleResId != -1;
    }

    @NonNull
    public static Rbar make(@NonNull View view, @StringRes int resId, int duration) {
        return make(view, view.getResources().getText(resId), duration);
    }

    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;

        do {
            if (view instanceof CoordinatorLayout) {
                return (ViewGroup)view;
            }

            if (view instanceof FrameLayout) {
                if (view.getId() == 16908290) {
                    return (ViewGroup)view;
                }

                fallback = (ViewGroup)view;
            }

            if (view != null) {
                ViewParent parent = view.getParent();
                view = parent instanceof View ? (View)parent : null;
            }
        } while(view != null);

        return fallback;
    }

    @NonNull
    public Rbar setText(@NonNull CharSequence message) {
        RbarContentLayout contentLayout = (RbarContentLayout)this.view.getChildAt(0);
        TextView tv = contentLayout.getMessageView();
        tv.setText(message);
        return this;
    }

    @NonNull
    public Rbar setText(@StringRes int resId) {
        return this.setText(this.getContext().getText(resId));
    }

    @NonNull
    public Rbar setAction(@StringRes int resId, View.OnClickListener listener) {
        return this.setAction(this.getContext().getText(resId), listener);
    }

    @NonNull
    public Rbar setAction(CharSequence text, final View.OnClickListener listener) {
        RbarContentLayout contentLayout = (RbarContentLayout)this.view.getChildAt(0);
        TextView tv = contentLayout.getActionView();
        if (!TextUtils.isEmpty(text) && listener != null) {
            this.hasAction = true;
            tv.setVisibility(View.GONE);
            tv.setText(text);
            tv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    listener.onClick(view);
                    Rbar.this.dispatchDismiss(1);
                }
            });
        } else {
            tv.setVisibility(View.GONE);
            tv.setOnClickListener((View.OnClickListener)null);
            this.hasAction = false;
        }

        return this;
    }

    public int getDuration() {
        return this.hasAction && this.accessibilityManager.isTouchExplorationEnabled() ? -2 : super.getDuration();
    }

    @NonNull
    public Rbar setActionTextColor(ColorStateList colors) {
        RbarContentLayout contentLayout = (RbarContentLayout)this.view.getChildAt(0);
        TextView tv = contentLayout.getActionView();
        tv.setTextColor(colors);
        return this;
    }

    @NonNull
    public Rbar setActionTextColor(@ColorInt int color) {
        RbarContentLayout contentLayout = (RbarContentLayout)this.view.getChildAt(0);
        TextView tv = contentLayout.getActionView();
        tv.setTextColor(color);
        return this;
    }

    /** @deprecated */
    @Deprecated
    @NonNull
    public Rbar setCallback(Rbar.Callback callback) {
        if (this.callback != null) {
            this.removeCallback(this.callback);
        }

        if (callback != null) {
            this.addCallback(callback);
        }

        this.callback = callback;
        return this;
    }

    static {
        SNACKBAR_BUTTON_STYLE_ATTR = new int[]{R.attr.snackbarButtonStyle};
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final class rbar_layout extends RBottomLayout.RbarBaseLayout {
        public rbar_layout(Context context) {
            super(context);
        }

        public rbar_layout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int childCount = this.getChildCount();
            int availableWidth = this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();

            for(int i = 0; i < childCount; ++i) {
                View child = this.getChildAt(i);
                if (child.getLayoutParams().width == -1) {
                    child.measure(MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), MeasureSpec.EXACTLY));
                }
            }

        }
    }

    public static class Callback extends RBottomLayout.BaseCallback<Rbar> {
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_TIMEOUT = 2;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;

        public Callback() {
        }

        public void onShown(Rbar sb) {
        }

        public void onDismissed(Rbar transientBottomBar, int event) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @IntRange(
            from = 1L
    )
    public @interface Duration {
    }
}
