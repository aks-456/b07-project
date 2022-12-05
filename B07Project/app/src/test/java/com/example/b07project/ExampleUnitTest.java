package com.example.b07project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;
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
    login_activity view;

    @Mock
    LoginContract.LoginView contract;

    @Test
    public void testPresenter(){
   //     when(view.->.some method call).thenReturn("abc")
        //when this method is invoke return abc
        //when()
    }







}