package com.zhuoxin.eshop.main.goods.m;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.zhuoxin.eshop.R;
import com.zhuoxin.eshop.model.GoodInfo;
import com.zhuoxin.eshop.model.GoodsResult;
import com.zhuoxin.eshop.network.EShopHttpClient;
import com.zhuoxin.eshop.network.UICallBack;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * 加载商品信息具体实现类
 * Created by Administrator on 2017/8/17.
 */

public class GoodsBiz implements IGoodsBiz {
    private MyRCAdapter mAdapter;
    private int pageNo;

    public GoodsBiz(RecyclerView recyclerView) {
        mAdapter = new MyRCAdapter(0);//创建适配器
        recyclerView.setAdapter(mAdapter);//为RecyclerView设置适配器
    }

    @Override
    public void refreshData(final Context context, String type, final OnLoadGoodsListener listener) {
        final EShopHttpClient mHttpClient = EShopHttpClient.getInstance();
        Call mCall = mHttpClient.loadGoods("1", type);
        mCall.enqueue(new UICallBack() {
            @Override
            public void onUIFailure(Call call, IOException e) {
                listener.loadFail(e.getMessage());
            }

            @Override
            public void onUIResponse(Call call, String response) {
                Gson gson = EShopHttpClient.getGson();
                Log.e("result", "onUIResponse: " + response);
                GoodsResult mGoodsResult = gson.fromJson(response, GoodsResult.class);
                if (mGoodsResult.getDatas().size() != 0) {
                    List<GoodInfo> mList = mGoodsResult.getDatas();
                    mAdapter.addData(mList);
                } else {
                    listener.loadFail(context.getString(R.string.load_more_error));
                }
                pageNo = 2;
            }
        });
    }

    @Override
    public void loadData(final Context context, String type, final OnLoadGoodsListener listener) {
        Call mCall = EShopHttpClient.getInstance().loadGoods(String.valueOf(pageNo), type);
        mCall.enqueue(new UICallBack() {
            @Override
            public void onUIFailure(Call call, IOException e) {
                listener.loadFail(e.getMessage());
            }

            @Override
            public void onUIResponse(Call call, String response) {
                GoodsResult mResult = EShopHttpClient.getGson().fromJson(response, GoodsResult.class);
                if (mResult.getDatas().size() == 0) {
                    listener.loadFail(context.getString(R.string.load_more_end));
                } else {
                    List<GoodInfo> mDatas = mResult.getDatas();
                    mAdapter.addData(mDatas);
                    listener.loadSuccess("加载成功");
                    pageNo++;
                }
            }
        });
    }
}
