package com.tv.GreenGrubBox.activites.activites.MainViewScreen;

import com.tv.GreenGrubBox.BaseClasses.BasePresenter;
import com.tv.GreenGrubBox.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by user on 24/1/18.
 */

public class MainViewScreenPresenter <V extends MainViewScreenMvpView, I extends MainViewScreenMvpInteractor> extends
        BasePresenter<V, I> implements MainViewScreenMvpPresenter<V, I> {

@Inject
    public MainViewScreenPresenter(I mvpInteractor, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }
}
