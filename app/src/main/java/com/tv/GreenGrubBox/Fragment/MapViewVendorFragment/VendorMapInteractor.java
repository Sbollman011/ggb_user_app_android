package com.tv.GreenGrubBox.Fragment.MapViewVendorFragment;

import com.tv.GreenGrubBox.BaseClasses.BaseInteractor;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.prefs.PreferencesHelper;

import javax.inject.Inject;

/**
 * Created by tv-1 on 07/02/18.
 */

public class VendorMapInteractor extends BaseInteractor implements  VendorMapMvpInteractor {


    @Inject
    public VendorMapInteractor(PreferencesHelper preferencesHelper, ApiHelper apiHelper) {
        super(preferencesHelper, apiHelper);
    }
}
