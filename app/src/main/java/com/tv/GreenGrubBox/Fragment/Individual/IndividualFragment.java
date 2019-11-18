package com.tv.GreenGrubBox.Fragment.Individual;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardMultilineWidget;
import com.tv.GreenGrubBox.BaseClasses.BaseFragment;
import com.tv.GreenGrubBox.BuildConfig;
import com.tv.GreenGrubBox.Fragment.Corporate.CorporateMvpInteractor;
import com.tv.GreenGrubBox.Fragment.Corporate.CorporateMvpPresenter;
import com.tv.GreenGrubBox.Fragment.Corporate.CorporateMvpView;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.activites.activites.NewMainViewScreen.NewMainViewScreenActivity;
import com.tv.GreenGrubBox.adapter.PackageAdapter;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
import com.tv.GreenGrubBox.data.modal.PackageDatum;
import com.tv.GreenGrubBox.data.modal.PackageModalMain;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;
import com.tv.GreenGrubBox.data.modal.SignUpResponseDatum;
import com.tv.GreenGrubBox.signup.signupindividual.SignUpIndividualActivity;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndividualFragment extends BaseFragment implements IndividualMvpView {


    @Inject
    public IndividualMvpPresenter<IndividualMvpView, IndividualMvpInteractor> mPresenter;
    @BindView(R.id.name_et)
    EditText name_et;
    @BindView(R.id.individual_tv)
    TextView individual_tv;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.choose_your_package_tv)
    TextView choose_your_package_tv;
    @BindView(R.id.package_spinner)
    Spinner package_spinner;
    @BindView(R.id.add_source_card_entry_widget)
    CardMultilineWidget add_source_card_entry_widget;
    @BindView(R.id.done_btn)
    Button done_btn;
    private PackageAdapter mPackageAdapter;
    private int mCurrentPackagePos = 0;
    private List<PackageDatum> packageDataList;
    private SignUpResponseDatum mSignUpResponse;

    public static IndividualFragment newInstance(Bundle bundle) {

        IndividualFragment fragment = new IndividualFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.done_btn)
    void OnClickDoneBtn(View v) {
        if (isValidData()) {

            showLoading();
            /*Stripe stripe;
            if (BuildConfig.DEBUG) {

            } else {
                stripe = new Stripe(getActivity(), "pk_live_Jd6lGHcwAsJj7Dctv1DQDAQ2");
            }*/
//            Stripe stripe = new Stripe(getActivity(), "pk_test_YjaymUsbEdEjfIflmv3S3XhW"); // test
            Stripe stripe = new Stripe(getActivity(), "pk_live_Jd6lGHcwAsJj7Dctv1DQDAQ2"); // live

            stripe.createToken(
                    add_source_card_entry_widget.getCard(),
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            hideLoading();
                            // Send token to your server
                            PackageDatum bean = packageDataList.get(mCurrentPackagePos);

                            DeviceInfo mDeviceInfo = new DeviceInfo();
                            mDeviceInfo.setOs(Constant.ANDROID);
                            mDeviceInfo.setResHeight(Constant.getDeviceHeight(getActivity()));
                            mDeviceInfo.setResWidth(Constant.getDeviceWidth(getActivity()));
                            mDeviceInfo.setOsVer(Constant.getOSDetails());
                            mDeviceInfo.setDevModel(Constant.getDeviceName());
                            mDeviceInfo.setAppVer(String.valueOf(Constant.appBuildVersionCode(getActivity())));
                            mDeviceInfo.setBattery(Constant.getBatteryPercentage(getActivity()));
                            mDeviceInfo.setDevice_token(CommonUtils.getDeviceTokenFromFCM());

                            mPresenter.completeRegistration(name_et.getText().toString().trim(), bean.getId(), token.getId(), mSignUpResponse, mDeviceInfo);
                        }

                        public void onError(Exception error) {
                            // Show localized error message
                            hideLoading();
                            showMessage(error.getMessage());

                        }
                    }
            );
        }
    }

    private boolean isValidData() {

        if (name_et.getText().toString().trim().isEmpty()) {
            showMessage(R.string.pleaseEnterNameText);
            return false;
        }

        if (mCurrentPackagePos == 0 || packageDataList == null || packageDataList.size() == 0) {
            showMessage(R.string.pleaseSelectPackageText);
            return false;
        }

        Card cardToSave = add_source_card_entry_widget.getCard();
        if (cardToSave == null) {
            showMessage(R.string.invalid_cardText);
            return false;
        }
        if (mSignUpResponse == null) {
            showMessage(R.string.some_error);
            return false;
        }


        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_individual, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);
        setFonts();
        initData();
        return view;
    }

    private void setFonts() {
        individual_tv.setTypeface(CommonUtils.setSemiBoldFont(getActivity()));
        name_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
        name_et.setTypeface(CommonUtils.setRegularFont(getActivity()));
        choose_your_package_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
        done_btn.setTypeface(CommonUtils.setRegularFont(getActivity()));

    }

    private void initData() {


        if (getArguments() != null && getArguments().containsKey(Constant.SIGN_UP_RESPONSE)) {
            mSignUpResponse = (SignUpResponseDatum) getArguments().getSerializable(Constant.SIGN_UP_RESPONSE);
        }

        mPresenter.getAllPackages();
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void populatePackageData(PackageModalMain mPackageModalMain) {

        if (mPackageModalMain == null) {
            showMessage(R.string.some_error);
            return;
        }

        if (mPackageModalMain.getData() == null) {
            showMessage(R.string.some_error);
            return;
        }

        if (mPackageModalMain.getData().size() == 0) {
            showMessage(R.string.some_error);
            return;
        }

        this.packageDataList = mPackageModalMain.getData();

        PackageDatum packageDatum = new PackageDatum();
        packageDatum.setName("Select Item");

        this.packageDataList.add(0, packageDatum);

        mPackageAdapter = new PackageAdapter(getActivity(), this.packageDataList);
        package_spinner.setAdapter(mPackageAdapter);
        package_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mCurrentPackagePos = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void MainScreen(LoginResponse mLoginResponse) {

        Intent mIntent = new Intent(getActivity(), NewMainViewScreenActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mIntent);
        startActivitySideWiseAnimation();
        finishCurrentActivity();
    }
}
