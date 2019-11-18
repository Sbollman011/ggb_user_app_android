package com.tv.GreenGrubBox.Fragment.Corporate;

import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;
import com.tv.GreenGrubBox.data.modal.SignUpResponseDatum;

public interface CorporateMvpPresenter<V extends CorporateMvpView,
        I extends CorporateMvpInteractor> extends MvpPresenter<V, I> {
    void signUpCorporate(String name, String promocode, SignUpResponseDatum mSignUpResponse, DeviceInfo mDeviceInfo);
}
