package com.zhuoxin.eshop.main.user.m;

import android.util.Log;

import com.google.gson.Gson;
import com.zhuoxin.eshop.model.CachePreferences;
import com.zhuoxin.eshop.model.UserResult;
import com.zhuoxin.eshop.network.EShopHttpClient;
import com.zhuoxin.eshop.network.UICallBack;

import java.io.IOException;

import okhttp3.Call;

/**
 * login的presenter，实现登录的具体业务逻辑
 * Created by Administrator on 2017/8/15.
 */

public class LoginBiz implements ILoginBiz {

    @Override
    public void login(String name, String psd, final OnLoginListener mListener) {
        final EShopHttpClient mClient = EShopHttpClient.getInstance();
        Call mCall = mClient.login(name, psd);
        mCall.enqueue(new UICallBack() {
            @Override
            public void onUIFailure(Call call, IOException e) {
                mListener.onFail(e.toString());
            }

            @Override
            public void onUIResponse(Call call, String response) {
                Gson gson = EShopHttpClient.getGson();
                Log.e("login", "onUIResponse: " + response);
                UserResult mResult = gson.fromJson(response, UserResult.class);
                if (mResult.getCode() == 1) {
                    mListener.onSuccess("登录成功");
                    CachePreferences.setUser(mResult.getUser());
                } else if (mResult.getCode() == 2) {
                    mListener.onFail("用户名或密码错误");
                } else {
                    mListener.onFail("未知错误");
                }
            }
        });
    }
}
