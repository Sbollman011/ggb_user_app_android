package com.tv.GreenGrubBox.Dialogs.ForgotPasswordDialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tv.GreenGrubBox.BaseClasses.BaseDialog;
import com.tv.GreenGrubBox.BaseClasses.DialogInterface;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.ForgotPasswordResponceModal;
import com.tv.GreenGrubBox.di.component.ActivityComponent;
import com.tv.GreenGrubBox.utils.CommonUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 8/2/18.
 */

public class ForgotPasswordDialog extends BaseDialog implements ForgotPasswordMvpView {

    @Inject
    ForgotPasswordMvpPresenter<ForgotPasswordMvpView, ForgotPasswordMvpInteractor> mPresenter;

    @BindView(R.id.forgot_your_pass_tv)
    TextView forgot_your_pass_tv;

    @BindView(R.id.email_address_et)
    EditText email_address_et;

    @BindView(R.id.email_ll)
    LinearLayout email_ll;

    @BindView(R.id.cancel_btn)
    Button cancel_btn;

    @OnClick(R.id.cancel_btn)
    void onClickcancel_btn(View v) {
        dismiss();
    }

    @OnClick(R.id.submit_btn)
    void onClicksubmit_btn(View v) {
        mPresenter.doForgotPassword(email_address_et.getText().toString().trim());
    }

    @BindView(R.id.submit_btn)
    Button submit_btn;

    @BindView(R.id.we_just_need_tv)
    TextView we_just_need_tv;

    private static final String TAG = ForgotPasswordDialog.class.getSimpleName();

    public static ForgotPasswordDialog newInstance(Bundle bundle) {
        ForgotPasswordDialog fragment = new ForgotPasswordDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void show(FragmentManager fragmentManager) {

        super.show(fragmentManager, TAG);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password_dialog, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);

        }
        setFonts();
        return view;
    }

    private void setFonts() {
        forgot_your_pass_tv.setTypeface(CommonUtils.setBoldFont(getActivity()));
        email_address_et.setTypeface(CommonUtils.setRegularFont(getActivity()));
        cancel_btn.setTypeface(CommonUtils.setBoldFont(getActivity()));
        submit_btn.setTypeface(CommonUtils.setBoldFont(getActivity()));
        we_just_need_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
    }

    @Override
    public void showLoadingGif() {

    }

    @Override
    public void showDialog(String title, String msg1, String msg2, int type, DialogInterface mDialogInterface) {

    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public void closeDialog(ForgotPasswordResponceModal modal) {
        if (modal.getStatus() == 1) {
            dismiss();
        } else {
            showMessage(modal.getMessage());
        }
    }
}
