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

package com.tv.GreenGrubBox.data.network;

import com.google.gson.JsonElement;
import com.tv.GreenGrubBox.data.modal.CheckBoxRequest;
import com.tv.GreenGrubBox.data.modal.ForgotPasswordRequestModal;
import com.tv.GreenGrubBox.data.modal.LoginRequest;
import com.tv.GreenGrubBox.data.modal.SignUpDataUserRequest;
import com.tv.GreenGrubBox.data.modal.SignUpRequest;
import com.tv.GreenGrubBox.data.modal.ValidateVersionRequestModal;
import com.tv.GreenGrubBox.data.modal.VerifyOTPRequest;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;

@Singleton
public class AppApiHelper implements ApiHelper {

    private static final String TAG = AppApiHelper.class.getSimpleName();
    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Call<JsonElement> doLogin(LoginRequest request) {
        return null;
    }

    @Override
    public Call<JsonElement> signUp(SignUpRequest request) {
        return null;
    }

    @Override
    public Call<JsonElement> boxCheckout(CheckBoxRequest request, String mAuth) {
        return null;
    }

    @Override
    public Call<JsonElement> completeRegistration(SignUpDataUserRequest request) {
        return null;
    }

    @Override
    public Call<JsonElement> doLogoutApiCall(String mAuthorization) {
        return null;
    }

    @Override
    public Call<JsonElement> getUserDetails(String mAuthorization, String _id) {
        return null;
    }

    @Override
    public Call<JsonElement> forgotPassword(ForgotPasswordRequestModal forgotPasswordModal) {
        return null;
    }

    @Override
    public Call<JsonElement> validateAppVersion(ValidateVersionRequestModal validateVersionRequestModal) {
        return null;
    }

    @Override
    public Call<JsonElement> verifyOtp(VerifyOTPRequest verifyOTPRequest) {
        return null;
    }

    @Override
    public Call<JsonElement> resendOtp(VerifyOTPRequest verifyOTPRequest) {
        return null;
    }

    @Override
    public Call<JsonElement> packages() {
        return null;
    }

    @Override
    public Call<JsonElement> getUserHistory(String userId, String mAuthorization, int mCurrentPage, int mPageSize) {
        return null;
    }

    @Override
    public Call<JsonElement> getVendors(long mCurrentPage, long mPageSize, String mSearch, String mLat, String mLong, String mType, String mAuthorization) {
        return null;
    }

   /* @Override
    public Call<JsonElement> getEventCategories() {
        return null;
    }*/



}

