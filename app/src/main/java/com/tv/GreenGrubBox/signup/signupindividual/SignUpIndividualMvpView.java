package com.tv.GreenGrubBox.signup.signupindividual;

import com.tv.GreenGrubBox.BaseClasses.MvpView;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
import com.tv.GreenGrubBox.data.modal.PackageModalMain;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;

/**
 * Created by tv-1 on 30/01/18.
 */

public interface SignUpIndividualMvpView extends MvpView {
    void populatePackageData(PackageModalMain mPackageModalMain);


    void openHomeScreen(SignUpResponse mSignUpResponse);
    void MainScreen(LoginResponse mLoginResponse);
}
