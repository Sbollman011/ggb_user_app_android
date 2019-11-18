package com.tv.GreenGrubBox.ActivateAccount;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.tv.GreenGrubBox.BaseClasses.BaseDialog;
import com.tv.GreenGrubBox.BaseClasses.DialogInterface;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.di.component.ActivityComponent;
import com.tv.GreenGrubBox.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dev-20 on 4/10/18.
 */

public class ActivateAccountDialog extends BaseDialog {


    private static final String TAG =ActivateAccountDialog.class.getSimpleName();

    @BindView(R.id.aSix_digit_pin_tv)
    TextView aSix_digit_pin_tv;

    @BindView(R.id.if_is_not_showing_tv)
    TextView if_is_not_showing_tv;

    @BindView(R.id.pin_sent_tv)
    TextView pin_sent_tv;

    @BindView(R.id.cross_iv)
    ImageView cross_iv;

    @OnClick(R.id.cross_iv)
    void onclickcross_iv(View view) {
        dismiss();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activate_account_dialog, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            //mPresenter.onAttach(this);
        }
        return view;
    }

    @Override
    public void showLoadingGif() {

    }

    @Override
    public void showDialog(String title, String msg1, String msg2, int type, DialogInterface mDialogInterface) {

    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    public static ActivateAccountDialog newInstance(Bundle bundle) {
        ActivateAccountDialog fragment = new ActivateAccountDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void setUp(View view) {



        setFonts();

    }

    private void setFonts() {
        if_is_not_showing_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
        aSix_digit_pin_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
        pin_sent_tv.setTypeface(CommonUtils.setBoldFont(getActivity()));
    }
}
