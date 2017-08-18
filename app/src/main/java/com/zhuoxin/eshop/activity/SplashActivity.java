package com.zhuoxin.eshop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.zhuoxin.eshop.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends Activity {

    @BindView(R.id.splash_tv_skip)
    TextView mSplashTvSkip;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让标题栏隐藏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        /*
         * Timer实现3S之后进入主界面
         */
        //1.获取Timer对象
        mTimer = new Timer(false);
        //2.开启计时器,加入计划
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //3.跳转界面
                jump();
            }
        }, 3000);
    }

    /**
     * 跳转至主界面
     */
    private void jump() {
        mTimer.cancel();
        Intent mIntent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(mIntent);
        finish();
    }

    /**
     * Splash界面销毁时，取消Timer，并将Timer赋值为null，为了gc更好的回收
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer = null;
        System.gc();
    }



    /**
     * Splash界面不可见时，取消Timer
     */
    @Override
    protected void onStop() {
        super.onStop();
        mTimer.cancel();
    }

    @OnClick(R.id.splash_tv_skip)
    public void onViewClicked() {
        jump();
    }
}
