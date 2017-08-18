package com.zhuoxin.eshop.main.goods.v;

/**
 *
 * Created by Administrator on 2017/8/17.
 */

interface IGoodsFragment {
    void hideText();//隐藏文本控件

    void hideView();//隐藏上拉加载和下拉刷新的控件

    void showFail();//请求数据失败时调用
    void showSuccess(String string);//请求数据成功时调用
}
