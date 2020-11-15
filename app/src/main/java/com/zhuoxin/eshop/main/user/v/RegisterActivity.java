package com.zhuoxin.eshop.main.user.v;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.zhuoxin.eshop.R;
import com.zhuoxin.eshop.base.BaseActivity;
import com.zhuoxin.eshop.components.ProgressDialogFragment;
import com.zhuoxin.eshop.main.user.p.RegisterPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * RegisterActivity注册页面，展示RegisterActivity的界面，提供数据给Presenter
 * Created by Administrator on 2017/8/16.
 */

public class RegisterActivity extends BaseActivity implements IRegisterActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.et_pwdAgain)
    EditText mEtPwdAgain;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.activity_register)
    RelativeLayout mActivityRegister;
    Unbinder unbinder;
    private RegisterPresenter mPresenter;
    private String name;
    private String psd;
    private ProgressDialogFragment mProgressDialogFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        unbinder = ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        ActionBar mSupportActionBar = getSupportActionBar();
        if (mSupportActionBar != null) {
            mSupportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mEtUsername.addTextChangedListener(mTextWatcher);
        mEtPwd.addTextChangedListener(mTextWatcher);
        mEtPwdAgain.addTextChangedListener(mTextWatcher);
    }

    /**
     * 重写文本输入监听
     */
    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean isClickable = !TextUtils.isEmpty(mEtUsername.getText().toString().trim())
                    && !TextUtils.isEmpty(mEtPwd.getText().toString().trim())
                    && !TextUtils.isEmpty(mEtPwdAgain.getText().toString().trim());
            mBtnRegister.setEnabled(isClickable);
        }
    };

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        showPB();
        if (mPresenter == null) {
            mPresenter = new RegisterPresenter(this);
        }
        name = mEtUsername.getText().toString().trim();
        psd = mEtPwd.getText().toString().trim();
        if (name.equals("") || psd.equals("")) {
            Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!psd.equals(mEtPwdAgain.getText().toString().trim())) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.register();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        if (mPresenter != null){
            mPresenter = null;
        }
        if (mProgressDialogFragment != null){
            mProgressDialogFragment = null;
        }
        super.onDestroy();
    }

    @Override
    public String getUserName() {
        return name;
    }

    @Override
    public String getPsd() {
        return psd;
    }

    @Override
    public void registerFail() {
        mEtPwd.setText("");
        mEtPwdAgain.setText("");
    }

    @Override
    public void registerSuccess() {
        finish();//结束当前界面，跳转回登陆界面
    }

    @Override
    public void showResult(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPB() {
        if (mProgressDialogFragment == null){
            mProgressDialogFragment = new ProgressDialogFragment();
        }
        if (mProgressDialogFragment.isVisible()){
            return;
        }
        mProgressDialogFragment.show(getSupportFragmentManager(),"pb_r");
    }

    @Override
    public void hidePB() {
        if (mProgressDialogFragment != null && mProgressDialogFragment.isVisible()){
            mProgressDialogFragment.dismiss();
        }
    }
}