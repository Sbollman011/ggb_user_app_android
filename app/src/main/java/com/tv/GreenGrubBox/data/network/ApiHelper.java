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

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

@Singleton
public interface ApiHelper {

    ApiHeader getApiHeader();

   /* @GET(ApiEndPoint.GET_EVENT_CATEGORIES)
    Call<JsonElement> getEventCategories();*/

    @Headers("Content-Type: application/json")
    @POST(ApiEndPoint.LOGIN)
    Call<JsonElement> doLogin(@Body LoginRequest request);

    @Headers("Content-Type: application/json")
    @POST(ApiEndPoint.SIGN_UP)
    Call<JsonElement> signUp(@Body SignUpRequest request
    );

    @Headers("Content-Type: application/json")
    @POST(ApiEndPoint.BOX_CHECKOUT)
    Call<JsonElement> boxCheckout(@Body CheckBoxRequest request, @Header("Authorization") String mAuth
    );

    @Headers("Content-Type: application/json")
    @PUT(ApiEndPoint.COMPLETE_REG)
    Call<JsonElement> completeRegistration(@Body SignUpDataUserRequest request
    );


    @GET(ApiEndPoint.LOGOUT)
    Call<JsonElement> doLogoutApiCall(@Header("Authorization") String mAuthorization);


    @GET(ApiEndPoint.GET_USER_DETAILS)
    Call<JsonElement> getUserDetails(@Header("Authorization") String mAuthorization, @Query("id") String id);


    @POST(ApiEndPoint.FORGOT_PASSWORD)
    Call<JsonElement> forgotPassword(@Body ForgotPasswordRequestModal forgotPasswordModal);


    @POST(ApiEndPoint.VALIDATE_APP_VERSION)
    Call<JsonElement> validateAppVersion(@Body ValidateVersionRequestModal validateVersionRequestModal);

    @POST(ApiEndPoint.VERIFY_OTP)
    Call<JsonElement> verifyOtp(@Body VerifyOTPRequest verifyOTPRequest);


    @POST(ApiEndPoint.RESEND_OTP)
    Call<JsonElement> resendOtp(@Body VerifyOTPRequest verifyOTPRequest);


    @Headers("Content-Type: application/json")
    @GET(ApiEndPoint.PACKAGES)
    Call<JsonElement> packages();


    @Headers("Content-Type: application/json")
    @GET(ApiEndPoint.GET_USER_HISTORY)
    Call<JsonElement> getUserHistory(@Query("userId") String userId,
                                     @Header("authorization") String mAuthorization,
                                     @Query("currentPage") int mCurrentPage,
                                     @Query("pageSize") int mPageSize);


    @Headers("Content-Type: application/json")
    @GET(ApiEndPoint.GET_VENDORS)
    Call<JsonElement> getVendors(@Query("currentPage") long mCurrentPage,
                                 @Query("pageSize") long mPageSize,
                                 @Query("search") String mSearch,
                                 @Query("lat") String mLat,
                                 @Query("long") String mLong,
                                 @Query("type") String mType,
                                 @Header("authorization") String mAuthorization);

}
