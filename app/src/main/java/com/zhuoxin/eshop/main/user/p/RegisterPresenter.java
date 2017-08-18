package com.zhuoxin.eshop.main.user.p;

import com.zhuoxin.eshop.main.user.m.IRegisterBiz;
import com.zhuoxin.eshop.main.user.m.RegisterBiz;
import com.zhuoxin.eshop.main.user.v.RegisterActivity;

/**
 * Register的控制台，负责RegisterBiz和RegisterActivity的交互
 * Created by Administrator on 2017/8/16.
 */

public class RegisterPresenter implements IRegisterPresenter {
    private RegisterActivity mActivity;
    private RegisterBiz mRegisterBiz;

    public RegisterPresenter(RegisterActivity activity) {
        mActivity = activity;
        mRegisterBiz = new RegisterBiz();
    }

    @Override
    public void register() {
        mRegisterBiz.register(mActivity, mActivity.getUserName(), mActivity.getPsd(), new IRegisterBiz.OnRegisterListener() {
            @Override
            public void onFail(String error) {
                mActivity.showResult(error);
                mActivity.registerFail();
                mActivity.hidePB();
            }

            @Override
            public void onSuccess(String str) {
                mActivity.showResult(str);
                mActivity.registerSuccess();
                mActivity.hidePB();
            }
        });
    }
}
