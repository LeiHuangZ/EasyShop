package com.zhuoxin.eshop.main.user.m;

import android.content.Context;

/**
 * Register的具体业务逻辑实现类的接口，注册方法以及注册监听
 * Created by Administrator on 2017/8/16.
 */

public interface IRegisterBiz {
    void register(Context context, String name, String psd, OnRegisterListener onRegisterListener);

        interface OnRegisterListener{
            void onFail(String error);
            void onSuccess(String str);
        };
}
