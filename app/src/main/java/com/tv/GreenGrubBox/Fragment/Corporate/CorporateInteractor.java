package com.tv.GreenGrubBox.Fragment.Corporate;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.Fragment.VendorListFragment.VendorListMvpInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;

public class CorporateInteractor extends BaseInteractor implements CorporateMvpInteractor {

    @Inject
    public CorporateInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}
