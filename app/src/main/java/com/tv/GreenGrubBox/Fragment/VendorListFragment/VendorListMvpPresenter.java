package com.tv.GreenGrubBox.Fragment.VendorListFragment;

import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;

/**
 * Created by user on 7/2/18.
 */

public interface VendorListMvpPresenter<V extends VendorListMvpView,
        I extends VendorListMvpInteractor> extends MvpPresenter<V, I> {

    void getVendors(boolean isProgress, long mCurrentPage, long mPageSize, String mSearch, String mLat, String mLong, String mType);
}
