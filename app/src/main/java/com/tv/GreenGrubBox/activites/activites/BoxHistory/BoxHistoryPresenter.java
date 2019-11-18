package com.tv.GreenGrubBox.activites.activites.BoxHistory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.BoxHistoryModal;
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
 * Created by admin on 01/02/18.
 */

public class BoxHistoryPresenter <V extends BoxHistoryMvpView, I extends BoxHistoryMvpInteractor> extends
        BasePresenter<V, I> implements BoxHistoryMvpPresenter<V, I> {

    private static final String TAG = BoxHistoryPresenter.class.getSimpleName();

    @Inject
    public BoxHistoryPresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getAllHistory(final boolean isProgress, int mCurrentPage, int mPageSize) {


        if (!getMvpView().isNetworkConnected()) {
            return;
        }

        if (isProgress) {
            getMvpView().showLoading();
        }

        getMvpView().hideKeyboard();
        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);

        mApiHelper.getUserHistory(MyPreference.getCurrentUser().getData().getId(), MyPreference.getUserAuthToken(),
                mCurrentPage, mPageSize).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                if (isProgress) {
                    getMvpView().hideLoading();
                }

                if (response.code() == 200) {

                    Gson gson = new Gson();
                    BoxHistoryModal mBoxHistoryModal = gson.fromJson(response.body().toString(), BoxHistoryModal.class);
                    getMvpView().populateHistoryData(mBoxHistoryModal);

                } else {
                    getMvpView().onError(R.string.some_error);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Logger.logsError(TAG, "onFailure Called");
                t.printStackTrace();
                if (isProgress) {
                    getMvpView().hideLoading();
                }
                getMvpView().onError(R.string.some_error);
            }
        });
    }
}
