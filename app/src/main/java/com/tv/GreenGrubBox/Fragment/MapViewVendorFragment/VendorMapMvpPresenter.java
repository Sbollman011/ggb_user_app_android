package com.tv.GreenGrubBox.Fragment.MapViewVendorFragment;

import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;

/**
 * Created by tv-1 on 07/02/18.
 */

public interface VendorMapMvpPresenter <V extends VendorMapMvpView,
        I extends VendorMapMvpInteractor> extends MvpPresenter<V, I> {

    void getVendors(boolean isProgress,long mCurrentPage,long mPageSize,String mSearch, String mLat,String mLong ,String mType );

}
