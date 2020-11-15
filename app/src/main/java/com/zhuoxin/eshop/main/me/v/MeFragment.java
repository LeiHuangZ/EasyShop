package com.zhuoxin.eshop.main.me.v;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.pkmmte.view.CircularImageView;
import com.zhuoxin.eshop.R;
import com.zhuoxin.eshop.main.user.v.LoginActivity;
import com.zhuoxin.eshop.main.user.v.UserActivity;
import com.zhuoxin.eshop.model.CachePreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MeFragment extends Fragment {

    @BindView(R.id.me_civ)
    CircularImageView mMeCiv;
    @BindView(R.id.me_tv_login)
    TextView mMeTvLogin;
    @BindView(R.id.me_tv_userinfo)
    TextView mMeTvUserinfo;
    @BindView(R.id.me_tv_goods)
    TextView mMeTvGoods;
    @BindView(R.id.me_tv_upload)
    TextView mMeTvUpload;
    Unbinder unbinder;
    private View mView;
    private String nickName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_me, container, false);
        }
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        nickName = CachePreferences.getUser().getNick_name();
        if (CachePreferences.getUser().getName() != null && nickName == null) {
            mMeTvLogin.setText(getActivity().getResources().getString(R.string.nickname_input));
        } else if (CachePreferences.getUser().getName() != null && nickName != null) {
            mMeTvLogin.setText(nickName);
        } else if (CachePreferences.getUser().getName() == null && nickName == null) {
            mMeTvLogin.setText(R.string.login_register);
        }
        super.onStart();
    }

    @OnClick({R.id.me_civ, R.id.me_tv_login, R.id.me_tv_userinfo, R.id.me_tv_goods, R.id.me_tv_upload})
    public void onViewClicked(View view) {
        Intent mIntent = new Intent(getActivity(), LoginActivity.class);
        if (nickName == null) {
            startActivity(mIntent);
            return;
        } else {
            startActivity(new Intent(getActivity(), UserActivity.class));
        }
        switch (view.getId()) {
            case R.id.me_civ:
                break;
            case R.id.me_tv_login:
                break;
            case R.id.me_tv_userinfo:
                break;
            case R.id.me_tv_goods:
                break;
            case R.id.me_tv_upload:
                break;
        }
    }
}
