package com.tv.GreenGrubBox.home;

import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;


/**
 * Created by admin on 19/01/18.
 */

public interface HomeMvpPresenter<V extends HomeMvpView,
        I extends HomeMvpInteractor> extends MvpPresenter<V, I> {

  //  void doLogoutApiCall(String mAuthToken);

}
