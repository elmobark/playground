package com.example.playground.BackBag;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;

import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.example.playground.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.internal.ThemeEnforcement;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public abstract class RBottomLayout<B extends  RBottomLayout<B>> {
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_SHORT = -1;
    public static final int LENGTH_LONG = 0;
    static final int ANIMATION_DURATION = 250;
    static final int ANIMATION_FADE_DURATION = 180;
    static final Handler handler;
    static final int MSG_SHOW = 0;
    static final int MSG_DISMISS = 1;
    private static final boolean USE_OFFSET_API;
    private static final int[] SNACKBAR_STYLE_ATTR;
    private final ViewGroup targetParent;
    private final Context context;
    protected final RBottomLayout.RbarBaseLayout view;
    private final com.example.playground.BackBag.ContentViewCallback contentViewCallback;
    private int duration;
    private List<RBottomLayout.BaseCallback<B>> callbacks;
    private RBottomLayout.Behavior behavior;
    private final AccessibilityManager accessibilityManager;
    final RbarManger.Callback managerCallback = new RbarManger.Callback() {
        public void show() {
            RBottomLayout.handler.sendMessage(RBottomLayout.handler.obtainMessage(0, RBottomLayout.this));
        }

        public void dismiss(int event) {
            RBottomLayout.handler.sendMessage(RBottomLayout.handler.obtainMessage(1, event, 0, RBottomLayout.this));
        }
    };

    protected RBottomLayout(@NonNull ViewGroup parent, @NonNull View content, @NonNull com.example.playground.BackBag.ContentViewCallback contentViewCallback) {
        this.targetParent = parent;
        this.contentViewCallback = contentViewCallback;
        this.context = parent.getContext();
        ThemeEnforcement.checkAppCompatTheme(this.context);
        LayoutInflater inflater = LayoutInflater.from(this.context);
        this.view = (RbarBaseLayout)inflater.inflate(this.getSnackbarBaseLayoutResId(), this.targetParent, false);
        this.view.addView(content);
        ViewCompat.setAccessibilityLiveRegion(this.view, ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE);
        ViewCompat.setImportantForAccessibility(this.view, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);
        ViewCompat.setFitsSystemWindows(this.view, true);
        ViewCompat.setOnApplyWindowInsetsListener(this.view, new OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), insets.getSystemWindowInsetBottom());
                return insets;
            }
        });
        ViewCompat.setAccessibilityDelegate(this.view, new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.addAction(1048576);
                info.setDismissable(true);
            }

            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                if (action == 1048576) {
                    RBottomLayout.this.dismiss();
                    return true;
                } else {
                    return super.performAccessibilityAction(host, action, args);
                }
            }
        });
        this.accessibilityManager = (AccessibilityManager)this.context.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    @LayoutRes
    protected int getSnackbarBaseLayoutResId() {
        return this.hasSnackbarStyleAttr() ? R.layout.mtrl_layout_rbar : R.layout.design_layout_rbar;
    }

    protected boolean hasSnackbarStyleAttr() {
        TypedArray a = this.context.obtainStyledAttributes(SNACKBAR_STYLE_ATTR);
        int snackbarStyleResId = a.getResourceId(0, -1);
        a.recycle();
        return snackbarStyleResId != -1;
    }

    @NonNull
    public void setDuration(int duration) {
        this.duration = duration;

    }

    public int getDuration() {
        return this.duration;
    }

    public void setBehavior(RBottomLayout.Behavior behavior) {
        this.behavior = behavior;

    }

    public RBottomLayout.Behavior getBehavior() {
        return this.behavior;
    }

    @NonNull
    public Context getContext() {
        return this.context;
    }

    @NonNull
    public View getView() {
        return this.view;
    }

    public void show() {
        RbarManger.getInstance().show(this.getDuration(), this.managerCallback);
    }

    public void dismiss() {
        this.dispatchDismiss(3);
    }

    protected void dispatchDismiss(int event) {
        RbarManger.getInstance().dismiss(this.managerCallback, event);
    }

    @NonNull
    public void addCallback(@NonNull RBottomLayout.BaseCallback<B> callback) {
        if (this.callbacks == null) {
            this.callbacks = new ArrayList();
        }

        this.callbacks.add(callback);

    }

    @NonNull
    public void removeCallback(@NonNull RBottomLayout.BaseCallback<B> callback) {
        this.callbacks.remove(callback);
    }

    public boolean isShown() {
        return RbarManger.getInstance().isCurrent(this.managerCallback);
    }

    public boolean isShownOrQueued() {
        return RbarManger.getInstance().isCurrentOrNext(this.managerCallback);
    }

    protected SwipeDismissBehavior<? extends View> getNewBehavior() {
        return new RBottomLayout.Behavior();
    }

    final void showView() {
        if (this.view.getParent() == null) {
            ViewGroup.LayoutParams lp = this.view.getLayoutParams();
            if (lp instanceof androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) {
                androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams clp = (androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams)lp;
                SwipeDismissBehavior<? extends View> behavior = this.behavior == null ? this.getNewBehavior() : this.behavior;
                if (behavior instanceof RBottomLayout.Behavior) {
                    ((RBottomLayout.Behavior)behavior).setRBottomLayout(this);
                }

                ((SwipeDismissBehavior)behavior).setListener(new SwipeDismissBehavior.OnDismissListener() {
                    public void onDismiss(View view) {
                        view.setVisibility(View.GONE);
                        RBottomLayout.this.dispatchDismiss(0);
                    }

                    public void onDragStateChanged(int state) {
                        switch(state) {
                            case 0:
                                RbarManger.getInstance().restoreTimeoutIfPaused(RBottomLayout.this.managerCallback);
                                break;
                            case 1:
                            case 2:
                                RbarManger.getInstance().pauseTimeout(RBottomLayout.this.managerCallback);
                        }

                    }
                });
                clp.setBehavior(behavior);
                clp.insetEdge = 80;
            }

            this.targetParent.addView(this.view);
        }

        this.view.setOnAttachStateChangeListener(new RBottomLayout.OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View v) {
            }

            public void onViewDetachedFromWindow(View v) {
                if (RBottomLayout.this.isShownOrQueued()) {
                    RBottomLayout.handler.post(new Runnable() {
                        public void run() {
                            RBottomLayout.this.onViewHidden(3);
                        }
                    });
                }

            }
        });
        if (ViewCompat.isLaidOut(this.view)) {
            if (this.shouldAnimate()) {
                this.animateViewIn();
            } else {
                this.onViewShown();
            }
        } else {
            this.view.setOnLayoutChangeListener(new RBottomLayout.OnLayoutChangeListener() {
                public void onLayoutChange(View view, int left, int top, int right, int bottom) {
                    RBottomLayout.this.view.setOnLayoutChangeListener((RBottomLayout.OnLayoutChangeListener)null);
                    if (RBottomLayout.this.shouldAnimate()) {
                        RBottomLayout.this.animateViewIn();
                    } else {
                        RBottomLayout.this.onViewShown();
                    }

                }
            });
        }

    }

    void animateViewIn() {
        final int translationYBottom = this.getTranslationYBottom();
        if (USE_OFFSET_API) {
            ViewCompat.offsetTopAndBottom(this.view, translationYBottom);
        } else {
            this.view.setTranslationY((float)translationYBottom);
        }

        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(new int[]{translationYBottom, 0});
        animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        animator.setDuration(250L);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                RBottomLayout.this.contentViewCallback.animateContentIn(70, 180);
            }

            public void onAnimationEnd(Animator animator) {
                RBottomLayout.this.onViewShown();
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private int previousAnimatedIntValue = translationYBottom;

            public void onAnimationUpdate(ValueAnimator animator) {
                int currentAnimatedIntValue = (Integer)animator.getAnimatedValue();
                if (RBottomLayout.USE_OFFSET_API) {
                    ViewCompat.offsetTopAndBottom(RBottomLayout.this.view, currentAnimatedIntValue - this.previousAnimatedIntValue);
                } else {
                    RBottomLayout.this.view.setTranslationY((float)currentAnimatedIntValue);
                }

                this.previousAnimatedIntValue = currentAnimatedIntValue;
            }
        });
        animator.start();
    }

    private void animateViewOut(final int event) {
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(new int[]{0, this.getTranslationYBottom()});
        animator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        animator.setDuration(250L);
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                RBottomLayout.this.contentViewCallback.animateContentOut(0, 180);
            }

            public void onAnimationEnd(Animator animator) {
                RBottomLayout.this.onViewHidden(event);
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private int previousAnimatedIntValue = 0;

            public void onAnimationUpdate(ValueAnimator animator) {
                int currentAnimatedIntValue = (Integer)animator.getAnimatedValue();
                if (RBottomLayout.USE_OFFSET_API) {
                    ViewCompat.offsetTopAndBottom(RBottomLayout.this.view, currentAnimatedIntValue - this.previousAnimatedIntValue);
                } else {
                    RBottomLayout.this.view.setTranslationY((float)currentAnimatedIntValue);
                }

                this.previousAnimatedIntValue = currentAnimatedIntValue;
            }
        });
        animator.start();
    }

    private int getTranslationYBottom() {
        int translationY = this.view.getHeight();
        ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            translationY += ((ViewGroup.MarginLayoutParams)layoutParams).bottomMargin;
        }

        return translationY;
    }

    final void hideView(int event) {
        if (this.shouldAnimate() && this.view.getVisibility() == View.VISIBLE) {
            this.animateViewOut(event);
        } else {
            this.onViewHidden(event);
        }

    }

    void onViewShown() {
        RbarManger.getInstance().onShown(this.managerCallback);
        if (this.callbacks != null) {
            int callbackCount = this.callbacks.size();

            for(int i = callbackCount - 1; i >= 0; --i) {
                ((RBottomLayout.BaseCallback)this.callbacks.get(i)).onShown(this);
            }
        }

    }

    void onViewHidden(int event) {
        RbarManger.getInstance().onDismissed(this.managerCallback);
        if (this.callbacks != null) {
            int callbackCount = this.callbacks.size();

            for(int i = callbackCount - 1; i >= 0; --i) {
                ((RBottomLayout.BaseCallback)this.callbacks.get(i)).onDismissed(this, event);
            }
        }

        ViewParent parent = this.view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup)parent).removeView(this.view);
        }

    }

    boolean shouldAnimate() {
       // int feedbackFlags = true;
        List<AccessibilityServiceInfo> serviceList = this.accessibilityManager.getEnabledAccessibilityServiceList(1);
        return serviceList != null && serviceList.isEmpty();
    }

    static {
        USE_OFFSET_API = Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT <= 19;
        SNACKBAR_STYLE_ATTR = new int[]{R.attr.snackbarStyle};
        handler = new Handler(Looper.getMainLooper(), new android.os.Handler.Callback() {
            public boolean handleMessage(Message message) {
                switch(message.what) {
                    case 0:
                        ((RBottomLayout)message.obj).showView();
                        return true;
                    case 1:
                        ((RBottomLayout)message.obj).hideView(message.arg1);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class BehaviorDelegate {
        private RbarManger.Callback managerCallback;

        public BehaviorDelegate(SwipeDismissBehavior<?> behavior) {
            behavior.setStartAlphaSwipeDistance(0.1F);
            behavior.setEndAlphaSwipeDistance(0.6F);
            behavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);
        }

        public void setRBottomLayout(RBottomLayout<?> RBottomLayout) {
            this.managerCallback = RBottomLayout.managerCallback;
        }

        public boolean canSwipeDismissView(View child) {
            return child instanceof RBottomLayout.RbarBaseLayout;
        }

        public void onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent event) {
            switch(event.getActionMasked()) {
                case 0:
                    if (parent.isPointInChildBounds(child, (int)event.getX(), (int)event.getY())) {
                        RbarManger.getInstance().pauseTimeout(this.managerCallback);
                    }
                    break;
                case 1:
                case 3:
                    RbarManger.getInstance().restoreTimeoutIfPaused(this.managerCallback);
                case 2:
            }

        }
    }

    public static class Behavior extends SwipeDismissBehavior<View> {
        private final RBottomLayout.BehaviorDelegate delegate = new RBottomLayout.BehaviorDelegate(this);

        public Behavior() {
        }

        private void setRBottomLayout(RBottomLayout<?> RBottomLayout) {
            this.delegate.setRBottomLayout(RBottomLayout);
        }

        public boolean canSwipeDismissView(View child) {
            return this.delegate.canSwipeDismissView(child);
        }

        public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent event) {
            this.delegate.onInterceptTouchEvent(parent, child, event);
            return super.onInterceptTouchEvent(parent, child, event);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected static class RbarBaseLayout extends FrameLayout {
        private final AccessibilityManager accessibilityManager;
        private final AccessibilityManagerCompat.TouchExplorationStateChangeListener touchExplorationStateChangeListener;
        private RBottomLayout.OnLayoutChangeListener onLayoutChangeListener;
        private RBottomLayout.OnAttachStateChangeListener onAttachStateChangeListener;

        protected RbarBaseLayout(Context context) {
            this(context, (AttributeSet)null);
        }

        protected RbarBaseLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.rbar_layout);
            if (a.hasValue(R.styleable.rbar_layout_elevation)) {
                ViewCompat.setElevation(this, (float)a.getDimensionPixelSize(R.styleable.rbar_layout_elevation, 0));
            }

            a.recycle();
            this.accessibilityManager = (AccessibilityManager)context.getSystemService(Context.ACCESSIBILITY_SERVICE);
            this.touchExplorationStateChangeListener = new AccessibilityManagerCompat.TouchExplorationStateChangeListener() {
                public void onTouchExplorationStateChanged(boolean enabled) {
                    RBottomLayout.RbarBaseLayout.this.setClickableOrFocusableBasedOnAccessibility(enabled);
                }
            };
            AccessibilityManagerCompat.addTouchExplorationStateChangeListener(this.accessibilityManager, this.touchExplorationStateChangeListener);
            this.setClickableOrFocusableBasedOnAccessibility(this.accessibilityManager.isTouchExplorationEnabled());
        }

        private void setClickableOrFocusableBasedOnAccessibility(boolean touchExplorationEnabled) {
            this.setClickable(!touchExplorationEnabled);
            this.setFocusable(touchExplorationEnabled);
        }

        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            if (this.onLayoutChangeListener != null) {
                this.onLayoutChangeListener.onLayoutChange(this, l, t, r, b);
            }

        }

        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            if (this.onAttachStateChangeListener != null) {
                this.onAttachStateChangeListener.onViewAttachedToWindow(this);
            }

            ViewCompat.requestApplyInsets(this);
        }

        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (this.onAttachStateChangeListener != null) {
                this.onAttachStateChangeListener.onViewDetachedFromWindow(this);
            }

            AccessibilityManagerCompat.removeTouchExplorationStateChangeListener(this.accessibilityManager, this.touchExplorationStateChangeListener);
        }

        void setOnLayoutChangeListener(RBottomLayout.OnLayoutChangeListener onLayoutChangeListener) {
            this.onLayoutChangeListener = onLayoutChangeListener;
        }

        void setOnAttachStateChangeListener(RBottomLayout.OnAttachStateChangeListener listener) {
            this.onAttachStateChangeListener = listener;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected interface OnAttachStateChangeListener {
        void onViewAttachedToWindow(View var1);

        void onViewDetachedFromWindow(View var1);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected interface OnLayoutChangeListener {
        void onLayoutChange(View var1, int var2, int var3, int var4, int var5);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @IntRange(
            from = 1L
    )
    public @interface Duration {
    }

    /** @deprecated */
    @Deprecated
    public interface ContentViewCallback extends com.google.android.material.snackbar.ContentViewCallback {
    }

    public abstract static class BaseCallback<B> {
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_TIMEOUT = 2;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;

        public BaseCallback() {
        }

        public void onDismissed(B transientBottomBar, int event) {
        }

        public void onShown(B transientBottomBar) {
        }

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public @interface DismissEvent {
        }
    }
}
