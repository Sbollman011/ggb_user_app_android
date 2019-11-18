package com.tv.GreenGrubBox.Fragment.Corporate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
import com.tv.GreenGrubBox.data.modal.SignUpDataUserRequest;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;
import com.tv.GreenGrubBox.data.modal.SignUpResponseDatum;
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

public class CorporatePresenter<V extends CorporateMvpView, I extends CorporateMvpInteractor> extends
        BasePresenter<V, I> implements CorporateMvpPresenter<V, I> {

    private static final String TAG = CorporatePresenter.class.getSimpleName();

    @Inject
    public CorporatePresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }

    @Override
    public void signUpCorporate(String firstName, String promoCode, SignUpResponseDatum mSignUpResponse, DeviceInfo mDeviceInfo) {

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
        mSignUpDateUserRequest.setUserId(mSignUpResponse.getId());
        mSignUpDateUserRequest.setAccountType("2");
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
}
