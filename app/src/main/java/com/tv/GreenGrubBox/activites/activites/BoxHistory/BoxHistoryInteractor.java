package com.tv.GreenGrubBox.activites.activites.BoxHistory;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.activites.activites.MainViewScreen.MainViewScreenMvpInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;

/**
 * Created by admin on 01/02/18.
 */

public class BoxHistoryInteractor extends BaseInteractor implements BoxHistoryMvpInteractor {

    @Inject
    public BoxHistoryInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}
