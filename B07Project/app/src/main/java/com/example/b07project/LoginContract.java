package com.example.b07project;

public interface LoginContract {
    interface LoginView{
        void onSuccess(LoginInformation info);
        void onFailed(String message);
    }
    interface LoginListener{
        void onSuccess(LoginInformation info);
        void onFailed(String message);
    }
}
