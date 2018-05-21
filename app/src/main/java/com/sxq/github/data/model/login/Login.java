package com.sxq.github.data.model.login;


import com.sxq.github.BuildConfig;

/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public class Login {

    private String mLogin;

    private String mToken;

    public Login(String login, String token) {
        mLogin = login;
        mToken = token;
    }

    public static Login getCurrentUser() {
        if (BuildConfig.DEBUG) {
            //TODO delete it
//            return new Login("s-xq", BuildConfig.GITHUB_AUTH_TOKEN);
            return new Login("JakeWharton", BuildConfig.GITHUB_AUTH_TOKEN);
        }
        return null;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }
}
