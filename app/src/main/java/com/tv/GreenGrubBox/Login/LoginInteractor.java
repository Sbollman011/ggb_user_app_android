package com.tv.GreenGrubBox.login;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;

/**
 * Created by admin on 29/01/18.
 */

public class LoginInteractor extends BaseInteractor implements LoginMvpInteractor {

   @Inject
    public LoginInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}
