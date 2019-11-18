package com.tv.GreenGrubBox.activites.activites.BoxHistory;

import com.tv.GreenGrubBox.BaseClasses.MvpPresenter;

/**
 * Created by admin on 01/02/18.
 */

public interface BoxHistoryMvpPresenter  <V extends BoxHistoryMvpView,
        I extends BoxHistoryMvpInteractor> extends MvpPresenter<V, I> {


    void getAllHistory(boolean isProgress, int mCurrentPage,int mPageSize);
}
