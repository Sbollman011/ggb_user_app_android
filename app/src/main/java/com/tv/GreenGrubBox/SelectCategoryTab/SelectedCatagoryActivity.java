package com.tv.GreenGrubBox.SelectCategoryTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.tv.GreenGrubBox.ActivateAccount.ActivateAccountActivity;
import com.tv.GreenGrubBox.BaseClasses.BaseActivity;
import com.tv.GreenGrubBox.Fragment.Corporate.CorporateFragment;
import com.tv.GreenGrubBox.Fragment.Individual.IndividualFragment;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.SignUpResponse;
import com.tv.GreenGrubBox.data.modal.SignUpResponseDatum;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedCatagoryActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;
    private SignUpResponseDatum mSignUpResponse;
    private boolean IS_CORP_SELECTED = false;
    private ArrayList<String> titleList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectcategory_tab);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        if (getIntent() != null) {
            if (getIntent().hasExtra(Constant.SIGN_UP_RESPONSE)) {
                mSignUpResponse = (SignUpResponseDatum) getIntent().getSerializableExtra(Constant.SIGN_UP_RESPONSE);
            }

            if (getIntent().hasExtra(Constant.IS_CORP_SELECTED)) {
                IS_CORP_SELECTED = getIntent().getBooleanExtra(Constant.IS_CORP_SELECTED, false);
            }
        }
        titleList = new ArrayList<>();

//        tabLayout.addTab(tabLayout.newTab().setText("Corporate"));
//        tabLayout.addTab(tabLayout.newTab().setText("Individual"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Adding adapter to pager
        setupViewPager(viewPager);

        //Adding onTabSelectedListener to swipe views
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);

        /*if (IS_CORP_SELECTED) {
            viewPager.setCurrentItem(0);
        } else {
            viewPager.setCurrentItem(1);
        }*/
        setCustomLayoutToTabs(tabLayout);

    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.SIGN_UP_RESPONSE, mSignUpResponse);
        Pager adapter = new Pager(getSupportFragmentManager());
        adapter.addFragment(IndividualFragment.newInstance(bundle), "Individual");
        adapter.addFragment(CorporateFragment.newInstance(bundle), "Corporate");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void setUp() {

    }
    private void setCustomLayoutToTabs(TabLayout tabs) {
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(this).inflate(R.layout.leader_board_tabs_layout, tabs, false);

            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            TextView tab_below_text = (TextView) relativeLayout.findViewById(R.id.tab_below_text);
            tabTextView.setTypeface(CommonUtils.setBoldFont(this));
            View div = relativeLayout.findViewById(R.id.div);
            if (i == 0) {
                div.setVisibility(View.GONE);
                tab_below_text.setText(getResources().getString(R.string.foryouself));
            } else {
                div.setVisibility(View.VISIBLE);
                tab_below_text.setText(getResources().getString(R.string.ifyouhavecode));
            }
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);

        }
    }

    class Pager extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Pager(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
