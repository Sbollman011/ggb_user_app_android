package com.tv.GreenGrubBox.ActivateAccount;


import com.tv.GreenGrubBox.BaseClasses.MvpView;
import com.tv.GreenGrubBox.data.modal.LoginRequest;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
import com.tv.GreenGrubBox.data.modal.SignUpRequest;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;

/**
 * Created by admin on 18/01/18.
 */

public interface ActivateAccountMvpView extends MvpView {
    void responseVerifyOTP(SignUpResponse signUpResponse);

    //void handleResendPinResponse(ResendVerificationResponseModal modal, LoginRequest loginRequest);

    //void responseVerifyOTP(LoginResponse responseModal, LoginRequest loginRequest, SignUpRequest signUpRequest);
}
