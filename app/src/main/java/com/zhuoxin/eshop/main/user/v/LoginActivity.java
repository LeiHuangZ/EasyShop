package com.zhuoxin.eshop.main.user.v;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.zhuoxin.eshop.R;
import com.zhuoxin.eshop.base.BaseActivity;
import com.zhuoxin.eshop.components.ProgressDialogFragment;
import com.zhuoxin.eshop.main.user.p.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 登录界面
 * Created by Administrator on 2017/8/15.
 */

public class LoginActivity extends BaseActivity implements ILoginActivity {
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.psd)
    EditText mPsd;
    @BindView(R.id.login)
    Button mLogin;
    Unbinder unbinder;
    @BindView(R.id.register)
    TextView mRegister;
    private String name;
    private String psd;
    private LoginPresenter mLoginPresenter;
    private ProgressDialogFragment mProgressDialogFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);

        //初始化控件
        initView();
    }

    /**
     * 初始化控件，设置ActionBar，EditText设置addTextChangedListener
     */
    private void initView() {
        setSupportActionBar(mToolBar);
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(R.string.login);
        }

        /*
         * 为EditText设置addTextChangedListener，传入TextWatcher，需要自定义一个TextWatcher，在下面自定义
         */
        mPsd.addTextChangedListener(mTextWatcher);
        mName.addTextChangedListener(mTextWatcher);
    }

    /**
     * 自定义TextWatcher
     */
    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        /*
         * 编辑框文本内容改变后调用
         */
        @Override
        public void afterTextChanged(Editable s) {
            //调用TextUtils.isEmpty方法判断是否为空将两个结果赋值给一个boolean变量
            boolean isClickable = (!TextUtils.isEmpty(mName.getText().toString().trim())
                    && !TextUtils.isEmpty(mPsd.getText().toString().trim()));
            //将boolean变量设置给Button的Enabled属性
            mLogin.setEnabled(isClickable);
        }
    };

    @OnClick({R.id.login, R.id.register})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.login:
                showPB();
                name = mName.getText().toString().trim();
                psd = mPsd.getText().toString().trim();
                if (!name.equals("") && !psd.equals("")) {
                    mLoginPresenter = new LoginPresenter(this);
                    mLoginPresenter.login();
                }
                break;
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }

    }

    @Override
    public String getUserName() {
        return name;
    }

    @Override
    public String getPassWord() {
        return psd;
    }

    @Override//展示登录进度条
    public void showPB() {
        if (mProgressDialogFragment == null) {
            mProgressDialogFragment = new ProgressDialogFragment();
        }
        Log.e("test", "mProgressDialogFragment.isVisible(): " + mProgressDialogFragment.isVisible());
        if (mProgressDialogFragment.isVisible()) {
            return;
        }
        mProgressDialogFragment.show(getSupportFragmentManager(), "pb");
        Log.e("test", "mProgressDialogFragment.isVisible(): " + mProgressDialogFragment.isVisible());
    }

    @Override//展示登录进度条
    public void hidePB() {
        mProgressDialogFragment.dismiss();
    }

    @Override//登录成功调用方法
    public void loginSuccess() {
        finish();
        hidePB();
    }

    @Override//登录失败调用方法
    public void loginFail() {
        mPsd.setText("");
        hidePB();
    }

    @Override
    public void showResult(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    /**
     * 界面销毁时，进行对象置为NULL，让GC更好回收无用的垃圾
     */
    @Override
    protected void onDestroy() {
        if (mLoginPresenter != null) {
            mLoginPresenter = null;
        }
        if (mProgressDialogFragment != null) {
            mProgressDialogFragment = null;
        }
        unbinder.unbind();
        super.onDestroy();
    }
}
