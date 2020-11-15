package com.zhuoxin.eshop.main.goods.v;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.zhuoxin.eshop.R;
import com.zhuoxin.eshop.main.goods.p.GoodsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class GoodsFragment extends Fragment implements IGoodsFragment {

    @BindView(R.id.rcv)
    RecyclerView mRcv;
    Unbinder unbinder;
    @BindView(R.id.goods_tv_fail)
    TextView mGoodsTvFail;
    @BindView(R.id.pfl)
    PtrClassicFrameLayout mPfl;
    private View mView;
    private GoodsPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_shop, container, false);
        }
        unbinder = ButterKnife.bind(this, mView);

        initRC();

        initView();
        return mView;
    }

    private void initView() {
        //使用本对象作为key，用来记录上一次刷新的时间,如果两次下拉刷新间隔太近，不会触发刷新
        mPfl.setLastUpdateTimeFooterRelateObject(this);
        mPfl.setBackgroundResource(R.color.black);//设置背景颜色
        mPfl.setDurationToClose(3000);//设置更新操作完成后，延迟多少秒后关闭进度条
        mPfl.setPtrHandler(new PtrDefaultHandler2() {
            @Override//上拉加载更多
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mPresenter.refreshData("");
            }

            @Override//下拉刷新
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPresenter.loadData("");
            }
        });

        mPfl.setDurationToClose(1500);
    }

    private void initRC() {
        /*
         * 调用控制台中的方法，请求数据
         */
        if (mPresenter == null) {
            mPresenter = new GoodsPresenter(this, mRcv);
        }
        mPresenter.refreshData("");


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override//隐藏错误信息
    public void hideText() {
        mGoodsTvFail.setVisibility(View.GONE);
    }

    @Override
    public void hideView() {
        mPfl.refreshComplete();//隐藏进度条
    }

    @Override//数据请求成功时回调
    public void showFail() {
        //展示失败的界面，即让TextView展示
        mGoodsTvFail.setVisibility(View.VISIBLE);
        hideView();
    }

    @Override//数据请求失败时回调
    public void showSuccess(String str) {
        //吐司展示请求数据成功信息
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        hideView();
    }
}
