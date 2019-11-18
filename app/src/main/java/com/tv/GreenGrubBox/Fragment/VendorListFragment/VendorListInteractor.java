package com.tv.GreenGrubBox.Fragment.VendorListFragment;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;

/**
 * Created by user on 7/2/18.
 */

public class VendorListInteractor extends BaseInteractor implements  VendorListMvpInteractor {

    @Inject
    public VendorListInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}
