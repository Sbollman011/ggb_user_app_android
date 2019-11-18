package com.tv.GreenGrubBox.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tv.GreenGrubBox.BaseClasses.BaseFragment;
import com.tv.GreenGrubBox.R;

/**
 * Created by tv-1 on 01/02/18.
 */

public class BlankFragment extends BaseFragment {
    @Override
    protected void setUp(View view) {

    }

    private View mBlankView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mBlankView = inflater.inflate(R.layout.fragment_blank, container, false);

        return mBlankView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
