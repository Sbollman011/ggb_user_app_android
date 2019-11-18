package com.tv.GreenGrubBox.activites.activites.NewMainViewScreen;

import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;

/**
 * Created by tv-1 on 31/01/18.
 */

public interface NewMainViewMvpPresenter <V extends NewMainViewMvpView,
        I extends NewMainViewMvpInteractor> extends MvpPresenter<V, I> {


    void checkOut(String contents, String lat, String lng);


}
