package com.tv.GreenGrubBox.Fragment.Account;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;


/**
 * Created by tv-1 on 01/02/18.
 */

public class AccountInteractor extends BaseInteractor implements AccountMvpInteractor {


    @Inject
    public AccountInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}
