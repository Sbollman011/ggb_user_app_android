package com.tv.GreenGrubBox.Fragment.Individual;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.Fragment.Individual.IndividualMvpInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;

public class IndividualInteractor extends BaseInteractor implements IndividualMvpInteractor {

    @Inject
    public IndividualInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}
