package com.sxq.github.data.model.login;


import com.sxq.github.BuildConfig;
import com.sxq.github.utils.PrefGetter;

/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public class Login {

    private String mLogin;

    private String mToken;

    private static Login mCurrentUser = null;

    public Login(String login, String token) {
        mLogin = login;
        mToken = token;
    }

    public static Login getCurrentUser() {
        if (mCurrentUser == null) {
            mCurrentUser = new Login(PrefGetter.getCurrentUserName(), PrefGetter.getCurrentUserToken());
        }
        return mCurrentUser;
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
