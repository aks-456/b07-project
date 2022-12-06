package com.example.b07project;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.errorprone.annotations.DoNotMock;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Mock
    LoginContract.LoginView loginView; //view

    @Mock
    LoginInt loginint; //model

    @Mock
    LoginInformation logininformation;


    @Test
    public void testLoginSuccessful(){
        String userName = "test@gmail.com";
        String password = "password";
        logininformation = new LoginInformation(userName,password);
        LoginPresenter presenter = new LoginPresenter(loginView);
        presenter.begin(logininformation);
        verify(loginView,times(1)).onSuccess(any());

    }

    @Test
    public void testLoginFailed1(){
        String userName = "test@gmail.com";
        String password = "12345";
        logininformation = new LoginInformation(userName,password);
        LoginPresenter presenter = new LoginPresenter(loginView);
        presenter.begin(logininformation);
        verify(loginView,times(1)).onFailed(any());

    }

    @Test
    public void testLoginFailed2(){
        String userName = "testgmail.com";
        String password = "12345";
        logininformation = new LoginInformation(userName,password);
        LoginPresenter presenter = new LoginPresenter(loginView);
        presenter.begin(logininformation);
        verify(loginView,times(1)).onFailed(any());

    }

    @Test
    public void testLoginFailed3(){
        String userName = "testgmail.com";
        String password = "1234567";
        logininformation = new LoginInformation(userName,password);
        LoginPresenter presenter = new LoginPresenter(loginView);
        presenter.begin(logininformation);
        verify(loginView,times(1)).onFailed(any());

    }









}