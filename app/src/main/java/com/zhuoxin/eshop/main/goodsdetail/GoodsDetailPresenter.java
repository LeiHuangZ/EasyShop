package com.zhuoxin.eshop.main.goodsdetail;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.zhuoxin.eshop.model.GoodsDetail;
import com.zhuoxin.eshop.model.GoodsDetailResult;
import com.zhuoxin.eshop.network.EShopHttpClient;
import com.zhuoxin.eshop.network.UICallBack;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;

/**
 * 控制台接口，继承MvpNullObjectBasePresenter，获得、删除商品信息
 * Created by Administrator on 2017/8/18.
 */

public class GoodsDetailPresenter extends MvpNullObjectBasePresenter<IGoodsView> {
    @Override//绑定VIew
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
    }

    @Override//接触View绑定
    public void attachView(IGoodsView view) {
        super.attachView(view);
    }

    void getGoodsDetail(String uuid) {
        getView().showProgress();
        Call mCall = EShopHttpClient.getInstance().getGoodsData(uuid);
        mCall.enqueue(new UICallBack() {
            @Override
            public void onUIFailure(Call call, IOException e) {
                getView().hideProgress();
                getView().showMessage(e.getMessage());
                getView().showError();
            }

            @Override
            public void onUIResponse(Call call, String response) {
                getView().hideProgress();
                GoodsDetailResult mResult = EShopHttpClient.getGson().fromJson(response, GoodsDetailResult.class);
                if (mResult.getCode() == 1) {
                    GoodsDetail mGoodsDetail = mResult.getDatas();
                    getView().setData(mGoodsDetail, mResult.getUser());
                    ArrayList<String> mList = new ArrayList<>();
                    for (int i = 0; i < mList.size(); i++) {
                        mList.add(mGoodsDetail.getPages().get(i).getUri());
                    }
                    getView().setImageData(mList);
                    getView().showMessage("加载成功");
                }else {
                    getView().showMessage("页面加载失败");
                    getView().showError();
                }
            }
        });

    }//获得商品信息

    void delGoodsDetail(String uuid) {
        getView().showProgress();
        Call mCall = EShopHttpClient.getInstance().deleteGoods(uuid);
        mCall.enqueue(new UICallBack() {
            @Override
            public void onUIFailure(Call call, IOException e) {
                getView().showMessage(e.getMessage());
            }

            @Override
            public void onUIResponse(Call call, String response) {
                GoodsDetailResult mResult = EShopHttpClient.getGson().fromJson(response, GoodsDetailResult.class);
                if (mResult.getCode() == 1) {
                    getView().deleteEnd();
                    getView().showMessage("删除失败");
                } else {
                    getView().showMessage("删除成功");
                }
            }
        });
    }//删除商品信息

}
