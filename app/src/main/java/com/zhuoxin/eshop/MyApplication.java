package com.zhuoxin.eshop;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhuoxin.eshop.model.CachePreferences;

/**
 * 用于初始化生命周期和应用的生命周期一样的一些变量
 * Created by Administrator on 2017/8/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        CachePreferences.init(this);
        //ImageLoader相关初始化
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)//开启硬盘缓存
                .cacheInMemory(true)//开启内存缓存
                .resetViewBeforeLoading(true)//再加载前重置ImageView
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(1024 * 1024 * 4)//设置内存缓存的大小4M
                .defaultDisplayImageOptions(displayImageOptions)//设置选项配置
                .build();
        ImageLoader.getInstance().init(configuration);
        super.onCreate();
    }
}
