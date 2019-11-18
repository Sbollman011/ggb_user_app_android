package com.tv.GreenGrubBox.ActivateAccount;


import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;

/**
 * Created by admin on 18/01/18.
 */

public interface ActivateAccountMvpPresenter<V extends ActivateAccountMvpView,
        I extends ActiviateAccountMvpInteractor> extends MvpPresenter<V, I> {


     void resendVerification(String email);


    void verifyOtp(String userEmail, int otp);
}
