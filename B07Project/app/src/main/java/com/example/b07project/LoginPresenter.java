package com.example.b07project;

public class LoginPresenter implements LoginContract.LoginListener{

    private LoginContract.LoginView login_view; // view field
    private LoginInt login_interactor; // model field

    public LoginPresenter(LoginContract.LoginView login_view)
    {
        this.login_view = login_view;
        login_interactor = new LoginInt(this);
    }
    public void begin(LoginInformation info)
    {
        login_interactor.login(info);
    }

    @Override
    public void onSuccess(LoginInformation info) {
        login_view.onSuccess(info);
    }

    @Override
    public void onFailed(String message) {
        login_view.onFailed(message);
    }

}
