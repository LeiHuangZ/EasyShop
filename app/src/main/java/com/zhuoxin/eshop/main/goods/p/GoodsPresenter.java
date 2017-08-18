package com.zhuoxin.eshop.main.goods.p;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhuoxin.eshop.main.goods.m.GoodsBiz;
import com.zhuoxin.eshop.main.goods.m.IGoodsBiz;
import com.zhuoxin.eshop.main.goods.v.GoodsFragment;
import com.zhuoxin.eshop.main.me.v.MeFragment;

/**
 * 加载商品信息的控制台，负责调度
 * Created by Administrator on 2017/8/17.
 */

public class GoodsPresenter implements IGoodsPresenter {
    private GoodsBiz mGoodsBiz;
    private GoodsFragment mFragment;

    public GoodsPresenter(GoodsFragment fragment, RecyclerView recylerView) {
        mFragment = fragment;
        mGoodsBiz = new GoodsBiz(recylerView);
        //为RecyclerView设置布局管理器，展示2列内容
        recylerView.setLayoutManager(new GridLayoutManager(mFragment.getActivity(),2));
    }

    @Override
    public void refreshData(String type) {
        mFragment.hideText();
        mGoodsBiz.refreshData(mFragment.getActivity(), type, new IGoodsBiz.OnLoadGoodsListener() {
            @Override
            public void loadFail(String error) {
                mFragment.showFail();
            }
            @Override
            public void loadSuccess(String string) {
                mFragment.showSuccess(string);
            }
        });
    }

    @Override
    public void loadData(String type) {
        mGoodsBiz.loadData(mFragment.getActivity(), type, new IGoodsBiz.OnLoadGoodsListener() {
            @Override
            public void loadFail(String error) {

            }

            @Override
            public void loadSuccess(String string) {

            }
        });
    }
}
