package com.tv.GreenGrubBox.Fragment.Account;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.UserDetailModal;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.network.retrofit.ApiClient;
import com.tv.GreenGrubBox.home.MyPreference;
import com.tv.GreenGrubBox.utils.Logger;
import com.tv.GreenGrubBox.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tv-1 on 01/02/18.
 */

public class AccountPresenter<V extends AccountMvpView, I extends AccountMvpInteractor> extends
        BasePresenter<V, I> implements AccountMvpPresenter<V, I> {

    private static final String TAG = AccountPresenter.class.getSimpleName();

    @Inject
    public AccountPresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getUserDetails(final boolean isProgress) {


        if (!getMvpView().isNetworkConnected()) {
            return;
        }

        if (isProgress) {
            getMvpView().showLoading();
        }

        getMvpView().hideKeyboard();
        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);


        mApiHelper.getUserDetails(MyPreference.getUserAuthToken(), MyPreference.getCurrentUser().getData().getId()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                if (isProgress) {
                    getMvpView().hideLoading();
                }

                if (response.code() == 200) {

                    Gson mGson = new Gson();
                    UserDetailModal mUserDetailModal = mGson.fromJson(response.body().toString(), UserDetailModal.class);
                    getMvpView().userDetailsResponse(mUserDetailModal);


                } else if (response.code() == 419) {
                    getMvpView().showMessage(R.string.userNotFoundText);
                    getMvpView().logoutUser();
                } else {
                    getMvpView().onError(R.string.some_error);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Logger.logsError(TAG, "onFailure Called");
                t.printStackTrace();
                if (isProgress) {
                    getMvpView().hideLoading();
                }
                getMvpView().onError(R.string.some_error);
            }
        });
    }

    @Override
    public void doLogoutApiCall(String mAuthToken) {
        if (!getMvpView().isNetworkConnected()) {
            return;
        }

        getMvpView().showLoading();
        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);
        mApiHelper.doLogoutApiCall(mAuthToken).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                getMvpView().hideLoading();
                if (response.code() == 200) {

                    getMvpView().logoutSuccess();

                   /* Gson mGson = new Gson();
                    LogoutUserResponse mLogoutUserResponse = mGson.fromJson(response.body().toString(), LogoutUserResponse.class);
                    if (mLogoutUserResponse.getLogout().equalsIgnoreCase("success")) {
                        getMvpView().logoutSuccess();
                    }*/

                } else if (response.code() == 401) {
                    getMvpView().showMessage(R.string.sessionExpireText);
                    getMvpView().openActivityOnTokenExpire();
                } else {
                    getMvpView().onError(R.string.some_error);

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Logger.logsError(TAG, "onFailure Called");
                t.printStackTrace();
                getMvpView().hideLoading();
                getMvpView().onError(R.string.some_error);

            }
        });
    }
}
