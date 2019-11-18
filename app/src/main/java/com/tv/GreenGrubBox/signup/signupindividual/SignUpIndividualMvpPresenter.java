package com.tv.GreenGrubBox.signup.signupindividual;

import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;

/**
 * Created by tv-1 on 30/01/18.
 */

public interface SignUpIndividualMvpPresenter  <V extends SignUpIndividualMvpView,
        I extends SignUpIndividualMvpInteractor> extends MvpPresenter<V, I> {
    void getAllPackages();

    void completeRegistration(String trim, String id, String tokenId, SignUpResponse mSignUpResponse, DeviceInfo mDeviceInfo);

    void doLogin(String email,String password);
}
