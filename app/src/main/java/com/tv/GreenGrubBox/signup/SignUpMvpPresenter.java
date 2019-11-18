package com.tv.GreenGrubBox.signup;

import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;

/**
 * Created by tv-1 on 29/01/18.
 */

public interface SignUpMvpPresenter <V extends SignUpMvpView,
        I extends SignUpMvpInteractor> extends MvpPresenter<V, I> {

    void signUp(String email, String password, int mAccountType, DeviceInfo mDeviceInfo);

}
