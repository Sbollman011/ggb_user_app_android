package com.tv.GreenGrubBox.Fragment.VendorListFragment;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.MainLocationModal;
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
 * Created by user on 7/2/18.
 */

public class VendorListPresenter<V extends VendorListMvpView, I extends VendorListMvpInteractor> extends
        BasePresenter<V, I> implements VendorListMvpPresenter<V, I> {

    private static final String TAG = VendorListPresenter.class.getSimpleName();

    @Inject
    public VendorListPresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getVendors(final boolean isProgress ,long mCurrentPage, long mPageSize, String mSearch, String mLat, String mLong, String mType) {
        if (!getMvpView().isNetworkConnected()) {
            return;
        }

        if (isProgress) {
            getMvpView().showLoading();
        }
        ApiHelper mApiHelper = ApiClient.getClient().create(ApiHelper.class);
        mApiHelper.getVendors(mCurrentPage, mPageSize, mSearch, mLat, mLong, mType, MyPreference.getUserAuthToken()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Logger.logsError(TAG, "onResponse Called : " + response.toString() + " \n call : " + call.request() + "\n " +
                        response.body() + "\n " + response.errorBody() + "\n " + response.code());
                if (isProgress) {
                    getMvpView().hideLoading();
                }

                if (response.code() == 200) {
                    Logger.logsError(TAG, "response body " + response.body().toString());
                    Gson mGson = new Gson();
                    MainLocationModal mainLocationModal = mGson.fromJson(response.body().toString(),MainLocationModal.class);
/*
                    Gson mGson = new Gson();
                    Type listType = new TypeToken<List<MainLocationDatum>>() {

                    }.getType();
                    List<MainLocationDatum> mMainLocationDatumLIst = (List<MainLocationDatum>) mGson.fromJson(response.body().toString(), listType);
                    //     MainLocationDatum mMainLocationDatum = mGson.fromJson(response.body().toString(), MainLocationDatum.class);*/

                    getMvpView().vendorsDataPopulate(mainLocationModal);

                } else if (response.code() == 401) {
                    getMvpView().showMessage(R.string.sessionExpireText);
                    getMvpView().openActivityOnTokenExpire();
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
