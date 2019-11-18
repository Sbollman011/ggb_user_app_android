package com.tv.GreenGrubBox.Fragment.Corporate;

import com.tv.GreenGrubBox.BaseClasses.MvpView;
import com.tv.GreenGrubBox.data.modal.LoginResponse;

public interface CorporateMvpView extends MvpView {
    void MainScreen(LoginResponse mLoginResponse);
}
