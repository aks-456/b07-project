package com.example.b07project;

import android.text.TextUtils;

public class LoginInt {

    private LoginContract.LoginListener login_listener;

    public LoginInt(LoginContract.LoginListener login_listener) {
        this.login_listener = login_listener;
    }
    public void login(LoginInformation info){
    if(faulty(info)) return;
    login_listener.onSuccess(info);
    }
    private boolean faulty(LoginInformation info)
    {
        if(TextUtils.isEmpty(info.getUsername()) || !info.getUsername().contains("@"))
        {
            login_listener.onFailed("Invalid Email");
            return true;
        }
        if(TextUtils.isEmpty(info.getPassword()))
        {
            login_listener.onFailed("Password Length >= 6");
            return true;
        }
        if(info.getPassword().length()<6)
        {
            login_listener.onFailed("Password Must Be Atleast 6 Characters");
            return true;
        }
        return false;

    }

}
