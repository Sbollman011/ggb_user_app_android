package com.tv.GreenGrubBox.home;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;

/**
 * Created by admin on 19/01/18.
 */

public class HomeInteractor extends BaseInteractor implements HomeMvpInteractor {

    @Inject
    public HomeInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}
