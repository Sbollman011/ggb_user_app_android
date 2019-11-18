package com.tv.GreenGrubBox.launch;

import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;
import com.tv.GreenGrubBox.data.modal.LoginRequest;

/**
 * Created by admin on 16/01/18.
 */

public interface LaunchMvpPresenter <V extends LaunchMvpView,
        I extends LaunchMvpInteractor> extends MvpPresenter<V, I> {


    void doLogin(String email, String password, String deviceId, String accountType, String registration);

    void doSocialLogin(LoginRequest mLoginRequest);

}
