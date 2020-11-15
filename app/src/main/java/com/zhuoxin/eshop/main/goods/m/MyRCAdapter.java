package com.zhuoxin.eshop.main.goods.m;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhuoxin.eshop.R;
import com.zhuoxin.eshop.main.goodsdetail.GoodsDetailActivity;
import com.zhuoxin.eshop.model.AvaterLoadOptions;
import com.zhuoxin.eshop.model.GoodInfo;
import com.zhuoxin.eshop.network.EasyShopApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView的适配，设置数据
 * Created by Administrator on 2017/8/17.
 */

public class MyRCAdapter extends RecyclerView.Adapter<MyRCAdapter.MyViewHolder> {
    private List<GoodInfo> mList = new ArrayList<>();
    private Context mContext;
    private int state;//记录界面的类型，商品展示界面/我的商品界面

    public MyRCAdapter(int state) {
        this.state = state;
    }

    /**
     * 添加数据的方法，刷新集合
     *
     * @param list 装有商品信息的集合
     */
    public void addData(List<GoodInfo> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 清空集合中的数据
     */
    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View mView = LayoutInflater.from(mContext).inflate(R.layout.layout_goods_item, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final GoodInfo mInfo = mList.get(position);
        Log.e("adapter", "onBindViewHolder: " + "minfo.name" + mInfo.getName());
        holder.mShopTvGoodsName.setText(mInfo.getName());
        holder.mShopTvGoodsPrice.setText(mInfo.getPrice());
        ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL + mInfo.getPage(), holder.mShopImg, AvaterLoadOptions.build_item());

        //为RecyclerView设置适配器
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetailActivity.getIntent(mContext, mInfo.getUuid(), state);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_img)
        ImageView mShopImg;
        @BindView(R.id.shop_tv_goodsName)
        TextView mShopTvGoodsName;
        @BindView(R.id.shop_tv_goodsPrice)
        TextView mShopTvGoodsPrice;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
