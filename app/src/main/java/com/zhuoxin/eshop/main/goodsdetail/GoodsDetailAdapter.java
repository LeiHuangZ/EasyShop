package com.zhuoxin.eshop.main.goodsdetail;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * ViewPager适配器
 * Created by Administrator on 2017/8/18.
 */

public class GoodsDetailAdapter extends PagerAdapter {
    private ArrayList<ImageView> mList;

    public GoodsDetailAdapter() {
        mList = new ArrayList<>();
    }

    public void addList(ArrayList<ImageView> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear(){
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
        super.destroyItem(container, position, object);
    }
}
