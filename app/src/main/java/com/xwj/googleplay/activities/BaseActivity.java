package com.xwj.googleplay.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.xwj.googleplay.R;
import com.xwj.googleplay.component.Constants;
import com.xwj.googleplay.views.Toaster;

/**
 * Created by user on 2015/4/14.
 */
public abstract class BaseActivity extends ActionBarActivity {
    public Context mContext;
    private long lastPressBackTime = 0;
    public boolean isKeyCodeBackOpen = false; // 是否是当前页面,如果否，则按返回键先移除当前页上的那个页面。
   // public SystemBarTintManager mTintManager;
   // public RequestQueue mRequestQueue;
   // public ImageLoader mImageLoader;

    /**
     * 初始化布局文件
     *
     * @return
     */
    public abstract int initResource();

    /**
     * 初始化组件
     */
    public abstract void initComponent();

    /**
     * 初始化actionbar
     */
    protected abstract void initActionbar() ;

    /**
     * 初始化数据,请求网络数据
     */
    public abstract void initData();

    /**
     * 添加监听
     */
    public abstract void addListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
       // initStatusBar();
        setContentView(initResource());
        mContext = this;
        initVolley();
        initComponent();
        initActionbar();
        initData();
        addListener();
        Constants.sActivityList.add(this);
    }

    private void initVolley() {
       // mRequestQueue = Volley.newRequestQueue(this);
       // mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_pic_right_in, R.anim.push_pic_right_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isKeyCodeBackOpen) {
            if (System.currentTimeMillis() - lastPressBackTime < 3000) {
                //overridePendingTransition(R.anim.push_pic_right_in,R.anim.push_pic_right_out);
                Constants.sActivityList.remove(this);
                finish();
                System.exit(0);
                return true;
            }
            Toaster.toast(getString(R.string.exit_hint));
            lastPressBackTime = System.currentTimeMillis();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        //mTintManager = new SystemBarTintManager(this);
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 跳转activity并带进入动画
     * @param intent
     */
    protected void startActivityWithAnim(Intent intent){

    }
}
