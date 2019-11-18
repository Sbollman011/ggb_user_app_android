package com.tv.GreenGrubBox.Fragment.Individual;

import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;
import com.tv.GreenGrubBox.data.modal.CardDetailModal;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.SignUpResponseDatum;

public interface IndividualMvpPresenter<V extends IndividualMvpView,
        I extends IndividualMvpInteractor> extends MvpPresenter<V, I> {

    void getAllPackages();

    void completeRegistration(String name, String id, CardDetailModal cardDetailModal, final SignUpResponseDatum mSignUpResponseMain, DeviceInfo mDeviceInfo);
}
