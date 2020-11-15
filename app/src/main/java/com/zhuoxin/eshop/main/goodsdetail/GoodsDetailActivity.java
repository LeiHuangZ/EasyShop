package com.zhuoxin.eshop.main.goodsdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhuoxin.eshop.R;
import com.zhuoxin.eshop.components.ProgressDialogFragment;
import com.zhuoxin.eshop.model.GoodsDetail;
import com.zhuoxin.eshop.model.User;
import com.zhuoxin.eshop.network.EasyShopApi;
import com.zhuoxin.eshop.utils.ActivityUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2017/8/18.
 */

public class GoodsDetailActivity extends MvpActivity<IGoodsView, GoodsDetailPresenter> implements IGoodsView {
    @BindView(R.id.tv_goods_delete)
    TextView mTvGoodsDelete;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;
    @BindView(R.id.tv_detail_name)
    TextView mTvDetailName;
    @BindView(R.id.tv_detail_price)
    TextView mTvDetailPrice;
    @BindView(R.id.tv_detail_master)
    TextView mTvDetailMaster;
    @BindView(R.id.tv_detail_describe)
    TextView mTvDetailDescribe;
    @BindView(R.id.btn_detail_message)
    Button mBtnDetailMessage;
    @BindView(R.id.linearLayout3)
    LinearLayout mLinearLayout3;
    @BindView(R.id.tv_goods_error)
    TextView mTvGoodsError;
    @BindView(R.id.activity_goods_detail)
    RelativeLayout mActivityGoodsDetail;
    private ProgressDialogFragment progress;
    private ActivityUtils mActivity;
    private GoodsDetailAdapter mAdapter;
    private ArrayList<ImageView> mList = new ArrayList<>();
    private static final String UUID = "uuid";
    private static final String TYPE = "type";

    private String uuid;
    private int state;//1表示从我的商品界面跳转，2表示从市场商品展示页面跳转而来

    public static Intent getIntent(Context context, String uuid, int type) {
        Intent intent = new Intent();
        intent.putExtra(UUID, uuid);
        intent.putExtra(TYPE, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mActivity = new ActivityUtils(this);//获取当前弱引用

        if (mAdapter == null) {
            mAdapter = new GoodsDetailAdapter();
        }
        mViewpager.setAdapter(mAdapter);

        uuid = getIntent().getStringExtra(UUID);
        state = getIntent().getIntExtra(TYPE, 0);

        if (state == 1){//从我的商品界面跳转过来
            mBtnDetailMessage.setVisibility(View.GONE);//不可发送消息
            mTvGoodsDelete.setVisibility(View.VISIBLE);//可删除商品
        } else {//从市场商品展示界面跳转
            mBtnDetailMessage.setVisibility(View.VISIBLE);//可发送消息
            mTvGoodsDelete.setVisibility(View.GONE);//不可删除商品
        }
        presenter.getGoodsDetail(uuid);
    }

    @NonNull
    @Override
    public GoodsDetailPresenter createPresenter() {
        return new GoodsDetailPresenter();
    }

    @Override
    public void showProgress() {
        if (progress == null) {
            progress = new ProgressDialogFragment();
        } else {
            progress.show(getSupportFragmentManager(), "goods_detail");
        }
    }

    @Override
    public void hideProgress() {
        progress.dismiss();
    }

    @Override
    public void setImageData(ArrayList<String> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {//根据传入的集合大小创建ImageView
            ImageView img = new ImageView(this);
            ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL + arrayList.get(i), img);
            mList.add(img);
        }
        mAdapter.addList(mList);
        mIndicator.setViewPager(mViewpager);
    }

    @Override
    public void setData(GoodsDetail data, User goods_user) {
        mTvDetailName.setText(data.getName());//商品名称
        String price = "商品价格:" + getString(R.string.goods_money, data.getPrice());
        mTvDetailPrice.setText(price);//2222￥
        String author = "发布者:" + goods_user.getNick_name();
        mTvDetailMaster.setText(author);//发布者的昵称
        String describe = "商品描述:\n" + data.getDescription();
        mTvDetailDescribe.setText(describe);//商品的描述
    }

    @Override
    public void showError() {
        mTvGoodsError.setVisibility(View.VISIBLE);
        mToolbar.setTitle("商品过期不存在");
    }

    @Override
    public void showMessage(String msg) {
        mActivity.showToast(msg);
    }

    @Override
    public void deleteEnd() {
        finish();
    }

    @OnClick({R.id.tv_goods_delete, R.id.btn_detail_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_delete:
                deleteEnd();
                break;
            case R.id.btn_detail_message:
                break;
        }
    }
}
