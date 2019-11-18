package com.tv.GreenGrubBox.adapter;

import android.app.Activity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.PackageDatum;

import java.util.List;

/**
 * Created by tv-1 on 30/01/18.
 */

public class PackageAdapter extends BaseAdapter {

    private Activity mActivity;
    private LayoutInflater inflter;

    private List<PackageDatum> mPackageDatumArrayList;

    public PackageAdapter(Activity mActivity, List<PackageDatum> mPackageDatumArrayList) {
        this.mActivity = mActivity;
        this.mPackageDatumArrayList = mPackageDatumArrayList;
        inflter = (LayoutInflater.from(mActivity));

    }


    @Override
    public int getCount() {
        return mPackageDatumArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mPackageDatumArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflter.inflate(R.layout.package_custom_spinner_items, null);
        }
        TextView name_tv = view.findViewById(R.id.name_tv);
        TextView price_tv = view.findViewById(R.id.price_tv);
        PackageDatum bean = mPackageDatumArrayList.get(i);
        // name_tv.setText(String.valueOf(bean.getDays()).concat(" ").concat(mActivity.getResources().getString(R.string.daysText)));

        String upperString = bean.getName().substring(0, 1).toUpperCase() + bean.getName().substring(1);
        name_tv.setText(upperString);


        if (i > 0) {
            price_tv.setText(("$").concat(bean.getValue()));
        } else {
            price_tv.setText("");
        }


        return view;
    }
}
