package com.tv.GreenGrubBox.Fragment.MapViewVendorFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tv.GreenGrubBox.BaseClasses.BaseFragment;
import com.tv.GreenGrubBox.Callbacks.OpenLocationDetailsCallback;
import com.tv.GreenGrubBox.Dialogs.LocationInfoDialog;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.MainLocationDatum;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;
import com.tv.GreenGrubBox.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tv-1 on 07/02/18.
 */

public class MapViewVendorFragment extends BaseFragment {
    private static final String TAG = MapViewVendorFragment.class.getSimpleName();

    @Override
    protected void setUp(View view) {
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
        setupTabText(tabs);
        tabs.getTabAt(0).select();
//        setCustomLayoutToTabs();
    }


    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.viewPager)
    ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_view_main_fragment_vendor, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    private void setupTabText(TabLayout tabs) {

        TextView tabOne = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabOne.setText(getResources().getString(R.string.vendors));
        tabOne.setTypeface(CommonUtils.setRegularFont(getActivity()));
        tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabTwo.setText(getResources().getString(R.string.dropstations));
        tabTwo.setTypeface(CommonUtils.setRegularFont(getActivity()));
        tabs.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabThree.setText(getResources().getString(R.string.both));
        tabThree.setTypeface(CommonUtils.setRegularFont(getActivity()));
        tabs.getTabAt(2).setCustomView(tabThree);

    }


    VendorMapFragment mVendorMapFragment;
    VendorMapFragment mVendorMapFragmentDS;
    VendorMapFragment mVendorMapFragmentBoth;

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        mVendorMapFragment = VendorMapFragment.newInstance(null, new OpenLocationDetailsCallback() {
            @Override
            public void openLocationDetails(MainLocationDatum bean) {
                openLocationDetailsFragment(bean);

            }
        });
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.LOCATION_TYPE, "1");// vendor
        mVendorMapFragment.setArguments(mBundle);
        adapter.addFrag(mVendorMapFragment, getResources().getString(R.string.vendors));

        mVendorMapFragmentDS = VendorMapFragment.newInstance(null, new OpenLocationDetailsCallback() {
            @Override
            public void openLocationDetails(MainLocationDatum bean) {
                openLocationDetailsFragment(bean);


            }
        });
        Bundle mBundleDS = new Bundle();
        mBundleDS.putString(Constant.LOCATION_TYPE, "2");// Drop stations
        mVendorMapFragmentDS.setArguments(mBundleDS);
        adapter.addFrag(mVendorMapFragmentDS, getResources().getString(R.string.dropstations));

        mVendorMapFragmentBoth = VendorMapFragment.newInstance(null, new OpenLocationDetailsCallback() {
            @Override
            public void openLocationDetails(MainLocationDatum bean) {
                openLocationDetailsFragment(bean);


            }
        });
        Bundle mBundleBoth = new Bundle();
        mBundleBoth.putString(Constant.LOCATION_TYPE, "3");// Both
        mVendorMapFragmentBoth.setArguments(mBundleBoth);
        adapter.addFrag(mVendorMapFragmentBoth, getResources().getString(R.string.both));


        viewPager.setAdapter(adapter);
    }

    private void openLocationDetailsFragment(MainLocationDatum bean) {
        Logger.logsError(TAG, "openLocationDetailsFragment Called");
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(Constant.LOCATION_DATUM, bean);
        LocationInfoDialog mFoodInfoDialog = new LocationInfoDialog();
        mFoodInfoDialog.setArguments(mBundle);
        getFragmentManager().beginTransaction()
                .add(R.id.container_map, mFoodInfoDialog, "LocationInfoDialog")
                .addToBackStack(null)
                .commit();

    }

    public void mapDirectionToCurrentLocation() {
        switch (viewPager.getCurrentItem()) {
            case 0:
                mVendorMapFragment.mapDirectionToCurrentLocation();
                break;
            case 1:
                mVendorMapFragmentDS.mapDirectionToCurrentLocation();

                break;
            case 2:
                mVendorMapFragmentBoth.mapDirectionToCurrentLocation();

                break;
            default:
                break;
        }


    }

    public void search(String mInput) {
        if (viewPager==null){
            return;
        }
        switch (viewPager.getCurrentItem()) {
            case 0:
                mVendorMapFragment.searchData(mInput);
                break;
            case 1:
                mVendorMapFragmentDS.searchData(mInput);

                break;
            case 2:
                mVendorMapFragmentBoth.searchData(mInput);

                break;
            default:
                break;
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setCustomLayoutToTabs() {
        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(getActivity()).inflate(R.layout.main_location_tabs_layout, tabs, false);
            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setTypeface(CommonUtils.setRegularFont(getActivity()));
            tabTextView.setTextSize(16);
            tabTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);
        }
    }

}
