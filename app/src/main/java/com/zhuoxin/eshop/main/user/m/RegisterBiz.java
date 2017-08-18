package com.zhuoxin.eshop.main.user.m;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.zhuoxin.eshop.R;
import com.zhuoxin.eshop.model.UserResult;
import com.zhuoxin.eshop.network.EShopHttpClient;
import com.zhuoxin.eshop.network.UICallBack;
import com.zhuoxin.eshop.utils.RegexUtils;

import java.io.IOException;

import okhttp3.Call;

/**
 * Register的具体实现类，具体登录业务逻辑
 * Created by Administrator on 2017/8/16.
 */

public class RegisterBiz implements IRegisterBiz {
    @Override
    public void register(Context context, String name, String psd, final OnRegisterListener onRegisterListener) {
        final EShopHttpClient mHttpClient = EShopHttpClient.getInstance();
        if (RegexUtils.verifyUsername(name) != RegexUtils.VERIFY_SUCCESS) {
            onRegisterListener.onFail(context.getString(R.string.username_rules));
            return;
        }
        if (RegexUtils.verifyPassword(psd) != RegexUtils.VERIFY_SUCCESS) {
            onRegisterListener.onSuccess(context.getString(R.string.username_pwd_rule));
            return;
        }
        Call mCall = mHttpClient.register(name, psd);
        mCall.enqueue(new UICallBack() {
            @Override
            public void onUIFailure(Call call, IOException e) {
                onRegisterListener.onFail(e.toString());
            }

            @Override
            public void onUIResponse(Call call, String response) {
                Gson gson = EShopHttpClient.getGson();
                Log.e("register", "onUIResponse: " + response);
                UserResult mResult = gson.fromJson(response, UserResult.class);
                if (mResult.getCode() == 1) {
                    onRegisterListener.onSuccess("注册成功，请前往登录");
                } else {
                    onRegisterListener.onFail(mResult.getMessage());
                }
            }
        });
    }
}
