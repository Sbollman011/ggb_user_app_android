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

package com.tv.GreenGrubBox.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.tv.GreenGrubBox.ActivateAccount.ActivateAccountMvpPresenter;
import com.tv.GreenGrubBox.ActivateAccount.ActivateAccountMvpView;
import com.tv.GreenGrubBox.ActivateAccount.ActivateAccountPresenter;
import com.tv.GreenGrubBox.ActivateAccount.ActiviateAccountInteractor;
import com.tv.GreenGrubBox.ActivateAccount.ActiviateAccountMvpInteractor;
import com.tv.GreenGrubBox.Dialogs.ChangeEmailDialog.ChangeEmailIdInteractor;
import com.tv.GreenGrubBox.Dialogs.ChangeEmailDialog.ChangeEmailIdMvpInteractor;
import com.tv.GreenGrubBox.Dialogs.ChangeEmailDialog.ChangeEmailIdMvpPresenter;
import com.tv.GreenGrubBox.Dialogs.ChangeEmailDialog.ChangeEmailIdMvpView;
import com.tv.GreenGrubBox.Dialogs.ChangeEmailDialog.ChangeEmailIdPresenter;
import com.tv.GreenGrubBox.Dialogs.ChangeToAccountTypeDialog.ChangeToAccountTypeInteractor;
import com.tv.GreenGrubBox.Dialogs.ChangeToAccountTypeDialog.ChangeToAccountTypeMvpInteractor;
import com.tv.GreenGrubBox.Dialogs.ChangeToAccountTypeDialog.ChangeToAccountTypeMvpPresenter;
import com.tv.GreenGrubBox.Dialogs.ChangeToAccountTypeDialog.ChangeToAccountTypeMvpView;
import com.tv.GreenGrubBox.Dialogs.ChangeToAccountTypeDialog.ChangeToAccountTypePresenter;
import com.tv.GreenGrubBox.Dialogs.ForgotPasswordDialog.ForgotPasswordInteractor;
import com.tv.GreenGrubBox.Dialogs.ForgotPasswordDialog.ForgotPasswordMvpInteractor;
import com.tv.GreenGrubBox.Dialogs.ForgotPasswordDialog.ForgotPasswordMvpPresenter;
import com.tv.GreenGrubBox.Dialogs.ForgotPasswordDialog.ForgotPasswordMvpView;
import com.tv.GreenGrubBox.Dialogs.ForgotPasswordDialog.ForgotPasswordPresenter;
import com.tv.GreenGrubBox.Fragment.Account.AccountInteractor;
import com.tv.GreenGrubBox.Fragment.Account.AccountMvpInteractor;
import com.tv.GreenGrubBox.Fragment.Account.AccountMvpPresenter;
import com.tv.GreenGrubBox.Fragment.Account.AccountMvpView;
import com.tv.GreenGrubBox.Fragment.Account.AccountPresenter;
import com.tv.GreenGrubBox.Fragment.Corporate.CorporateInteractor;
import com.tv.GreenGrubBox.Fragment.Corporate.CorporateMvpInteractor;
import com.tv.GreenGrubBox.Fragment.Corporate.CorporateMvpPresenter;
import com.tv.GreenGrubBox.Fragment.Corporate.CorporateMvpView;
import com.tv.GreenGrubBox.Fragment.Corporate.CorporatePresenter;
import com.tv.GreenGrubBox.Fragment.Individual.IndividualInteractor;
import com.tv.GreenGrubBox.Fragment.Individual.IndividualMvpInteractor;
import com.tv.GreenGrubBox.Fragment.Individual.IndividualMvpPresenter;
import com.tv.GreenGrubBox.Fragment.Individual.IndividualMvpView;
import com.tv.GreenGrubBox.Fragment.Individual.IndividualPresenter;
import com.tv.GreenGrubBox.Fragment.MapViewVendorFragment.VendorMapInteractor;
import com.tv.GreenGrubBox.Fragment.MapViewVendorFragment.VendorMapMvpInteractor;
import com.tv.GreenGrubBox.Fragment.MapViewVendorFragment.VendorMapMvpPresenter;
import com.tv.GreenGrubBox.Fragment.MapViewVendorFragment.VendorMapMvpView;
import com.tv.GreenGrubBox.Fragment.MapViewVendorFragment.VendorMapPresenter;
import com.tv.GreenGrubBox.Fragment.VendorListFragment.VendorListInteractor;
import com.tv.GreenGrubBox.Fragment.VendorListFragment.VendorListMvpInteractor;
import com.tv.GreenGrubBox.Fragment.VendorListFragment.VendorListMvpPresenter;
import com.tv.GreenGrubBox.Fragment.VendorListFragment.VendorListMvpView;
import com.tv.GreenGrubBox.Fragment.VendorListFragment.VendorListPresenter;
import com.tv.GreenGrubBox.activites.activites.BoxHistory.BoxHistoryInteractor;
import com.tv.GreenGrubBox.activites.activites.BoxHistory.BoxHistoryMvpInteractor;
import com.tv.GreenGrubBox.activites.activites.BoxHistory.BoxHistoryMvpPresenter;
import com.tv.GreenGrubBox.activites.activites.BoxHistory.BoxHistoryMvpView;
import com.tv.GreenGrubBox.activites.activites.BoxHistory.BoxHistoryPresenter;
import com.tv.GreenGrubBox.activites.activites.MainViewScreen.MainViewScreenInteractor;
import com.tv.GreenGrubBox.activites.activites.MainViewScreen.MainViewScreenMvpInteractor;
import com.tv.GreenGrubBox.activites.activites.MainViewScreen.MainViewScreenMvpPresenter;
import com.tv.GreenGrubBox.activites.activites.MainViewScreen.MainViewScreenMvpView;
import com.tv.GreenGrubBox.activites.activites.MainViewScreen.MainViewScreenPresenter;
import com.tv.GreenGrubBox.activites.activites.NewMainViewScreen.NewMainViewInteractor;
import com.tv.GreenGrubBox.activites.activites.NewMainViewScreen.NewMainViewMvpInteractor;
import com.tv.GreenGrubBox.activites.activites.NewMainViewScreen.NewMainViewMvpPresenter;
import com.tv.GreenGrubBox.activites.activites.NewMainViewScreen.NewMainViewMvpView;
import com.tv.GreenGrubBox.activites.activites.NewMainViewScreen.NewMainViewPresenter;
import com.tv.GreenGrubBox.adapter.BoxHistoryAdapter;
import com.tv.GreenGrubBox.adapter.LocationItemAdapter;
import com.tv.GreenGrubBox.data.modal.BoxHistoryDatum;
import com.tv.GreenGrubBox.data.modal.MainLocationDatum;
import com.tv.GreenGrubBox.di.ActivityContext;
import com.tv.GreenGrubBox.di.PerActivity;
import com.tv.GreenGrubBox.home.HomeInteractor;
import com.tv.GreenGrubBox.home.HomeMvpInteractor;
import com.tv.GreenGrubBox.home.HomeMvpPresenter;
import com.tv.GreenGrubBox.home.HomeMvpView;
import com.tv.GreenGrubBox.home.HomePresenter;
import com.tv.GreenGrubBox.launch.LaunchInteractor;
import com.tv.GreenGrubBox.launch.LaunchMvpInteractor;
import com.tv.GreenGrubBox.launch.LaunchMvpPresenter;
import com.tv.GreenGrubBox.launch.LaunchMvpView;
import com.tv.GreenGrubBox.launch.LaunchPresenter;
import com.tv.GreenGrubBox.login.LoginInteractor;
import com.tv.GreenGrubBox.login.LoginMvpInteractor;
import com.tv.GreenGrubBox.login.LoginMvpPresenter;
import com.tv.GreenGrubBox.login.LoginMvpView;
import com.tv.GreenGrubBox.login.LoginPresenter;
import com.tv.GreenGrubBox.signup.SignUpInteractor;
import com.tv.GreenGrubBox.signup.SignUpMvpInteractor;
import com.tv.GreenGrubBox.signup.SignUpMvpPresenter;
import com.tv.GreenGrubBox.signup.SignUpMvpView;
import com.tv.GreenGrubBox.signup.SignUpPresenter;
import com.tv.GreenGrubBox.signup.signupcorporate.SignUpCorporateInteractor;
import com.tv.GreenGrubBox.signup.signupcorporate.SignUpCorporateMvpInteractor;
import com.tv.GreenGrubBox.signup.signupcorporate.SignUpCorporateMvpPresenter;
import com.tv.GreenGrubBox.signup.signupcorporate.SignUpCorporateMvpView;
import com.tv.GreenGrubBox.signup.signupcorporate.SignUpCorporatePresenter;
import com.tv.GreenGrubBox.signup.signupindividual.SignUpIndividualInteractor;
import com.tv.GreenGrubBox.signup.signupindividual.SignUpIndividualMvpInteractor;
import com.tv.GreenGrubBox.signup.signupindividual.SignUpIndividualMvpPresenter;
import com.tv.GreenGrubBox.signup.signupindividual.SignUpIndividualMvpView;
import com.tv.GreenGrubBox.signup.signupindividual.SignUpIndividualPresenter;
import com.tv.GreenGrubBox.utils.rx.AppSchedulerProvider;
import com.tv.GreenGrubBox.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by janisharali on 27/01/17.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    BoxHistoryAdapter provideBoxHistoryAdapter() {
        return new BoxHistoryAdapter(mActivity, new ArrayList<BoxHistoryDatum>());
    }

    @Provides
    LocationItemAdapter provideLocationItemAdapter() {
        return new LocationItemAdapter( new ArrayList<MainLocationDatum>(),mActivity);
    }


    @Provides
    @PerActivity
    LaunchMvpInteractor provideLaunchMvpInteractor(LaunchInteractor interactor) {
        return interactor;
    }

    @Provides
    LaunchMvpPresenter<LaunchMvpView, LaunchMvpInteractor> provideLaunchPresenter(
            LaunchPresenter<LaunchMvpView, LaunchMvpInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainViewScreenMvpInteractor provideMainViewScreenMvpInteractor(MainViewScreenInteractor interactor) {
        return interactor;
    }

    @Provides
    MainViewScreenMvpPresenter<MainViewScreenMvpView, MainViewScreenMvpInteractor> provideMainViewScreenPresenter(
            MainViewScreenPresenter<MainViewScreenMvpView, MainViewScreenMvpInteractor> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    HomeMvpInteractor provideHomeMvpInteractor(HomeInteractor interactor) {
        return interactor;
    }

    @Provides
    HomeMvpPresenter<HomeMvpView, HomeMvpInteractor> provideHomePresenter(
            HomePresenter<HomeMvpView, HomeMvpInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpInteractor provideLoginMvpInteractor(LoginInteractor interactor) {
        return interactor;
    }

    @Provides
    LoginMvpPresenter<LoginMvpView, LoginMvpInteractor> provideLoginPresenter(
            LoginPresenter<LoginMvpView, LoginMvpInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SignUpMvpInteractor provideSignUpMvpInteractor(SignUpInteractor interactor) {
        return interactor;
    }

    @Provides
    SignUpMvpPresenter<SignUpMvpView, SignUpMvpInteractor> provideSignUpPresenter(
            SignUpPresenter<SignUpMvpView, SignUpMvpInteractor> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    SignUpCorporateMvpInteractor provideSignUpCorporateMvpInteractor(SignUpCorporateInteractor interactor) {
        return interactor;
    }

    @Provides
    SignUpCorporateMvpPresenter<SignUpCorporateMvpView, SignUpCorporateMvpInteractor> provideSignUpCorporatePresenter(
            SignUpCorporatePresenter<SignUpCorporateMvpView, SignUpCorporateMvpInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SignUpIndividualMvpInteractor provideSignUpIndividualMvpInteractor(SignUpIndividualInteractor interactor) {
        return interactor;
    }

    @Provides
    SignUpIndividualMvpPresenter<SignUpIndividualMvpView, SignUpIndividualMvpInteractor> provideSignUpIndividualPresenter(
            SignUpIndividualPresenter<SignUpIndividualMvpView, SignUpIndividualMvpInteractor> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    NewMainViewMvpInteractor provideNewMainViewMvpInteractor(NewMainViewInteractor interactor) {
        return interactor;
    }

    @Provides
    NewMainViewMvpPresenter<NewMainViewMvpView, NewMainViewMvpInteractor> provideNewMainViewPresenter(
            NewMainViewPresenter<NewMainViewMvpView, NewMainViewMvpInteractor> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    AccountMvpInteractor provideAccountMvpInteractor(AccountInteractor interactor) {
        return interactor;
    }

    @Provides
    AccountMvpPresenter<AccountMvpView, AccountMvpInteractor> provideAccountPresenter(
            AccountPresenter<AccountMvpView, AccountMvpInteractor> presenter) {
        return presenter;
    }

  @Provides
    @PerActivity
  BoxHistoryMvpInteractor provideBoxHistoryMvpInteractor(BoxHistoryInteractor interactor) {
        return interactor;
    }

    @Provides
    BoxHistoryMvpPresenter<BoxHistoryMvpView, BoxHistoryMvpInteractor> provideBoxHistoryPresenter(
            BoxHistoryPresenter<BoxHistoryMvpView, BoxHistoryMvpInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    VendorListMvpInteractor provideVendorListMvpInteractor(VendorListInteractor interactor) {
        return interactor;
    }

    @Provides
    VendorListMvpPresenter<VendorListMvpView, VendorListMvpInteractor> provideVendorListPresenter(
            VendorListPresenter<VendorListMvpView, VendorListMvpInteractor> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    VendorMapMvpInteractor provideVendorMapMvpInteractor(VendorMapInteractor interactor) {
        return interactor;
    }

    @Provides
    VendorMapMvpPresenter<VendorMapMvpView, VendorMapMvpInteractor> provideVendorMapPresenter(
            VendorMapPresenter<VendorMapMvpView, VendorMapMvpInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ForgotPasswordMvpInteractor provideForgotPasswordMvpInteractor(ForgotPasswordInteractor interactor) {
        return interactor;
    }

    @Provides
    ForgotPasswordMvpPresenter<ForgotPasswordMvpView, ForgotPasswordMvpInteractor> provideForgotPasswordPresenter(
            ForgotPasswordPresenter<ForgotPasswordMvpView, ForgotPasswordMvpInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CorporateMvpInteractor provideCorporateMvpInteractor(CorporateInteractor interactor) {
        return interactor;
    }

    @Provides
    CorporateMvpPresenter<CorporateMvpView, CorporateMvpInteractor> provideCorporatePresenter(
            CorporatePresenter<CorporateMvpView, CorporateMvpInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    IndividualMvpInteractor provideIndividualMvpInteractor(IndividualInteractor interactor) {
        return interactor;
    }

    @Provides
    IndividualMvpPresenter<IndividualMvpView, IndividualMvpInteractor> provideIndividualPresenter(
            IndividualPresenter<IndividualMvpView, IndividualMvpInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ActiviateAccountMvpInteractor provideActivateAccountMvpInteractor(ActiviateAccountInteractor interactor) {
        return interactor;
    }

    @Provides
    ActivateAccountMvpPresenter<ActivateAccountMvpView, ActiviateAccountMvpInteractor> provideActivateAccountPresenter(
            ActivateAccountPresenter<ActivateAccountMvpView, ActiviateAccountMvpInteractor> presenter) {
        return presenter;
    }



    @Provides
    @PerActivity
    ChangeToAccountTypeMvpInteractor provideChangeToAccountTypeMvpInteractor(ChangeToAccountTypeInteractor interactor) {
        return interactor;
    }

    @Provides
    ChangeToAccountTypeMvpPresenter<ChangeToAccountTypeMvpView, ChangeToAccountTypeMvpInteractor> provideChangeToAccountTypePresenter(
            ChangeToAccountTypePresenter<ChangeToAccountTypeMvpView, ChangeToAccountTypeMvpInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ChangeEmailIdMvpInteractor provideChangeEmailIdMvpInteractor(ChangeEmailIdInteractor interactor) {
        return interactor;
    }

    @Provides
    ChangeEmailIdMvpPresenter<ChangeEmailIdMvpView, ChangeEmailIdMvpInteractor> provideChangeEmailIdPresenter(
            ChangeEmailIdPresenter<ChangeEmailIdMvpView, ChangeEmailIdMvpInteractor> presenter) {
        return presenter;
    }

}
