package com.tv.GreenGrubBox.login;

import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;

/**
 * Created by admin on 29/01/18.
 */

public interface LoginMvpPresenter  <V extends com.tv.GreenGrubBox.login.LoginMvpView,
        I extends com.tv.GreenGrubBox.login.LoginMvpInteractor> extends MvpPresenter<V, I> {

    void doLogin(String email, String password, DeviceInfo mDeviceInfo);

}
