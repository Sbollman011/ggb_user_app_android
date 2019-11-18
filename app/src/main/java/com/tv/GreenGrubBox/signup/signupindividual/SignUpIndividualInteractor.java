package com.tv.GreenGrubBox.signup.signupindividual;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;

/**
 * Created by tv-1 on 30/01/18.
 */

public class SignUpIndividualInteractor extends BaseInteractor implements SignUpIndividualMvpInteractor {


    @Inject
    public SignUpIndividualInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}
