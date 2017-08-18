package com.zhuoxin.eshop.network;

import com.google.gson.Gson;
import com.zhuoxin.eshop.model.CachePreferences;
import com.zhuoxin.eshop.model.GoodsUpLoad;
import com.zhuoxin.eshop.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 封装okHttp，实现单例模式，提供了请求所有信息的方法
 * Created by Administrator on 2017/8/15.
 */

public class EShopHttpClient {
    /*声明所需的本类对象、OkHttpClient、Gson对象*/
    private static EShopHttpClient mHttpClient;
    private static OkHttpClient mOkHttpClient;
    private static Gson mGson;

    public static Gson getGson() {
        return mGson;
    }

    /**
     * 提供公共的静态的方法，供外界获取对象
     *
     * @return EShopHttpClient
     */
    public static EShopHttpClient getInstance() {
        if (mHttpClient == null) {
            mHttpClient = new EShopHttpClient();
        }
        return mHttpClient;
    }

    /**
     * 私有化构造，并实例化OkHttpClient和Gson对象
     */
    private EShopHttpClient() {
        HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = new OkHttpClient.Builder().addInterceptor(mHttpLoggingInterceptor).build();
        if (mGson == null) {
            mGson = new Gson();
        }
    }

    /**
     * 登录
     * @param name 用户名
     * @param psd  密码
     * @return Call对象，得到OkHttp的Call对象
     */
    public Call login(String name, String psd) {
        //创建RequestBody
        FormBody mBody = new FormBody.Builder()
                .add("username", name)
                .add("password", psd)
                .build();
        //创建Request对象
        Request mRequest = new Request.Builder()
                .post(mBody)
                .url(EasyShopApi.BASE_URL + EasyShopApi.LOGIN)
                .build();
        return mOkHttpClient.newCall(mRequest);
    }

    /**
     * 注册
     * @param name 用户名
     * @param psd 密码
     * @return 返回call对象 得到注册的Call对象
     */
    public Call register(String name, String psd) {
        //创建RequestBody
        FormBody mBody = new FormBody.Builder()
                .add("username", name)
                .add("password", psd)
                .build();
        //创建Request对象
        Request mRequest = new Request.Builder()
                .post(mBody)
                .url(EasyShopApi.BASE_URL + EasyShopApi.REGISTER)
                .build();
        return mOkHttpClient.newCall(mRequest);
    }

    public Call loadGoods(String pageNo, String type){
        //创建RequestBody
        FormBody mBody = new FormBody.Builder()
                .add("pageNo", pageNo)
                .add("type", type)
                .build();
        //创建Request对象
        //创建Request对象
        Request mRequest = new Request.Builder()
                .post(mBody)
                .url(EasyShopApi.BASE_URL + EasyShopApi.GETGOODS)
                .build();
        return mOkHttpClient.newCall(mRequest);
    }

