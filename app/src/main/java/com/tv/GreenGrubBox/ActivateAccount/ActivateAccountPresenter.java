package com.tv.GreenGrubBox.ActivateAccount;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.ResendVerificationResponseModal;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;
import com.tv.GreenGrubBox.data.modal.VerifyOTPRequest;
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
 * Created by admin on 18/01/18.
 */

public class ActivateAccountPresenter<V extends ActivateAccountMvpView, I extends ActiviateAccountMvpInteractor> extends
        BasePresenter<V, I> implements ActivateAccountMvpPresenter<V, I> {

    private static final String TAG = ActivateAccountPresenter.class.getSimpleName();

    @Inject
    public ActivateAccountPresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);

    }

    @Override
    public void resendVerification(final String email) {


        if (getMvpView() == null) {
            return;
        }

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().onError(R.string.pleaseCheckInternetConnectionText);
            return;
        }

        getMvpView().showLoading();
        getMvpView().hideKeyboard();

        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);
        VerifyOTPRequest mVerifyOTPRequest = new VerifyOTPRequest();
        mVerifyOTPRequest.setEmail(email);

        Gson gson = new GsonBuilder().create();
        Logger.logsError(TAG, "JSON requestModal : " + gson.toJson(mVerifyOTPRequest));
        mApiHelper.resendOtp(mVerifyOTPRequest).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                getMvpView().hideLoading();
                if (response.code() == 200 || response.code() == 201) {

                    Gson mGson1 = new Gson();
                    ResendVerificationResponseModal modal = mGson1.fromJson(response.body().toString().trim(),
                            ResendVerificationResponseModal.class);

                    if (modal == null) {
                        getMvpView().showMessage(R.string.some_error);
                        return;
                    } else {
                        getMvpView().showMessage(modal.getMessage());
                    }


                    Logger.logsError(TAG, "response  BODY : " + response.body().toString());

                } else {
                    getMvpView().showMessage(R.string.some_error);
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
    public void verifyOtp(String userEmail, int otp) {

        if (getMvpView() == null) {
            return;
        }

        if (!getMvpView().isNetworkConnected()) {
            getMvpView().onError(R.string.pleaseCheckInternetConnectionText);
            return;
        }

        getMvpView().showLoading();
        getMvpView().hideKeyboard();

        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);
        VerifyOTPRequest verifyOTPRequest = new VerifyOTPRequest();
        verifyOTPRequest.setEmail(userEmail);
        verifyOTPRequest.setOtp(otp);


        Gson gson = new GsonBuilder().create();
        Logger.logsError(TAG, "JSON requestModal : " + gson.toJson(verifyOTPRequest));
        mApiHelper.verifyOtp(verifyOTPRequest).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                getMvpView().hideLoading();
                if (response.code() == 200 || response.code() == 201) {
                    Gson mGson1 = new Gson();
                    SignUpResponse signUpResponse = mGson1.fromJson(response.body().toString().trim(), SignUpResponse.class);

                    if (signUpResponse == null) {
                        getMvpView().showMessage(R.string.some_error);
                        return;
                    }


                    if(signUpResponse.getRsaPublicKey() != null){
                        MyPreference.setRSAKeyFromSever(signUpResponse.getRsaPublicKey());
                    }

                    getMvpView().responseVerifyOTP(signUpResponse);


                    Logger.logsError(TAG, "response  BODY : " + response.body().toString());

                } else {
                    getMvpView().showMessage(R.string.some_error);
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
//
//    @Override
//    public void verifyOtp(String email, String username, String socialId, int otp, DeviceInfo mDeviceInfo, String accountType,
//                          final LoginRequest loginRequest, final SignUpRequest signUpRequest) {
//
//
//        if (getMvpView() == null) {
//            return;
//        }
//
//        if (!getMvpView().isNetworkConnected()) {
//            getMvpView().onError(R.string.pleaseCheckInternetConnectionText);
//            return;
//        }
//
//        getMvpView().showLoading();
//        getMvpView().hideKeyboard();
//
//        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);
//        VerifyOTPRequest verifyOTPRequest = new VerifyOTPRequest();
//        verifyOTPRequest.setEmail(email);
//        verifyOTPRequest.setUsername(username);
//        verifyOTPRequest.setOtp(otp);
//        verifyOTPRequest.setAccountType(accountType);
//        verifyOTPRequest.setDeviceInfo(mDeviceInfo);
//        verifyOTPRequest.setSocialId(socialId);
//
//
//        Gson gson = new GsonBuilder().create();
//        Logger.logsError(TAG, "JSON requestModal : " + gson.toJson(verifyOTPRequest));
//        mApiHelper.verifyOTP(verifyOTPRequest).enqueue(new Callback<JsonElement>() {
//            @Override
//            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
//                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
//                getMvpView().hideLoading();
//                if (response.code() == 200 || response.code() == 201) {
//                    Gson mGson1 = new Gson();
//                    LoginResponse mLoginResponse = mGson1.fromJson(response.body().toString().trim(), LoginResponse.class);
//
//                    if (mLoginResponse == null) {
//                        getMvpView().showMessage(R.string.some_error);
//                        return;
//                    }
//
//                    getMvpView().responseVerifyOTP(mLoginResponse,loginRequest,signUpRequest);
//
//                    Logger.logsError(TAG, "response  BODY : " + response.body().toString());
//
//                } else {
//                    getMvpView().showMessage(R.string.some_error);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonElement> call, Throwable t) {
//                Logger.logsError(TAG, "onFailure Called");
//                t.printStackTrace();
//                getMvpView().hideLoading();
//                getMvpView().onError(R.string.some_error);
//            }
//        });
//    }
