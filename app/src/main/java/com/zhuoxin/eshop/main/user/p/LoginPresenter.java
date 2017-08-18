package com.zhuoxin.eshop.main.user.p;

import com.zhuoxin.eshop.main.user.m.ILoginBiz;
import com.zhuoxin.eshop.main.user.m.LoginBiz;
import com.zhuoxin.eshop.main.user.v.LoginActivity;

/**
 * login的presenter，实现了接口中定义的登录方法
 * Created by Administrator on 2017/8/15.
 */

public class LoginPresenter implements ILoginPresenter {
    private LoginBiz mLoginBiz;
    private LoginActivity mLoginActivity;

    public LoginPresenter(LoginActivity loginActivity) {
        mLoginActivity = loginActivity;
        mLoginBiz = new LoginBiz();
    }

    @Override
    public void login() {
        String mUserName = mLoginActivity.getUserName();
        String mPassWord = mLoginActivity.getPassWord();
        mLoginBiz.login(mUserName, mPassWord, new ILoginBiz.OnLoginListener() {
            @Override
            public void onFail(String error) {
                mLoginActivity.showResult(error);
                mLoginActivity.loginFail();
            }

            @Override
            public void onSuccess(String result) {
                mLoginActivity.showResult(result);
                mLoginActivity.loginSuccess();
            }
        });
    }
}
