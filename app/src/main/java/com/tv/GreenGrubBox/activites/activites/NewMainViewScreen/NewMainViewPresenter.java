package com.tv.GreenGrubBox.activites.activites.NewMainViewScreen;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.CheckBoxRequest;
import com.tv.GreenGrubBox.data.modal.CheckOutBoxResponse;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.network.retrofit.ApiClient;
import com.tv.GreenGrubBox.home.MyPreference;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Logger;
import com.tv.GreenGrubBox.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tv-1 on 31/01/18.
 */

public class NewMainViewPresenter<V extends NewMainViewMvpView, I extends NewMainViewMvpInteractor> extends
        BasePresenter<V, I> implements NewMainViewMvpPresenter<V, I> {


    private static final String TAG = NewMainViewPresenter.class.getSimpleName();

    @Inject
    public NewMainViewPresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }

    @Override
    public void checkOut(String contents, String lat, String lng) {


        if (!getMvpView().isNetworkConnected()) {
            return;
        }

        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);

        final CheckBoxRequest mCheckBoxRequest = new CheckBoxRequest();
        mCheckBoxRequest.setUserId(MyPreference.getCurrentUser().getData().getId());
        mCheckBoxRequest.setBoxId(contents);
        mCheckBoxRequest.setLat(lat);
        mCheckBoxRequest.setLng(lng);
        mCheckBoxRequest.setTimezone(CommonUtils.getTimeZone());


        Gson gson = new GsonBuilder().create();
        Logger.logsError(TAG, "JSON requestModal : " + gson.toJson(mCheckBoxRequest));
        mApiHelper.boxCheckout(mCheckBoxRequest, MyPreference.getUserAuthToken()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, final Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());


                getMvpView().hideLoading();

                if (response.code() == 200 || response.code() == 201) {


                    Logger.logsError(TAG, "response  BODY : " + response.body().toString());

                    Gson mGson1 = new Gson();
                    CheckOutBoxResponse mCheckOutBoxResponse = mGson1.fromJson(response.body().toString(), CheckOutBoxResponse.class);
                    getMvpView().handleCheckOutResponse(mCheckOutBoxResponse);

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
