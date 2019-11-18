package com.tv.GreenGrubBox.login;

import com.tv.GreenGrubBox.BaseClasses.MvpView;
import com.tv.GreenGrubBox.data.modal.LoginResponse;

/**
 * Created by admin on 29/01/18.
 */

public interface LoginMvpView extends MvpView {
    void MainScreen(LoginResponse mLoginResponse);

    void openIndividualScreen(LoginResponse mLoginResponse);

    void openCorporateScreen(LoginResponse mLoginResponse);

    void activateAccount(LoginResponse mLoginResponse, String email);
}
