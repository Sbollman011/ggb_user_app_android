package com.tv.GreenGrubBox.Fragment.VendorListFragment;

import com.tv.GreenGrubBox.BaseClasses.MvpView;
import com.tv.GreenGrubBox.data.modal.MainLocationModal;

/**
 * Created by user on 7/2/18.
 */

public interface VendorListMvpView extends MvpView {

    void vendorsDataPopulate(MainLocationModal mMainLocationDatumLIst);

}
