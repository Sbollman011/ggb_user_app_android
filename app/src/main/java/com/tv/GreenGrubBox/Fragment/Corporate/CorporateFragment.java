package com.tv.GreenGrubBox.Fragment.Corporate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.stripe.android.model.Card;
import com.tv.GreenGrubBox.BaseClasses.BaseFragment;
import com.tv.GreenGrubBox.Fragment.VendorListFragment.VendorListMvpInteractor;
import com.tv.GreenGrubBox.Fragment.VendorListFragment.VendorListMvpPresenter;
import com.tv.GreenGrubBox.Fragment.VendorListFragment.VendorListMvpView;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.activites.activites.NewMainViewScreen.NewMainViewScreenActivity;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;
import com.tv.GreenGrubBox.data.modal.SignUpResponseDatum;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;
import com.tv.GreenGrubBox.utils.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CorporateFragment extends BaseFragment implements CorporateMvpView {

    @Inject
    public CorporateMvpPresenter<CorporateMvpView, CorporateMvpInteractor> mPresenter;
    @BindView(R.id.done_btn)
    Button done_btn;
    @BindView(R.id.promocode_et)
    EditText promocode_et;
    @BindView(R.id.promocode_tv)
    TextView promocode_tv;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.corporate_user_tv)
    TextView corporate_user_tv;
    @BindView(R.id.name_et)
    EditText name_et;
    private SignUpResponseDatum mSignUpResponse;

    public static CorporateFragment newInstance(Bundle bundle) {


        CorporateFragment fragment = new CorporateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.done_btn)
    void onclickdone_btn(View view) {
        if (!isValidData()) {
            return;
        }
        DeviceInfo mDeviceInfo = new DeviceInfo();
        mDeviceInfo.setOs(Constant.ANDROID);
        mDeviceInfo.setResHeight(Constant.getDeviceHeight(getActivity()));
        mDeviceInfo.setResWidth(Constant.getDeviceWidth(getActivity()));
        mDeviceInfo.setOsVer(Constant.getOSDetails());
        mDeviceInfo.setDevModel(Constant.getDeviceName());
        mDeviceInfo.setAppVer(String.valueOf(Constant.appBuildVersionCode(getActivity())));
        mDeviceInfo.setBattery(Constant.getBatteryPercentage(getActivity()));
        mDeviceInfo.setDevice_token(CommonUtils.getDeviceTokenFromFCM());

        mPresenter.signUpCorporate(name_et.getText().toString().trim(), promocode_et.getText().toString().trim(), mSignUpResponse, mDeviceInfo);
    }

    private boolean isValidData() {

        if (name_et.getText().toString().trim().isEmpty()) {
            showMessage(R.string.pleaseEnterNameText);
            return false;
        }

        if (promocode_et.getText().toString().trim().isEmpty()) {
            showMessage(R.string.pleaseenterpromocode);
            return false;
        }


        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_corporate_layout, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);
        setFonts();
        initData();
        return view;
    }

    private void setFonts() {
        promocode_et.setTypeface(CommonUtils.setRegularFont(getActivity()));
        promocode_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
        done_btn.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        name_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
        corporate_user_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        name_et.setTypeface(CommonUtils.setRegularFont(getActivity()));
    }

    private void initData() {
        if (getArguments() != null && getArguments().containsKey(Constant.SIGN_UP_RESPONSE)) {
            mSignUpResponse = (SignUpResponseDatum) getArguments().getSerializable(Constant.SIGN_UP_RESPONSE);
        }


    }


    @Override
    protected void setUp(View view) {

    }

//    @Override
//    public void openNewScreen(SignUpResponse mSignUpResponse) {
//        if (mSignUpResponse == null) {
//            showMessage(R.string.some_error);
//            return;
//        }
//
//
//        Intent mIntent = new Intent(getActivity(), NewMainViewScreenActivity.class);
//        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(mIntent);
//        startActivitySideWiseAnimation();
//        finishCurrentActivity();
//
//       /* if (mSignUpResponse.getStatus() == 1) {
//            mPresenter.doLogin(mSignUpResponse.getEmail(), mSignUpResponse.getPassword());
//
//
//        } else {
//            showMessage(mSignUpResponse.getMessage());
//        }*/
//    }


    @Override
    public void MainScreen(LoginResponse mLoginResponse) {


        Intent mIntent = new Intent(getActivity(), NewMainViewScreenActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mIntent);
        startActivitySideWiseAnimation();
        finishCurrentActivity();
    }
}
