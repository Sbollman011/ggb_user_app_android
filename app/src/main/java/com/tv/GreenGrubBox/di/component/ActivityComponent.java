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

package com.tv.GreenGrubBox.di.component;

import com.tv.GreenGrubBox.ActivateAccount.ActivateAccountActivity;
import com.tv.GreenGrubBox.ActivateAccount.ActivateAccountDialog;
import com.tv.GreenGrubBox.Dialogs.ForgotPasswordDialog.ForgotPasswordDialog;
import com.tv.GreenGrubBox.Fragment.Account.AccountFragment;
import com.tv.GreenGrubBox.Fragment.Corporate.CorporateFragment;
import com.tv.GreenGrubBox.Fragment.Individual.IndividualFragment;
import com.tv.GreenGrubBox.Fragment.MapViewVendorFragment.VendorMapFragment;
import com.tv.GreenGrubBox.Fragment.VendorListFragment.VendorListFragment;
import com.tv.GreenGrubBox.SplashActivity;
import com.tv.GreenGrubBox.activites.activites.BoxHistory.BoxHistoryActivity;
import com.tv.GreenGrubBox.activites.activites.MainViewScreen.MainViewScreenActivity;
import com.tv.GreenGrubBox.activites.activites.NewMainViewScreen.NewMainViewScreenActivity;
import com.tv.GreenGrubBox.di.PerActivity;
import com.tv.GreenGrubBox.di.module.ActivityModule;
import com.tv.GreenGrubBox.home.HomeActivity;
import com.tv.GreenGrubBox.launch.LaunchActivity;
import com.tv.GreenGrubBox.login.LoginActivity;
import com.tv.GreenGrubBox.signup.SignUpActivity;
import com.tv.GreenGrubBox.signup.signupcorporate.SignUpCorporateActivity;
import com.tv.GreenGrubBox.signup.signupindividual.SignUpIndividualActivity;

import dagger.Component;


@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity activity);

    void inject(LaunchActivity loginActivity);

    void inject(HomeActivity homeActivity);

    void inject(MainViewScreenActivity mainViewScreenActivity);

    void inject(LoginActivity loginActivity);

    void inject(SignUpActivity signUpActivity);

    void inject(SignUpCorporateActivity signUpCorporateActivity);

    void inject(SignUpIndividualActivity signUpIndividualActivity);

    void inject(NewMainViewScreenActivity newMainViewScreenActivity);

    void inject(AccountFragment accountFragment);

    void inject(BoxHistoryActivity boxHistoryActivity);

    void inject(VendorListFragment vendorListFragment);

    void inject(VendorMapFragment vendorMapFragment);

    void inject(ForgotPasswordDialog forgotPasswordDialog);

    void inject(ActivateAccountActivity activateAccountActivity);

    void inject(ActivateAccountDialog activateAccountDialog);

    void inject(CorporateFragment corporateFragment);

    void inject(IndividualFragment individualFragment);
}
