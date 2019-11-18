package com.tv.GreenGrubBox.Fragment.Individual;

import com.tv.GreenGrubBox.BaseClasses.MvpView;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
import com.tv.GreenGrubBox.data.modal.PackageModalMain;

public interface IndividualMvpView extends MvpView {
    void populatePackageData(PackageModalMain mPackageModalMain);

    void MainScreen(LoginResponse mLoginResponse);
}
