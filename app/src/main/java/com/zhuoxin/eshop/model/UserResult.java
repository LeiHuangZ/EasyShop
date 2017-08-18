package com.zhuoxin.eshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

public class UserResult {

    private int code = 0;
    @SerializedName("msg")
    private String message = "";
    @SerializedName("data")
    private User user;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", user=" + user +
                '}';
    }
}
