package com.tv.GreenGrubBox.signup.signupcorporate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.LoginRequest;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
import com.tv.GreenGrubBox.data.modal.SignUpDataUserRequest;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;
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
 * Created by admin on 30/01/18.
 */

public class SignUpCorporatePresenter <V extends SignUpCorporateMvpView, I extends SignUpCorporateMvpInteractor> extends
        BasePresenter<V, I> implements SignUpCorporateMvpPresenter<V, I> {


    private static final String TAG = SignUpCorporatePresenter.class.getSimpleName();

    @Inject
    public SignUpCorporatePresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }

    @Override
    public void signUpCorporate(String firstName, String promoCode, final SignUpResponse mSignUpResponse, DeviceInfo mDeviceInfo) {
//        getMvpView().openMainScreen();
        //validate email and password
        if (firstName == null || firstName.isEmpty()) {
            getMvpView().onError(R.string.empty_name);
            return;
        }

        if (promoCode == null || promoCode.isEmpty()) {
            getMvpView().onError(R.string.empty_promocode);
            return;
        }

        if (!getMvpView().isNetworkConnected()) {
            return;
        }

        getMvpView().showLoading();
        getMvpView().hideKeyboard();

        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);

        SignUpDataUserRequest mSignUpDateUserRequest = new SignUpDataUserRequest();
        mSignUpDateUserRequest.setName(firstName);
        mSignUpDateUserRequest.setPromoCode(promoCode);
        mSignUpDateUserRequest.setUserId(mSignUpResponse.getData().getId());

        mSignUpDateUserRequest.setDeviceInfo(mDeviceInfo);

        Gson gson = new GsonBuilder().create();
        Logger.logsError(TAG, "JSON requestModal : " + gson.toJson(mSignUpDateUserRequest));
        mApiHelper.completeRegistration(mSignUpDateUserRequest).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                getMvpView().hideLoading();
                if (response.code() == 200) {
                    Logger.logsError(TAG, "response  Header auth-token : " + response.headers().get("auth-token"));

                  /*  Gson mGson1  = new Gson();
                    SignUpResponse mSignUpDataUserRequest = mGson1.fromJson(response.body().toString().trim(),
                            SignUpResponse.class);


                    mSignUpDataUserRequest.setEmail(mSignUpResponse.getEmail());
                    mSignUpDataUserRequest.setPassword(mSignUpResponse.getPassword());
                    getMvpView().openNewScreen(mSignUpDataUserRequest);*/

                    Gson mGson1 = new Gson();
                    LoginResponse mLoginResponse = mGson1.fromJson(response.body().toString().trim(),
                            LoginResponse.class);
                    if (mLoginResponse == null) {
                        getMvpView().showMessage(R.string.some_error);
                        return;
                    }
                    if (mLoginResponse.getStatus()==1){

                        MyPreference.setUserData(mLoginResponse);
                        //getMvpView().showMessage("Login Successful");
                        MyPreference.saveUserAuthToken(mLoginResponse.getToken());
                        getMvpView().MainScreen(mLoginResponse);
                    }else{
                        getMvpView().showMessage(mLoginResponse.getMessage());
                    }

                    Logger.logsError(TAG,"response  BODY : "  + response.body().toString());


                } else {
                    getMvpView().onError(R.string.signupFailText);
                }}

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
                    if (mLoginResponse.getStatus()==1){
                        getMvpView().MainScreen(mLoginResponse);
                        MyPreference.setUserData(mLoginResponse);
                        //getMvpView().showMessage("Login Successful");
                        MyPreference.saveUserAuthToken(mLoginResponse.getToken());
                    }else{
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
