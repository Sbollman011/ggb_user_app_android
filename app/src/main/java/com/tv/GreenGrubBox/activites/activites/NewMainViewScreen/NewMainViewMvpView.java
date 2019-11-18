package com.tv.GreenGrubBox.activites.activites.NewMainViewScreen;

import com.tv.GreenGrubBox.BaseClasses.MvpView;
import com.tv.GreenGrubBox.data.modal.CheckOutBoxResponse;

/**
 * Created by tv-1 on 31/01/18.
 */

public interface NewMainViewMvpView  extends MvpView{
    void handleCheckOutResponse(CheckOutBoxResponse mCheckOutBoxResponse);
}
