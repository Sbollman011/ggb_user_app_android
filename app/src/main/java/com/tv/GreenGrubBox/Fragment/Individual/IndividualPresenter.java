package com.tv.GreenGrubBox.Fragment.Individual;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.CardDetailModal;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
import com.tv.GreenGrubBox.data.modal.PackageModalMain;
import com.tv.GreenGrubBox.data.modal.SignUpDataUserRequest;
import com.tv.GreenGrubBox.data.modal.SignUpResponseDatum;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.network.retrofit.ApiClient;
import com.tv.GreenGrubBox.home.MyPreference;
import com.tv.GreenGrubBox.utils.Constant;
import com.tv.GreenGrubBox.utils.Logger;
import com.tv.GreenGrubBox.utils.rx.SchedulerProvider;

import java.security.GeneralSecurityException;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndividualPresenter<V extends IndividualMvpView, I extends IndividualMvpInteractor> extends
        BasePresenter<V, I> implements IndividualMvpPresenter<V, I> {

    private static final String TAG = IndividualPresenter.class.getSimpleName();

    @Inject
    public IndividualPresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getAllPackages() {


        if (!getMvpView().isNetworkConnected()) {
            return;
        }

        getMvpView().showLoading();
        getMvpView().hideKeyboard();
        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);

        mApiHelper.packages().enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                getMvpView().hideLoading();
                if (response.code() == 200) {

                    Gson mGson = new Gson();
                    PackageModalMain mPackageModalMain = mGson.fromJson(response.body().toString(), PackageModalMain.class);
                    getMvpView().populatePackageData(mPackageModalMain);

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

    @Override
    public void completeRegistration(String name, String id, CardDetailModal cardDetailModal, SignUpResponseDatum mSignUpResponseMain, DeviceInfo mDeviceInfo) {


        if (!getMvpView().isNetworkConnected()) {
            return;
        }

        getMvpView().showLoading();
        getMvpView().hideKeyboard();
        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(cardDetailModal);
        Constant.encryptRSAToString(json, MyPreference.getRSAKeyFromServer());

        SignUpDataUserRequest mSignUpDataUserRequest = new SignUpDataUserRequest();
        mSignUpDataUserRequest.setName(name);
        mSignUpDataUserRequest.setPackageId(id);
        mSignUpDataUserRequest.setAccountType("1");
//        mSignUpDataUserRequest.setCardToken(tokenId);
        mSignUpDataUserRequest.setCardDetail(MyPreference.getEncryptedKey());
        mSignUpDataUserRequest.setUserId(mSignUpResponseMain.getId());

        mSignUpDataUserRequest.setDeviceInfo(mDeviceInfo);


        mApiHelper.completeRegistration(mSignUpDataUserRequest).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                getMvpView().hideLoading();
                if (response.code() == 200) {
                  /*  Gson mGson  = new Gson();
                    SignUpResponse mSignUpResponse = mGson.fromJson(response.body().toString(),SignUpResponse.class);

                    mSignUpResponse.setEmail(mSignUpResponseMain.getEmail());
                    mSignUpResponse.setPassword(mSignUpResponseMain.getPassword());


                    getMvpView().openHomeScreen(mSignUpResponse);*/

                    Gson mGson1 = new Gson();
                    LoginResponse mLoginResponse = mGson1.fromJson(response.body().toString().trim(),
                            LoginResponse.class);
                    if (mLoginResponse == null) {
                        getMvpView().showMessage(R.string.some_error);
                        return;
                    }
                    if (mLoginResponse.getStatus() == 1) {

                        MyPreference.setUserData(mLoginResponse);
                        //getMvpView().showMessage("Login Successful");
                        MyPreference.saveUserAuthToken(mLoginResponse.getToken());
                        getMvpView().MainScreen(mLoginResponse);
                    } else {
                        getMvpView().showMessage(mLoginResponse.getMessage());
                    }


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