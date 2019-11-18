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

public final class ApiEndPoint {

   // public static final String BASE_URL = "http://192.168.1.2:5000/";// Development Local
   // public static final String BASE_URL = "http://35.160.200.41:5000/";// Development Server
    public static final String BASE_URL = "https://api.greengrubbox.com/";// Live Server
//    public static final String BASE_URL = "http://34.219.124.227:5000/";// Staging Server
    public static final String BASE_URL_GOOGLE = "https://maps.googleapis.com/maps/api/";

    //public static final String LOGIN = "authentication/login";
    public static final String LOGIN = "/v1/user/signIn";
    public static final String LOGOUT = "user/logout";
    public static final String COMPLETE_REG = "/v1/user/completeRegistration";
    public static final String PACKAGES = "packages";
    public static final String SIGN_UP = "/v1/user/signUp";
    public static final String BOX_CHECKOUT = "user/box-checkOut";
    public static final String GET_USER_DETAILS = "user";
    public static final String VALIDATE_APP_VERSION = "validateAppVersion";
    public static final String GET_USER_HISTORY = "user/history";
    public static final String GET_VENDORS = "user/vendors";
    public static final String FORGOT_PASSWORD = "user/forgot-password";
    public static final String VERIFY_OTP = "user/verifyOTP";
    public static final String RESEND_OTP = "user/resendOTP";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }
}