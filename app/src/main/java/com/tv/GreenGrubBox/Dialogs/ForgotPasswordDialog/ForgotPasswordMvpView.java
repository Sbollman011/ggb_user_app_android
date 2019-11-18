package com.tv.GreenGrubBox.Dialogs.ForgotPasswordDialog;

import com.tv.GreenGrubBox.BaseClasses.MvpView;
import com.tv.GreenGrubBox.data.modal.ForgotPasswordResponceModal;

/**
 * Created by user on 12/2/18.
 */

public interface ForgotPasswordMvpView extends MvpView {

    void closeDialog(ForgotPasswordResponceModal modal);
}
