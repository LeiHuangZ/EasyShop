package com.zhuoxin.eshop.main.user.v;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.pkmmte.view.CircularImageView;
import com.zhuoxin.eshop.R;
import com.zhuoxin.eshop.base.BaseActivity;
import com.zhuoxin.eshop.model.CachePreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人信息界面
 * Created by Administrator on 2017/8/16.
 */

public class UserActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.me_civ)
    CircularImageView mMeCiv;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.ll_item_name)
    LinearLayout mLlItemName;
    @BindView(R.id.tv_nickName)
    TextView mTvNickName;
    @BindView(R.id.ll_item_nickName)
    LinearLayout mLlItemNickName;
    @BindView(R.id.tv_hxId)
    TextView mTvHxId;
    @BindView(R.id.ll_item_hxId)
    LinearLayout mLlItemHxId;
    @BindView(R.id.user_btn)
    Button mUserBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(mToolBar);
        ActionBar mSupportActionBar = getSupportActionBar();
        if (mSupportActionBar != null){
            mSupportActionBar.setDisplayHomeAsUpEnabled(true);
            mSupportActionBar.setTitle(R.string.me_person_info);
        }
    }


    @OnClick({R.id.ll_item_name, R.id.ll_item_nickName, R.id.ll_item_hxId, R.id.user_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_item_name:
                break;
            case R.id.ll_item_nickName:
                break;
            case R.id.ll_item_hxId:
                break;
            case R.id.user_btn:
                CachePreferences.clearAllData();
                finish();
                break;
        }
    }
}
