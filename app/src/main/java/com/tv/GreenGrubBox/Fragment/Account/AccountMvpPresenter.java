package com.tv.GreenGrubBox.Fragment.Account;

import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;

/**
 * Created by tv-1 on 01/02/18.
 */

public interface AccountMvpPresenter <V extends AccountMvpView,
        I extends AccountMvpInteractor> extends MvpPresenter<V, I> {
    void getUserDetails(boolean isProgress);
    void doLogoutApiCall(String mAuthToken);

}
