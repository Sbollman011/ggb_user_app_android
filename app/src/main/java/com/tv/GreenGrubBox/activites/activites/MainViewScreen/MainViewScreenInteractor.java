package com.tv.GreenGrubBox.activites.activites.MainViewScreen;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;

/**
 * Created by user on 24/1/18.
 */

public class MainViewScreenInteractor extends BaseInteractor implements MainViewScreenMvpInteractor {

    @Inject
    public MainViewScreenInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}