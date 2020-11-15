package com.zhuoxin.eshop.network;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 封装CallBack接口，使得网络请求之后可以直接在两个方法中进行UI更新
 * Created by Administrator on 2017/8/14.
 */

public abstract class UICallBack implements Callback {

    //获取Handler对象，并获取主线程的Looper对象
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(@NonNull final Call call, @NonNull final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onUIFailure(call, e);
            }
        });
    }

    @Override
    public void onResponse(@NonNull final Call call, @NonNull final Response response) throws IOException {
        final String result = response.body().string();
        if (result.equals("") && !response.isSuccessful()) {
            throw new IOException("error code:" + response.code());
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onUIResponse(call, result);
                }
            });
        }
    }

    public abstract void onUIFailure(Call call, IOException e);

    public abstract void onUIResponse(Call call, String response);
}
