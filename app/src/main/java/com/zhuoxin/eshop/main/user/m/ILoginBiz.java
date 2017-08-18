package com.zhuoxin.eshop.main.user.m;

/**
 * login的model接口，定义了model的登录方法以及定义了一个监听接口
 * Created by Administrator on 2017/8/15.
 */

public interface ILoginBiz {
    void login(String name, String psd, OnLoginListener mListener);

    interface OnLoginListener{
        void onFail(String error);
        void onSuccess(String result);
    }
}
