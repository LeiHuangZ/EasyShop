package com.zhuoxin.eshop.main.goods.m;

import android.content.Context;

/**
 * 加载商品信息的model类的接口，定义了具体实现哪些功能
 * Created by Administrator on 2017/8/17.
 */

public interface IGoodsBiz {
    void refreshData(Context context, String type, OnLoadGoodsListener listener);//下拉刷新

    void loadData(Context context, String type, OnLoadGoodsListener listener);//上拉加载

    interface OnLoadGoodsListener {
        void loadFail(String error);

        void loadSuccess(String string);
    }
}
