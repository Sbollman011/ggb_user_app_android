package com.tv.GreenGrubBox.login;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
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
 * Created by admin on 29/01/18.
 */

public class LoginPresenter<V extends com.tv.GreenGrubBox.login.LoginMvpView, I extends com.tv.GreenGrubBox.login.LoginMvpInteractor> extends
        BasePresenter<V, I> implements com.tv.GreenGrubBox.login.LoginMvpPresenter<V, I> {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    @Inject
    public LoginPresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }

    @Override
    public void doLogin(final String email, String password, DeviceInfo mDeviceInfo) {

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

        mLoginRequest.setPassword(password);
        mLoginRequest.setEmail(email);
        mLoginRequest.setDeviceInfo(mDeviceInfo);

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

                        if (mLoginResponse.getData().getIsRenewRequire() == 1) {
                            getMvpView().showMessage(mLoginResponse.getMessage());

                            getMvpView().MainScreen(mLoginResponse);

                            MyPreference.setUserData(mLoginResponse);
                            //getMvpView().showMessage("Login Successful");
                            MyPreference.saveUserAuthToken(mLoginResponse.getToken());
                           /* if (mLoginResponse.getData().getAccountType() == 1) {
                                //Individual
                                getMvpView().openIndividualScreen(mLoginResponse);
                            } else if (mLoginResponse.getData().getAccountType() == 2) {
                                //Corporate

                                getMvpView().openCorporateScreen(mLoginResponse);
                            }*/
                            return;
                        } else {
                            getMvpView().MainScreen(mLoginResponse);

                            MyPreference.setUserData(mLoginResponse);
                            //getMvpView().showMessage("Login Successful");
                            MyPreference.saveUserAuthToken(mLoginResponse.getToken());
                        }
                        /*if (!mLoginResponse.getData().isRegistered()) {
                            //getMvpView().showMessage(R.string.youhavenotcompletedyoutregistrationTxt);
                            if (mLoginResponse.getData().getAccountType() == 1) {
                                //Individual
                                getMvpView().openIndividualScreen(mLoginResponse);
                            } else if (mLoginResponse.getData().getAccountType() == 2) {
                                //Corporate
                                getMvpView().openCorporateScreen(mLoginResponse);
                            }

                        } else {
                            getMvpView().MainScreen(mLoginResponse);
                            MyPreference.setUserData(mLoginResponse);
                            //getMvpView().showMessage("Login Successful");
                            MyPreference.saveUserAuthToken(mLoginResponse.getToken());
                        }*/

                    } else if (mLoginResponse.getStatus() == 2) { // User is verified but registration is not completed.
                        if (mLoginResponse.getData().getAccountType() == 1) {
                            //Individual
                            getMvpView().openIndividualScreen(mLoginResponse);
                        } else if (mLoginResponse.getData().getAccountType() == 2) {
                            //Corporate

                            getMvpView().openCorporateScreen(mLoginResponse);
                        }
                    } else if (mLoginResponse.getStatus() == 3) { // otp is active but not used
                        getMvpView().showMessage(mLoginResponse.getMessage());

                        getMvpView().activateAccount(mLoginResponse, email);
                    } else if (mLoginResponse.getStatus() == 4) { // otp is not active and user is not verified


                        getMvpView().activateAccount(mLoginResponse, email);
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
