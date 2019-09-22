package com.example.playground.BackBag;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;



import java.lang.ref.WeakReference;

public class RbarManger {
    static final int MSG_TIMEOUT = 0;
    private static final int SHORT_DURATION_MS = 1500;
    private static final int LONG_DURATION_MS = 2750;
    private static RbarManger RbarManger;
    private final Object lock = new Object();
    private final Handler handler = new Handler(Looper.getMainLooper(), new android.os.Handler.Callback() {
        public boolean handleMessage(Message message) {
            switch(message.what) {
                case 0:
                    RbarManger.this.handleTimeout((RbarManger.SnackbarRecord)message.obj);
                    return true;
                default:
                    return false;
            }
        }
    });
    private RbarManger.SnackbarRecord currentSnackbar;
    private RbarManger.SnackbarRecord nextSnackbar;

    static RbarManger getInstance() {
        if (RbarManger == null) {
            RbarManger = new RbarManger();
        }

        return RbarManger;
    }

    private RbarManger() {
    }

    public void show(int duration, RbarManger.Callback callback) {
        synchronized(this.lock) {
            if (this.isCurrentSnackbarLocked(callback)) {
                this.currentSnackbar.duration = duration;
                this.handler.removeCallbacksAndMessages(this.currentSnackbar);
                this.scheduleTimeoutLocked(this.currentSnackbar);
            } else {
                if (this.isNextSnackbarLocked(callback)) {
                    this.nextSnackbar.duration = duration;
                } else {
                    this.nextSnackbar = new RbarManger.SnackbarRecord(duration, callback);
                }

                if (this.currentSnackbar == null || !this.cancelSnackbarLocked(this.currentSnackbar, 4)) {
                    this.currentSnackbar = null;
                    this.showNextSnackbarLocked();
                }
            }
        }
    }

    public void dismiss(RbarManger.Callback callback, int event) {
        synchronized(this.lock) {
            if (this.isCurrentSnackbarLocked(callback)) {
                this.cancelSnackbarLocked(this.currentSnackbar, event);
            } else if (this.isNextSnackbarLocked(callback)) {
                this.cancelSnackbarLocked(this.nextSnackbar, event);
            }

        }
    }

    public void onDismissed(RbarManger.Callback callback) {
        synchronized(this.lock) {
            if (this.isCurrentSnackbarLocked(callback)) {
                this.currentSnackbar = null;
                if (this.nextSnackbar != null) {
                    this.showNextSnackbarLocked();
                }
            }

        }
    }

    public void onShown(RbarManger.Callback callback) {
        synchronized(this.lock) {
            if (this.isCurrentSnackbarLocked(callback)) {
                this.scheduleTimeoutLocked(this.currentSnackbar);
            }

        }
    }

    public void pauseTimeout(RbarManger.Callback callback) {
        synchronized(this.lock) {
            if (this.isCurrentSnackbarLocked(callback) && !this.currentSnackbar.paused) {
                this.currentSnackbar.paused = true;
                this.handler.removeCallbacksAndMessages(this.currentSnackbar);
            }

        }
    }

    public void restoreTimeoutIfPaused(RbarManger.Callback callback) {
        synchronized(this.lock) {
            if (this.isCurrentSnackbarLocked(callback) && this.currentSnackbar.paused) {
                this.currentSnackbar.paused = false;
                this.scheduleTimeoutLocked(this.currentSnackbar);
            }

        }
    }

    public boolean isCurrent(RbarManger.Callback callback) {
        synchronized(this.lock) {
            return this.isCurrentSnackbarLocked(callback);
        }
    }

    public boolean isCurrentOrNext(RbarManger.Callback callback) {
        synchronized(this.lock) {
            return this.isCurrentSnackbarLocked(callback) || this.isNextSnackbarLocked(callback);
        }
    }

    private void showNextSnackbarLocked() {
        if (this.nextSnackbar != null) {
            this.currentSnackbar = this.nextSnackbar;
            this.nextSnackbar = null;
            RbarManger.Callback callback = (RbarManger.Callback)this.currentSnackbar.callback.get();
            if (callback != null) {
                callback.show();
            } else {
                this.currentSnackbar = null;
            }
        }

    }

    private boolean cancelSnackbarLocked(RbarManger.SnackbarRecord record, int event) {
        RbarManger.Callback callback = (RbarManger.Callback)record.callback.get();
        if (callback != null) {
            this.handler.removeCallbacksAndMessages(record);
            callback.dismiss(event);
            return true;
        } else {
            return false;
        }
    }

    private boolean isCurrentSnackbarLocked(RbarManger.Callback callback) {
        return this.currentSnackbar != null && this.currentSnackbar.isSnackbar(callback);
    }

    private boolean isNextSnackbarLocked(RbarManger.Callback callback) {
        return this.nextSnackbar != null && this.nextSnackbar.isSnackbar(callback);
    }

    private void scheduleTimeoutLocked(RbarManger.SnackbarRecord r) {
        if (r.duration != -2) {
            int durationMs = 2750;
            if (r.duration > 0) {
                durationMs = r.duration;
            } else if (r.duration == -1) {
                durationMs = 1500;
            }

            this.handler.removeCallbacksAndMessages(r);
            this.handler.sendMessageDelayed(Message.obtain(this.handler, 0, r), (long)durationMs);
        }
    }

    void handleTimeout(RbarManger.SnackbarRecord record) {
        synchronized(this.lock) {
            if (this.currentSnackbar == record || this.nextSnackbar == record) {
                this.cancelSnackbarLocked(record, 2);
            }

        }
    }

    private static class SnackbarRecord {
        final WeakReference<RbarManger.Callback> callback;
        int duration;
        boolean paused;

        SnackbarRecord(int duration, RbarManger.Callback callback) {
            this.callback = new WeakReference(callback);
            this.duration = duration;
        }

        boolean isSnackbar(RbarManger.Callback callback) {
            return callback != null && this.callback.get() == callback;
        }
    }

    interface Callback {
        void show();

        void dismiss(int var1);
    }
}

