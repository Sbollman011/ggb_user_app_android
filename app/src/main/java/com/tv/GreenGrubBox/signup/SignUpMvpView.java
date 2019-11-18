package com.tv.GreenGrubBox.signup;

import com.tv.GreenGrubBox.BaseClasses.MvpView;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;

/**
 * Created by tv-1 on 29/01/18.
 */

public interface SignUpMvpView extends MvpView {
    void signUpResponse(SignUpResponse mSignUpResponse, String email);
}
