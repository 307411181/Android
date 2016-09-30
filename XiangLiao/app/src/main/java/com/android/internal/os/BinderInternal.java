//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.android.internal.os;

import android.os.IBinder;
import android.os.SystemClock;
import android.util.EventLog;
import java.lang.ref.WeakReference;

public class BinderInternal {
    static WeakReference<GcWatcher> mGcWatcher = new WeakReference(new GcWatcher());
    static long mLastGcTime;

    public BinderInternal() {
    }

    static void forceBinderGc() {
        forceGc("Binder");
    }

    public static void forceGc(String var0) {
        EventLog.writeEvent(2741, var0);
        Runtime.getRuntime().gc();
    }

    public static final native IBinder getContextObject();

    public static long getLastGcTime() {
        return mLastGcTime;
    }

    static final native void handleGc();

    public static final native void joinThreadPool();

    static final class GcWatcher {
        GcWatcher() {
        }

        protected void finalize() throws Throwable {
            BinderInternal.handleGc();
            BinderInternal.mLastGcTime = SystemClock.uptimeMillis();
            BinderInternal.mGcWatcher = new WeakReference(new GcWatcher());
        }
    }
}
