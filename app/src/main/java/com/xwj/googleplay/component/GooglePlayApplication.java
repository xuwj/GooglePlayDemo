package com.xwj.googleplay.component;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.xwj.googleplay.R;
import com.xwj.googleplay.views.Toaster;

/**
 * 应用程序工具类，主要用于初始化一些操作
 * Created by admin on 2015/5/12.
 */
public class GooglePlayApplication extends Application {
    private Context mContext;
    private static GooglePlayApplication mGooglePlayApplication;
    /**
     * 获取到主线程的handler
     */
    private Handler mMainThreadHandler;
    /**
     * 获取到主线程的looper
     */
    private Looper mMainThreadLooper;
    /**
     * 获取到主线程
     */
    private Thread mMainThead;
    /**
     * 获取到主线程的id
     */
    private int mMainTheadId;

    public synchronized static GooglePlayApplication getApplicationInstance() {  //同步避免多线程安全
        if (mGooglePlayApplication == null)
            mGooglePlayApplication = new GooglePlayApplication();
        return mGooglePlayApplication;
    }

    ;

    public Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    public Thread getMainThread() {
        return mMainThead;
    }

    public int getMainThreadId() {
        return mMainTheadId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        this.mMainThreadHandler = new Handler();
        this.mMainThreadLooper = getMainLooper();
        this.mMainThead = Thread.currentThread();
        //android.os.Process.myUid()   获取到用户id
        //android.os.Process.myPid()获取到进程id
        //android.os.Process.myTid()获取到调用线程的id
        this.mMainTheadId = android.os.Process.myTid();
        Toaster.init(mContext, R.layout.toast, android.R.id.message);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
