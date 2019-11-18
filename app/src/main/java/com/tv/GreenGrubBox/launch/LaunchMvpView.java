package com.tv.GreenGrubBox.launch;

import com.tv.GreenGrubBox.BaseClasses.MvpView;
import com.tv.GreenGrubBox.data.modal.LoginRequest;

/**
 * Created by admin on 16/01/18.
 */

public interface LaunchMvpView extends MvpView {



    void openRegScreen(LoginRequest mLoginRequest);
}
