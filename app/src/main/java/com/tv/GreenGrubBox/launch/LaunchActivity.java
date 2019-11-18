package com.tv.GreenGrubBox.launch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.augustopicciani.drawablepageindicator.widget.DrawablePagerIndicator;
import com.tv.GreenGrubBox.BaseClasses.BaseActivity;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.adapter.Slide_ViewPagerAdapter;
import com.tv.GreenGrubBox.data.modal.LandingModal;
import com.tv.GreenGrubBox.data.modal.LoginRequest;
import com.tv.GreenGrubBox.login.LoginActivity;
import com.tv.GreenGrubBox.signup.SignUpActivity;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Views.VerticalViewPager;
import java.util.ArrayList;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 16/01/18.
 */

public class LaunchActivity extends BaseActivity implements LaunchMvpView, ViewPager.OnPageChangeListener {

    private static final String TAG = LaunchActivity.class.getSimpleName();

    @Inject
    LaunchMvpPresenter<LaunchMvpView, LaunchMvpInteractor> mPresenter;

    private long mLastClickTime = 0;

    @BindView(R.id.vp_slider)
    ViewPager vp_slider;

    @BindView(R.id.vp_slider_vertical)
    VerticalViewPager vp_slider_vertical;

    @BindView(R.id.ll_dots)
    DrawablePagerIndicator ll_dots;

    @BindView(R.id.login_tv)
    TextView login_tv;

    @BindView(R.id.sign_up_tv)
    TextView sign_up_tv;

    @OnClick(R.id.sign_up_tv)
    void onClicksign_up_tv(View view) {
        Intent intent = new Intent(LaunchActivity.this, SignUpActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.login_tv)
    void onClickLogin_tv(View view) {
        Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    @BindView(R.id.changeimg_iv)
    ImageView changeimg_iv;

    @BindView(R.id.register_rl)
    LinearLayout register_rl;

    @BindView(R.id.swipe_to_learn_tv)
    TextView swipe_to_learn_tv;


    int[] mResources = {
            R.drawable.icon_a,
            R.drawable.icon_b,
            R.drawable.icon_c,
            R.drawable.icon_d
    };
    private int mCurrentPos = 0;

   /* @OnClick(R.id.login_tv)
    void onclicklogin_tv(View view) {
        Intent intent = new Intent(LaunchActivity.this, MainViewScreenActivity.class);
        startActivity(intent);
    }*/

    private Slide_ViewPagerAdapter sliderPagerAdapter;
    private ArrayList<LandingModal> slider_image_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        ButterKnife.setDebug(true);
        mPresenter.onAttach(LaunchActivity.this);
        setFonts();
        initData();
    }

    private void setFonts() {
        sign_up_tv.setTypeface(CommonUtils.setRegularFont(this));
        login_tv.setTypeface(CommonUtils.setRegularFont(this));
        swipe_to_learn_tv.setTypeface(CommonUtils.setSemiBoldFont(this));

    }

    private void initData() {
        //Load animation
        final Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        final Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);


        vp_slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                vp_slider_vertical.setCurrentItem(position, true);

                if (mCurrentPos < position) {
                    //slide down
                    slideAnimation(slide_down);
                } else {
                    //slide up
                    slideAnimation(slide_up);
                }

                mCurrentPos = position;

                switch (position) {
                    case 0:
                        // uppettext_tv.setText("");
                        changeimg_iv.setImageResource(R.mipmap.ic_launcher_round);
                        break;
                    case 1:
                        //  uppettext_tv.setText(getResources().getString(R.string.signup));
                        changeimg_iv.setImageResource(R.mipmap.ic_launcher_round);
                        break;
                    case 2:
                        // uppettext_tv.setText(getResources().getString(R.string.practice));
                        changeimg_iv.setImageResource(R.mipmap.ic_launcher_round);
                        break;
                    case 3:
                        // uppettext_tv.setText(getResources().getString(R.string.trk_progress));
                        changeimg_iv.setImageResource(R.mipmap.ic_launcher_round);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_slider.setOnPageChangeListener(this);

        CustomPagerAdapterVerticalImages mCustomPagerAdapter = new CustomPagerAdapterVerticalImages(this);
        vp_slider_vertical.setPagingEnabled(false);
        vp_slider_vertical.setAdapter(mCustomPagerAdapter);

        slider_image_list.add(new LandingModal(getResources().getString(R.string.headingscrollertrxt1), getResources().getString(R.string.scrollertext1)));
        slider_image_list.add(new LandingModal(getResources().getString(R.string.headingscrollertrxt2), getResources().getString(R.string.scrollertext2)));
        slider_image_list.add(new LandingModal(getResources().getString(R.string.headingscrollertrxt3), getResources().getString(R.string.scrollertext3)));
        slider_image_list.add(new LandingModal(getResources().getString(R.string.headingscrollertrxt4), getResources().getString(R.string.scrollertext4)));

        sliderPagerAdapter = new Slide_ViewPagerAdapter(LaunchActivity.this, slider_image_list);
        vp_slider.setAdapter(sliderPagerAdapter);
        ll_dots.setViewPager(vp_slider);

    }

    private void slideAnimation(Animation animation) {

// Start animation
        changeimg_iv.startAnimation(animation);


    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {

    }


    @Override
    public void openRegScreen(LoginRequest mLoginRequest) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                // uppettext_tv.setText("");
                changeimg_iv.setImageResource(R.drawable.ic_launcher_background);
                break;
            case 1:
                //  uppettext_tv.setText(getResources().getString(R.string.signup));
//                changeimg_iv.setImageResource(R.drawable.login_facebook);
                break;
            case 2:
                // uppettext_tv.setText(getResources().getString(R.string.practice));
//                changeimg_iv.setImageResource(R.drawable.login_google_plus);
                break;
            case 3:
                // uppettext_tv.setText(getResources().getString(R.string.trk_progress));
                changeimg_iv.setImageResource(R.drawable.ic_launcher_background);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class CustomPagerAdapterVerticalImages extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapterVerticalImages(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

}
