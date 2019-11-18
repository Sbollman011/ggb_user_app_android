package com.tv.GreenGrubBox.signup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.SignUpRequest;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.network.retrofit.ApiClient;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Logger;
import com.tv.GreenGrubBox.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tv-1 on 29/01/18.
 */

public class SignUpPresenter <V extends SignUpMvpView, I extends SignUpMvpInteractor> extends
        BasePresenter<V, I> implements SignUpMvpPresenter<V, I> {


    private static final String TAG = SignUpPresenter.class.getSimpleName();

    @Inject
    public SignUpPresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }

    @Override
    public void signUp(final String email, final String password, int mAccountType, DeviceInfo mDeviceInfo) {
        //validate email and password
        if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
            return;
        }
        if (!CommonUtils.isEmailValid(email)) {
            getMvpView().onError(R.string.invalid_email);
            return;
        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        }

        if (!getMvpView().isNetworkConnected()) {
            return;
        }

        getMvpView().showLoading();
        getMvpView().hideKeyboard();
        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);

        SignUpRequest mSignUpRequest = new SignUpRequest();

        mSignUpRequest.setPassword(password);
        mSignUpRequest.setEmail(email);
        mSignUpRequest.setAccountType(mAccountType);
        mSignUpRequest.setDeviceInfo(mDeviceInfo);


        Gson gson = new GsonBuilder().create();
        Logger.logsError(TAG, "JSON requestModal : " + gson.toJson(mSignUpRequest));
        mApiHelper.signUp(mSignUpRequest).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                getMvpView().hideLoading();
                if (response.code() == 200) {

                    Gson mGson1 = new Gson();
                    SignUpResponse mSignUpResponse = mGson1.fromJson(response.body().toString(),SignUpResponse.class);
                    mSignUpResponse.setEmail(email);
                    mSignUpResponse.setPassword(password);
                    getMvpView().signUpResponse (mSignUpResponse,email);


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
