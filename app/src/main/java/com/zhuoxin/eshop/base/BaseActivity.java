package com.zhuoxin.eshop.base;

import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 封装Activity，封装跳转方法
 * Created by Administrator on 2017/8/15.
 */

public class BaseActivity extends AppCompatActivity {
    public void startActivity(Class<?> mClass) {
        startActivity(new Intent(this, mClass));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
