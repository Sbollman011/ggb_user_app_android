/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.tv.GreenGrubBox;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AlertDialog;

import com.facebook.FacebookSdk;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.data.modal.UserDetailModal;
import com.tv.GreenGrubBox.data.modal.ValidateVersionRequestModal;
import com.tv.GreenGrubBox.data.modal.ValidateVersionResponseModal;
import com.tv.GreenGrubBox.data.network.ApiHelper;
import com.tv.GreenGrubBox.data.network.retrofit.ApiClient;
import com.tv.GreenGrubBox.di.component.ApplicationComponent;
import com.tv.GreenGrubBox.di.component.DaggerApplicationComponent;
import com.tv.GreenGrubBox.di.module.ApplicationModule;
import com.tv.GreenGrubBox.home.MyPreference;
import com.tv.GreenGrubBox.utils.Logger;
import com.tv.GreenGrubBox.utils.NetworkUtils;
import com.twitter.sdk.android.core.Twitter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by janisharali on 27/01/17.
 */


public class MvpApp extends MultiDexApplication {
    public static final String PACKAGE_NAME = MvpApp.class.getPackage().getName();
    private static final String TAG = MvpApp.class.getSimpleName();
    private static MvpApp mInstance;
    private ApplicationComponent mApplicationComponent;

    public static synchronized MvpApp getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        MultiDex.install(getApplicationContext());

        super.onCreate();


        /**Noughat Camera fix*/

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        mInstance = this;
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);
//        FacebookSdk.sdkInitialize(getApplicationContext());
        Twitter.initialize(this);



/*
        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(Level.BODY);
        }

        CalligraphyConfig.initDefault(mCalligraphyConfig);*/


    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
