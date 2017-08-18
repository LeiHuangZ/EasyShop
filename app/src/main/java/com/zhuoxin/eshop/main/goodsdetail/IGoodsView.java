package com.zhuoxin.eshop.main.goodsdetail;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.zhuoxin.eshop.model.GoodsDetail;
import com.zhuoxin.eshop.model.User;

import java.util.ArrayList;

/**
 * 继承了MVP VIEW顶层接口的View层接口
 * Created by Administrator on 2017/8/18.
 */

public interface IGoodsView extends MvpView {
    void showProgress();//显示进度条
    void hideProgress();//隐藏进度条
    //设置图片路径
    void setImageData(ArrayList<String> arrayList);
    //设置商品信息
    void setData(GoodsDetail data, User goods_user);
    //商品不存在
    void showError();
    //提示信息
    void showMessage(String msg);
    //删除商品
    void deleteEnd();

}
