package com.tv.GreenGrubBox.Fragment.MapViewVendorFragment;

import com.tv.GreenGrubBox.BaseClasses.MvpView;
import com.tv.GreenGrubBox.data.modal.MainLocationModal;

/**
 * Created by tv-1 on 07/02/18.
 */

public interface VendorMapMvpView extends MvpView {
    void vendorsDataPopulate(MainLocationModal mainLocationModal);

}
