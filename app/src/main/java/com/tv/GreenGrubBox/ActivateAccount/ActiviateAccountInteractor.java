package com.tv.GreenGrubBox.ActivateAccount;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;

/**
 * Created by admin on 18/01/18.
 */

public class ActiviateAccountInteractor extends BaseInteractor implements ActiviateAccountMvpInteractor {

    @Inject
    public ActiviateAccountInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}
