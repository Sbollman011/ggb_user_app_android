package com.tv.GreenGrubBox.Dialogs.ForgotPasswordDialog;

import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;

/**
 * Created by user on 12/2/18.
 */

public interface ForgotPasswordMvpPresenter <V extends  ForgotPasswordMvpView,
        I extends  ForgotPasswordMvpInteractor> extends MvpPresenter<V, I> {

    void doForgotPassword(String emailId);
}
