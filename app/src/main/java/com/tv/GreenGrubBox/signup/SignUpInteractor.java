package com.tv.GreenGrubBox.signup;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;


/**
 * Created by tv-1 on 29/01/18.
 */

public class SignUpInteractor extends BaseInteractor implements SignUpMvpInteractor {

    @Inject
    public SignUpInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}
