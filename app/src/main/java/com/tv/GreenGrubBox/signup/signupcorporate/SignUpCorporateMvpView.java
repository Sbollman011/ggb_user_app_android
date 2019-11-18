package com.tv.GreenGrubBox.signup.signupcorporate;

import com.tv.GreenGrubBox.BaseClasses.MvpView;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;

/**
 * Created by admin on 30/01/18.
 */

public interface SignUpCorporateMvpView extends MvpView {
    void openNewScreen(SignUpResponse mSignUpDataUserRequest);

    void MainScreen(LoginResponse mLoginResponse);
}