    //获取商品详情
    //http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=view&uuid=5606FF8EF60146A48F1FCDC34144907D
    public Call getGoodsData(String goodsUuid){
        RequestBody requestBody=new FormBody.Builder()
                .add("uuid",goodsUuid)//uuid=5606FF8EF60146A48F1FCDC34144907D
                .build();
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.DETAIL)//http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=view
                .post(requestBody)
                .build();
        return mOkHttpClient.newCall(request);
    }


    //上传头像
    public Call uploadAvatar(File file){//上传图片文件
        RequestBody requestBody=new MultipartBody.Builder()
                //上传文件的方式
                .setType(MultipartBody.FORM)//multipart/form-data
                //需要一个用户类JSON字符串
                .addFormDataPart("user",mGson.toJson(CachePreferences.getUser()))
                //"image"：key值   file.getName():文件名  RequestBody.create(MediaType.parse("image/png"),file)要上传的文件和文件类型
                .addFormDataPart("image",file.getName(),RequestBody.create(MediaType.parse("image/png"),file))
                .build();
        //http://wx.feicuiedu.com:9094/yitao/UserWeb?method=update
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.UPDATA)
                .post(requestBody)
                .build();
        return mOkHttpClient.newCall(request);
    }
    //修改昵称
    public Call uploadUser(User user) {
        //构建请求体
        RequestBody requestBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//multipart/form-data
                //传入一个用户的json字符串
                .addFormDataPart("user",mGson.toJson(user))
                .build();
        //构建请求
        Request request=new Request.Builder()
                //http://wx.feicuiedu.com:9094/yitao/UserWeb?method=update
                .url(EasyShopApi.BASE_URL+EasyShopApi.UPDATA)
                .post(requestBody)
                .build();
        return mOkHttpClient.newCall(request);
    }
    //获取个人商品
    //商品每页20条数据
    //pageNo=1 //string
    //发布者
    //master="android"
    //类型
    //type="dress"    //空字符串可获取所有个人商品
    public Call getPersonData(int pageNo, String type, String master) {
        RequestBody requestBody=new FormBody.Builder()
                .add("pageNo",String.valueOf(pageNo))
                .add("master",master)
                .add("type",type)
                .build();
        Request request=new Request.Builder()
                //http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=getAll
                .url(EasyShopApi.BASE_URL+EasyShopApi.GETGOODS)
                .post(requestBody)
                .build();
        return mOkHttpClient.newCall(request);
    }
    //删除商品
    /*
            路径： GoodsServlet?method=delete
            方法： post
            请求：
            //商品的uuid(商品表中的主键)
            uuid="....."
     */
    public Call deleteGoods(String uuid){
        RequestBody requestBody=new FormBody.Builder()
                .add("uuid",uuid)
                .build();
//http://wx.feicuiedu.com:9094/yitao/GoodsServlet?method=delete
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.DELETE)
                .post(requestBody)
                .build();
        return mOkHttpClient.newCall(request);
    }
    //上传商品
    /*
    路径： GoodsServlet?method=add
    方法： post/Multipart
    请求：
    //请求是一个商品类的JSON字符串， 商品的图片以文件形式上传
    {
        "description": "诚信商家，非诚勿扰",     //商品描述
        "master": "android",                    //商品发布者
        "name": "礼物，鱼丸，鱼翅，火箭，飞机",   //商品名称
        "price": "88",                          //商品价格
        "type": "gift"                          //商品类型
    }
     */
    //files表示多个图片的文件
    public Call upLoad(GoodsUpLoad goodsUpLoad, ArrayList<File> files){
        MultipartBody.Builder builder=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("good",mGson.toJson(goodsUpLoad));
        //将所有图片添加进来
        for (File file:files){//将图片一张一张添加到请求体中
            builder.addFormDataPart("image",file.getName(),RequestBody.create(MediaType.parse("image/png"),file));
        }
        RequestBody requestBody=builder.build();//构建请求体
        //构建请求
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.UPLOADGOODS)
                .post(requestBody)
                .build();
        return mOkHttpClient.newCall(request);
    }
    //获取好友列表
    /*
    路径： UserWeb?method=getNames
    方法： post
    请求：
    //表单
    name=[环信id1,环信id2,....]
     */
    //ids 环信ID集合
    public Call getUsers(List<String> ids){
        String names=ids.toString();//"[1 2 3 4]"
        //去空格
        names=names.replace(" ","");//[1234]
        RequestBody requestBody=new FormBody.Builder()
                .add("name",names)
                .build();
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.GET_NAMES)
                .post(requestBody)
                .build();
        return mOkHttpClient.newCall(request);
    }
    //根据用户昵称查询好友
    /*
    路径： UserWeb?method=getUsers
    方法： post
    请求：
    //表单
    nickname=用户昵称
     */
    public Call getSearchUser(String nickname){
        RequestBody requestBody=new FormBody.Builder()
                .add("nickname",nickname)
                .build();
        Request request=new Request.Builder()
                .url(EasyShopApi.BASE_URL+EasyShopApi.GET_USER)
                .post(requestBody)
                .build();
        return mOkHttpClient.newCall(request);
    }


}
