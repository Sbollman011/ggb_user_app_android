package com.tv.GreenGrubBox.signup.signupcorporate;

import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;


/**
 * Created by admin on 30/01/18.
 */

public interface SignUpCorporateMvpPresenter <V extends SignUpCorporateMvpView,
        I extends SignUpCorporateMvpInteractor> extends MvpPresenter<V, I> {

   // void signUpCorporate(String userId,String firstName,String lastName,String packageId,String cardToken,String promoCode);
    void signUpCorporate(String firstName, String promoCode, SignUpResponse mSignUpResponse, DeviceInfo mDeviceInfo);

    void doLogin(String email,String password);
}
