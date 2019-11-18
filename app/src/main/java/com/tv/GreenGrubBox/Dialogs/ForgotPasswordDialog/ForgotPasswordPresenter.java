package com.tv.GreenGrubBox.Dialogs.ForgotPasswordDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.ForgotPasswordRequestModal;
import com.tv.GreenGrubBox.data.modal.ForgotPasswordResponceModal;
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
 * Created by user on 12/2/18.
 */

public class ForgotPasswordPresenter<V extends ForgotPasswordMvpView, I extends ForgotPasswordMvpInteractor> extends
        BasePresenter<V, I> implements ForgotPasswordMvpPresenter<V, I> {

    private static final String TAG = ForgotPasswordPresenter.class.getSimpleName();

    @Inject
    public ForgotPasswordPresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }

    @Override
    public void doForgotPassword(String email) {

        if (email == null || email.isEmpty()) {
            getMvpView().showMessage(R.string.empty_email);
            return;
        }
        if (!CommonUtils.isEmailValid(email)) {
            getMvpView().showMessage(R.string.invalid_email);
            return;
        }

        if (!getMvpView().isNetworkConnected()) {
            return;
        }

        getMvpView().showLoading();
        getMvpView().hideKeyboard();

        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);
        ForgotPasswordRequestModal forgotPasswordModal = new ForgotPasswordRequestModal();
        forgotPasswordModal.setEmail(email);

        Gson gson = new GsonBuilder().create();
        Logger.logsError(TAG, "JSON requestModal : " + gson.toJson(forgotPasswordModal));
        mApiHelper.forgotPassword(forgotPasswordModal).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                getMvpView().hideLoading();
                if (response.code() == 200 || response.code() == 201) {

                    Gson mGson1 = new Gson();
                    ForgotPasswordResponceModal modal = mGson1.fromJson(response.body().toString().trim(),
                            ForgotPasswordResponceModal.class);



                    if (modal == null) {
                        getMvpView().showMessage(R.string.some_error);
                        return;
                    }



                    getMvpView().closeDialog(modal);

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
