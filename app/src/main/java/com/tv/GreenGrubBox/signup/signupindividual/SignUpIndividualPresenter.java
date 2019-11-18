package com.tv.GreenGrubBox.signup.signupindividual;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.CardDetailModal;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.LoginRequest;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
import com.tv.GreenGrubBox.data.modal.PackageModalMain;
import com.tv.GreenGrubBox.data.modal.SignUpDataUserRequest;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.network.retrofit.ApiClient;
import com.tv.GreenGrubBox.home.MyPreference;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;
import com.tv.GreenGrubBox.utils.Logger;
import com.tv.GreenGrubBox.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tv-1 on 30/01/18.
 */

public class SignUpIndividualPresenter<V extends SignUpIndividualMvpView, I extends SignUpIndividualMvpInteractor> extends
        BasePresenter<V, I> implements SignUpIndividualMvpPresenter<V, I> {


    private static final String TAG = SignUpIndividualPresenter.class.getSimpleName();

    @Inject
    public SignUpIndividualPresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
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
    public void completeRegistration(String name, String id, CardDetailModal cardDetailModal, final SignUpResponse mSignUpResponseMain, DeviceInfo mDeviceInfo) {


        if (!getMvpView().isNetworkConnected()) {
            return;
        }

        getMvpView().showLoading();
        getMvpView().hideKeyboard();
        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);

        Gson gson = new Gson();
        String json = gson.toJson(cardDetailModal);
        Logger.logsInfo(TAG, "cardDetailModal =========" + json);
        Constant.encryptRSAToString(json, MyPreference.getCurrentUser().getRsaPublicKey());

        SignUpDataUserRequest mSignUpDataUserRequest = new SignUpDataUserRequest();
        mSignUpDataUserRequest.setName(name);
        mSignUpDataUserRequest.setPackageId(id);
//        mSignUpDataUserRequest.setCardToken(tokenId);
        mSignUpDataUserRequest.setCardDetail(MyPreference.getEncryptedKey());
        mSignUpDataUserRequest.setUserId(mSignUpResponseMain.getData().getId());

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

    @Override
    public void doLogin(String email, String password) {

//        getMvpView().openMainScreen();
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
        /*ApiHelper mApiHelper = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiHelper.class);*/
        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);

        LoginRequest mLoginRequest = new LoginRequest();
        // mLoginRequest.setDeviceId(deviceId);
        mLoginRequest.setPassword(password);
        mLoginRequest.setEmail(email);
        //  mLoginRequest.setAccountType(accountType);
        // mLoginRequest.setRegistration(registration);
        //  mLoginRequest.setDeviceInfo(mDeviceInfo);

        Gson gson = new GsonBuilder().create();
        Logger.logsError(TAG, "JSON requestModal : " + gson.toJson(mLoginRequest));
        mApiHelper.doLogin(mLoginRequest).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                getMvpView().hideLoading();
                if (response.code() == 200 || response.code() == 201) {

                    Logger.logsError(TAG, "response  Header auth-token : " + response.headers().get("auth-token"));

                    Gson mGson1 = new Gson();
                    LoginResponse mLoginResponse = mGson1.fromJson(response.body().toString().trim(),
                            LoginResponse.class);
                    if (mLoginResponse == null) {
                        getMvpView().showMessage(R.string.some_error);
                        return;
                    }
                    if (mLoginResponse.getStatus() == 1) {
                        getMvpView().MainScreen(mLoginResponse);
                        MyPreference.setUserData(mLoginResponse);
                        //getMvpView().showMessage("Login Successful");
                        MyPreference.saveUserAuthToken(mLoginResponse.getToken());
                    } else {
                        getMvpView().showMessage(mLoginResponse.getMessage());
                    }


                    Logger.logsError(TAG, "response  BODY : " + response.body().toString());

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
