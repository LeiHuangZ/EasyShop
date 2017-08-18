package com.zhuoxin.eshop.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuoxin.eshop.R;
import com.zhuoxin.eshop.fragment.LogoutFragment;
import com.zhuoxin.eshop.main.goods.v.GoodsFragment;
import com.zhuoxin.eshop.main.me.v.MeFragment;
import com.zhuoxin.eshop.model.CachePreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_tv_title)
    TextView mMainTvTitle;
    @BindView(R.id.main_tb)
    Toolbar mMainTb;
    @BindViews({R.id.main_tv_shop, R.id.main_tv_chat, R.id.main_tv_people, R.id.main_tv_me})
    TextView[] mMainTvs;
    @BindColor(R.color.colorPrimary)
    int colorPrimary;
    @BindColor(R.color.text_color_hint)
    int colorHint;
    @BindView(R.id.main_vp)
    ViewPager mMainVp;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();

        initView();

        CachePreferences.init(this);
    }

    /**
     * 创建相应的Fragment并存入集合
     */
    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new GoodsFragment());
        fragmentList.add(new LogoutFragment());
        fragmentList.add(new LogoutFragment());
        fragmentList.add(new MeFragment());
    }

    /**
     * 初始化View,包括设置标题,设置menu选中状态,为ViewPager设置适配器
     */
    private void initView() {
        //设置标题
        setSupportActionBar(mMainTb);

        mMainTvs[0].setTextColor(colorPrimary);
        mMainTvs[0].setSelected(true);
        mMainTvTitle.setText(R.string.main_shop);

        //为ViewPager设置适配器
        mMainVp.setAdapter(adapter);
        //
        mMainVp.addOnPageChangeListener(vpChangeListener);
    }

    /**
     * 创建ViewPager的适配器,设置ViewPager的条目个数,返回相应位置的Fragment
     */
    FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mMainTvs.length;
        }
    };

    /**
     * 创建ViewPager的滑动监听,当滑动到某个Fragment时,设置标题为对应标题
     * 并且设置对应位置的menu条目为选中状态
     */
    ViewPager.OnPageChangeListener vpChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mMainTvTitle.setText(mMainTvs[position].getText());
            for (int i = 0; i < mMainTvs.length; i++) {
                mMainTvs[i].setTextColor(colorHint);
                mMainTvs[i].setSelected(false);
                mMainTvs[i].setTag(i);
            }
            mMainTvs[position].setTextColor(colorPrimary);
            mMainTvs[position].setSelected(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * menu的点击事件,实现点击menu条目,展示相应的Fragment
     */
    @OnClick({R.id.main_tv_shop, R.id.main_tv_chat, R.id.main_tv_people, R.id.main_tv_me})
    public void onViewClicked(TextView view) {
        for (int i = 0; i < mMainTvs.length; i++) {
            mMainTvs[i].setTextColor(colorHint);
            mMainTvs[i].setSelected(false);
            mMainTvs[i].setTag(i);
        }
        view.setTextColor(colorPrimary);
        view.setSelected(true);
        int mI = (int) view.getTag();
        mMainVp.setCurrentItem(mI);
    }


    /**
     * 重写系统返回键,实现双击退出
     */
    private long oneTime;

    @Override
    public void onBackPressed() {
        long twoTime = System.currentTimeMillis();
        if (twoTime - oneTime > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            oneTime = twoTime;
        } else {
            finish();
        }
    }
}
