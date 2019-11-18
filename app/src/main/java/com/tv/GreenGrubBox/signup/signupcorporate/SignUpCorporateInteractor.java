package com.tv.GreenGrubBox.signup.signupcorporate;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;

/**
 * Created by admin on 30/01/18.
 */

public class SignUpCorporateInteractor extends BaseInteractor implements SignUpCorporateMvpInteractor {

    @Inject
    public SignUpCorporateInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}
