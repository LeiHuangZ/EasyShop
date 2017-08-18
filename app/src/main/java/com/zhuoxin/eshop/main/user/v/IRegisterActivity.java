package com.zhuoxin.eshop.main.user.v;

/**
 * RegisterActivity的接口，定义了RegisterActivity的功能方法
 * Created by Administrator on 2017/8/16.
 */

interface IRegisterActivity {
    String getUserName();//获取用户名
    String getPsd();//获取密码

    void registerFail();//注册失败调用
    void registerSuccess();//注册成功调用

    void showResult(String str);//展示注册结果

    void showPB();//展示进度条
    void hidePB();//隐藏进度条

}
