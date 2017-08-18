package com.zhuoxin.eshop.main.goods.p;

/**
 * 加载商品信息的控制台接口，定义了加载的方法
 * Created by Administrator on 2017/8/17.
 */

public interface IGoodsPresenter {
    void refreshData(String type);
    void loadData(String type);
}
