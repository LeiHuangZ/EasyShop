package com.zhuoxin.eshop.main.user.v;

/**
 * LoginActivity接口，定义了LoginActivity的功能
 * Created by Administrator on 2017/8/15.
 */

public interface ILoginActivity {
    String getUserName();
    String getPassWord();

    void showPB();
    void hidePB();

    void loginSuccess();
    void loginFail();

    void showResult(String result);
}
