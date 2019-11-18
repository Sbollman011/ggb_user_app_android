package com.tv.GreenGrubBox.signup.signupindividual;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
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
import com.tv.GreenGrubBox.BaseClasses.BaseActivity;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.activites.activites.NewMainViewScreen.NewMainViewScreenActivity;
import com.tv.GreenGrubBox.adapter.PackageAdapter;
import com.tv.GreenGrubBox.data.modal.DeviceInfo;
import com.tv.GreenGrubBox.data.modal.LoginResponse;
import com.tv.GreenGrubBox.data.modal.PackageDatum;
import com.tv.GreenGrubBox.data.modal.PackageModalMain;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tv-1 on 30/01/18.
 */

public class SignUpIndividualActivity extends BaseActivity implements SignUpIndividualMvpView {
    private List<PackageDatum> packageDataList;

    @Override
    protected void setUp() {

    }

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

    @OnClick(R.id.done_btn)
    void OnClickDoneBtn(View v) {
        if (isValidData()) {

            showLoading();
//            Stripe stripe = new Stripe(this, "pk_test_YjaymUsbEdEjfIflmv3S3XhW");
            Stripe stripe = new Stripe(this, "pk_live_Jd6lGHcwAsJj7Dctv1DQDAQ2");
            stripe.createToken(
                    add_source_card_entry_widget.getCard(),
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            hideLoading();
                            // Send token to your server
                            PackageDatum bean = packageDataList.get(mCurrentPackagePos);

                            DeviceInfo mDeviceInfo = new DeviceInfo();
                            mDeviceInfo.setOs(Constant.ANDROID);
                            mDeviceInfo.setResHeight(Constant.getDeviceHeight(SignUpIndividualActivity.this));
                            mDeviceInfo.setResWidth(Constant.getDeviceWidth(SignUpIndividualActivity.this));
                            mDeviceInfo.setOsVer(Constant.getOSDetails());
                            mDeviceInfo.setDevModel(Constant.getDeviceName());
                            mDeviceInfo.setAppVer(String.valueOf(Constant.appBuildVersionCode(SignUpIndividualActivity.this)));
                            mDeviceInfo.setBattery(Constant.getBatteryPercentage(SignUpIndividualActivity.this));
                            mDeviceInfo.setDevice_token(CommonUtils.getDeviceTokenFromFCM());

                            mPresenter.completeRegistration(name_et.getText().toString().trim(), bean.getId(), token.getId(), mSignUpResponse,mDeviceInfo);
                        }

                        public void onError(Exception error) {
                            // Show localized error message
                            hideLoading();
                            showMessage(error.getMessage());

                        }
                    }
            );
        }}

    private boolean isValidData() {

        if (name_et.getText().toString().trim().isEmpty()) {
            showMessage(R.string.pleaseEnterNameText);
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

        if ( packageDataList==null || packageDataList.size()==0){
            showMessage(R.string.pleaseSelectPackageText);
            return false;
        }

        return true;
    }

    @Inject
    SignUpIndividualMvpPresenter<SignUpIndividualMvpView, SignUpIndividualMvpInteractor> mPresenter;

    private PackageAdapter mPackageAdapter;
    private int mCurrentPackagePos = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_individual);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(SignUpIndividualActivity.this);

        initData();
        setFonts();
    }

    private void setFonts() {
        individual_tv.setTypeface(CommonUtils.setSemiBoldFont(this));
        name_tv.setTypeface(CommonUtils.setRegularFont(this));
        name_et.setTypeface(CommonUtils.setRegularFont(this));
        choose_your_package_tv.setTypeface(CommonUtils.setRegularFont(this));
        done_btn.setTypeface(CommonUtils.setRegularFont(this));

    }

    private SignUpResponse mSignUpResponse;

    private void initData() {

        if (getIntent() != null) {
            if (getIntent().hasExtra(Constant.SIGN_UP_RESPONSE)) {
                mSignUpResponse = (SignUpResponse) getIntent().getSerializableExtra(Constant.SIGN_UP_RESPONSE);
            }
        }

        mPresenter.getAllPackages();
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

        mPackageAdapter = new PackageAdapter(SignUpIndividualActivity.this, mPackageModalMain.getData());
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
    public void openHomeScreen(SignUpResponse mSignUpResponse) {
        if (mSignUpResponse==null){
            showMessage(R.string.some_error);
            return;
        }
        if (mSignUpResponse.getStatus()==1){
            mPresenter.doLogin(mSignUpResponse.getEmail(),mSignUpResponse.getPassword());

         /*   Intent intent = new Intent(SignUpIndividualActivity.this, MainViewScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            startActivitySideWiseAnimation();
            finishCurrentActivity();*/

        }else{
            showMessage(mSignUpResponse.getMessage());
        }
    }

    @Override
    public void MainScreen(LoginResponse mLoginResponse) {


        Intent mIntent = new Intent(this, NewMainViewScreenActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mIntent);
        startActivitySideWiseAnimation();
        finishCurrentActivity();
    }
}
