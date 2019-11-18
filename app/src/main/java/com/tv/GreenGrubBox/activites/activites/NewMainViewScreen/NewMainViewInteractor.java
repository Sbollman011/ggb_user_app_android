package com.tv.GreenGrubBox.activites.activites.NewMainViewScreen;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;

/**
 * Created by tv-1 on 31/01/18.
 */

public class NewMainViewInteractor extends BaseInteractor implements NewMainViewMvpInteractor {


    @Inject
    public NewMainViewInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}
