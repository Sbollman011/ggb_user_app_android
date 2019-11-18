package com.tv.GreenGrubBox.Fragment.Account;

import com.tv.GreenGrubBox.BaseClasses.MvpView;
import com.tv.GreenGrubBox.data.modal.UserDetailModal;

/**
 * Created by tv-1 on 01/02/18.
 */

public interface AccountMvpView extends MvpView {
    void userDetailsResponse(UserDetailModal mUserDetailModal);
    void logoutSuccess();
    void logoutUser();
}
