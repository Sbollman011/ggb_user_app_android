package com.tv.GreenGrubBox.launch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.LoginRequest;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
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
 * Created by admin on 16/01/18.
 */

public class LaunchPresenter <V extends LaunchMvpView, I extends LaunchMvpInteractor> extends
        BasePresenter<V, I> implements LaunchMvpPresenter<V, I> {

    private static final String TAG = LaunchPresenter.class.getSimpleName();

    @Inject
    public LaunchPresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }

    @Override
    public void doLogin(String email, String password, String deviceId, String accountType, String registration) {
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
       /* mLoginRequest.setDeviceId(deviceId);
        mLoginRequest.setPassword(password);
        mLoginRequest.setUsername(email);
        mLoginRequest.setAccountType(accountType);
        mLoginRequest.setRegistration(registration);*/

        Gson gson = new GsonBuilder().create();
        Logger.logsError(TAG, "JSON requestModal : " + gson.toJson(mLoginRequest));
        mApiHelper.doLogin(mLoginRequest).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                getMvpView().hideLoading();
                if (response.code() == 200) {
                    Logger.logsError(TAG, "response  Header auth-token : " + response.headers().get("auth-token"));

                    Gson mGson1  = new Gson();
                    LoginResponse mLoginResponse = mGson1.fromJson(response.body().toString().trim(),
                            LoginResponse.class);
                    MyPreference.setUserData(mLoginResponse);

                    getMvpView().showMessage("login successful");
                    MyPreference.saveUserAuthToken(mLoginResponse.getToken());
                    Logger.logsError(TAG,"response  BODY : "  + response.body().toString());
                   // getMvpView().openHomeScreen();

                } else {
                     getMvpView().onError(R.string.loginFailText);
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
    public void doSocialLogin(final LoginRequest mLoginRequest) {

        if (!getMvpView().isNetworkConnected()) {
            return;
        }
        getMvpView().showLoading();

        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);
        mApiHelper.doLogin(mLoginRequest).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                getMvpView().hideLoading();
                if (response.code() == 200) {
                    Logger.logsError(TAG, "response  Header auth-token : " + response.headers().get("auth-token"));
                    getMvpView().showMessage("login successful");
                   // getMvpView().openHomeScreen();
                } else if (response.code()==401){
                    //401 UnAuth
                    getMvpView().openRegScreen(mLoginRequest);
                }else {
                    getMvpView().onError(R.string.loginFailText);
                }}

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Logger.logsError(TAG, "onFailure Called");
                t.printStackTrace();
                getMvpView().hideLoading();
                getMvpView().onError(R.string.some_error);

            }});
    }}
