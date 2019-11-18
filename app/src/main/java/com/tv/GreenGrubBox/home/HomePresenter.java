package com.tv.GreenGrubBox.home;

import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by admin on 19/01/18.
 */

public class HomePresenter<V extends HomeMvpView, I extends HomeMvpInteractor> extends
        BasePresenter<V, I> implements HomeMvpPresenter<V, I> {

    private static final String TAG =HomePresenter.class.getSimpleName();

    @Inject
    public HomePresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }

   /* @Override
    public void doLogoutApiCall(String mAuthToken) {
        if (!getMvpView().isNetworkConnected()) {
            return;
        }

        getMvpView().showLoading();
        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);
        mApiHelper.doLogoutApiCall("Basic " + mAuthToken).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                getMvpView().hideLoading();
                if (response.code() == 200) {

               //     getMvpView().logoutSuccess();

                   *//* Gson mGson = new Gson();
                    LogoutUserResponse mLogoutUserResponse = mGson.fromJson(response.body().toString(), LogoutUserResponse.class);
                    if (mLogoutUserResponse.getLogout().equalsIgnoreCase("success")) {
                        getMvpView().logoutSuccess();
                    }*//*

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
*/
}
