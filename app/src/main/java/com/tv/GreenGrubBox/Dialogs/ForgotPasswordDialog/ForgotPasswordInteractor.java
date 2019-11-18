package com.tv.GreenGrubBox.Dialogs.ForgotPasswordDialog;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;

/**
 * Created by user on 12/2/18.
 */

public class ForgotPasswordInteractor extends BaseInteractor implements ForgotPasswordMvpInteractor {

    @Inject
    public ForgotPasswordInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}
