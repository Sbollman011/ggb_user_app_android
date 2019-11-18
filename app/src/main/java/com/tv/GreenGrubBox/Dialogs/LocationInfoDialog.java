package com.tv.GreenGrubBox.Dialogs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tv.GreenGrubBox.BaseClasses.BaseFragment;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.MainLocationDatum;
import com.tv.GreenGrubBox.utils.CommonUtils;
import com.tv.GreenGrubBox.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by user on 25/1/18.
 */

public class LocationInfoDialog extends BaseFragment {

    @BindView(R.id.product_iv)
    CircleImageView product_iv;

    @OnClick(R.id.product_iv)
    void onClickproduct_iv(View v) {
        if (mainLocationDatum != null && mainLocationDatum.getImages() != null && mainLocationDatum.getImages().size() > 0) {
            Bundle mBundle = new Bundle();
            mBundle.putStringArrayList(Constant.IMAGES_LIST, mainLocationDatum.getImages());
            ImagesDetailDialog mImagesDetailDialog = new ImagesDetailDialog();
            mImagesDetailDialog.setArguments(mBundle);
            mImagesDetailDialog.show(getFragmentManager(), "ImagesDetailDialog");
        }
    }

    @BindView(R.id.web_tv)
    TextView web_tv;

    @BindView(R.id.yelp_tv)
    TextView yelp_tv;

    @BindView(R.id.name_of_product_tv)
    TextView name_of_product_tv;

    @BindView(R.id.category_name_tv)
    TextView category_name_tv;

    @BindView(R.id.ratingBar1)
    ImageView ratingBar1;

    @BindView(R.id.ratingBar2)
    ImageView ratingBar2;

    @BindView(R.id.ratingBar3)
    ImageView ratingBar3;

    @BindView(R.id.ratingBar4)
    ImageView ratingBar4;

    @BindView(R.id.ratingBar5)
    ImageView ratingBar5;

    @BindView(R.id.product_Address_tv)
    TextView product_Address_tv;

    @BindView(R.id.description_tv)
    TextView description_tv;

    @BindView(R.id.close_iv)
    ImageView close_iv;

    @BindView(R.id.yelp_ll)
    LinearLayout yelp_ll;

    @BindView(R.id.web_ll)
    LinearLayout web_ll;

    @BindView(R.id.ratingBar_ll)
    LinearLayout ratingBar_ll;

    @OnClick(R.id.yelp_ll)
    void onClickyelp_ll(View v) {
        openBrowser(mainLocationDatum.getYelpLink());
    }

    private void openBrowser(String url) {
        if (url == null || url.isEmpty()) {
            return;
        }
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.web_ll)
    void onClickweb_ll(View v) {
        openBrowser(mainLocationDatum.getWebLink());

    }

    @OnClick(R.id.close_iv)
    void onclickclose_iv(View view) {
//        dismiss();
//        getFragmentManager().popBackStackImmediate();

        List<Fragment> fragmentList = getFragmentManager().getFragments();
// You might have to access all the fragments by their tag,
// in which case just follow the line below to remove the fragment
        if (fragmentList == null) {
            // code that handles no existing fragments
            return;
        }

        for (Fragment frag : fragmentList) {
            // To save any of the fragments, add this check
            // a tag can be added as a third parameter to the fragment when you commit it
            if (frag != null && frag.getTag() != null && frag.getTag().equalsIgnoreCase("LocationInfoDialog")) {
                getFragmentManager().beginTransaction().remove(frag).commit();
                break;
            }

        }
//        getFragmentManager().popBackStack();

    }


    private static final String TAG = LocationInfoDialog.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.location_info_dialog_layout, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    private MainLocationDatum mainLocationDatum;

    @Override
    protected void setUp(View view) {

        hideKeyboard();

        if (getArguments() != null) {
            if (getArguments().containsKey(Constant.LOCATION_DATUM)) {
                mainLocationDatum = (MainLocationDatum) getArguments().getSerializable(Constant.LOCATION_DATUM);
                if (mainLocationDatum != null) {
                    name_of_product_tv.setText(mainLocationDatum.getBusinessName());
                    category_name_tv.setText(mainLocationDatum.getCategory());
                    description_tv.setText(mainLocationDatum.getDescription());

                    if (mainLocationDatum.getAddress() != null) {
                        product_Address_tv.setText(mainLocationDatum.getAddress().getFormattedAddress());
                    }

                  //  ratingBar.setRating(mainLocationDatum.getRating());

                    if (mainLocationDatum.getImages() != null && mainLocationDatum.getImages().size() > 0) {
                        String imgUrl = mainLocationDatum.getImages().get(0);
                        Glide.
                                with(getActivity()).
                                load(imgUrl)
                                .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.default_user_pop_up))
                                .into(product_iv);
                    }
                    if (mainLocationDatum.getType() == 2) {
                        ratingBar_ll.setVisibility(View.GONE);
                        yelp_ll.setVisibility(View.INVISIBLE);
                    } else {
                        ratingBar_ll.setVisibility(View.VISIBLE);
                        yelp_ll.setVisibility(View.VISIBLE);
                    }
                } else {
                    showMessage(R.string.some_error);
                }

                if (mainLocationDatum.getRating() == 0) {
                    ratingBar1.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar2.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar3.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar4.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar5.setImageResource(R.drawable.rating_bar_blank);

                } else if (mainLocationDatum.getRating() == 0.5) {
                    ratingBar1.setImageResource(R.drawable.rating_bar_first_half);
                    ratingBar2.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar3.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar4.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar5.setImageResource(R.drawable.rating_bar_blank);

                } else if (mainLocationDatum.getRating() == 1) {
                    ratingBar1.setImageResource(R.drawable.rating_bar_first_fill);
                    ratingBar2.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar3.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar4.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar5.setImageResource(R.drawable.rating_bar_blank);

                } else if (mainLocationDatum.getRating() == 1.5) {
                    ratingBar1.setImageResource(R.drawable.rating_bar_first_fill);
                    ratingBar2.setImageResource(R.drawable.rating_bar_first_half);
                    ratingBar3.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar4.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar5.setImageResource(R.drawable.rating_bar_blank);

                } else if (mainLocationDatum.getRating() == 2) {
                    ratingBar1.setImageResource(R.drawable.rating_bar_first_fill);
                    ratingBar2.setImageResource(R.drawable.rating_bar_second_fill);
                    ratingBar3.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar4.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar5.setImageResource(R.drawable.rating_bar_blank);

                } else if (mainLocationDatum.getRating() == 2.5) {
                    ratingBar1.setImageResource(R.drawable.rating_bar_first_fill);
                    ratingBar2.setImageResource(R.drawable.rating_bar_second_fill);
                    ratingBar3.setImageResource(R.drawable.rating_bar_second_half);
                    ratingBar4.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar5.setImageResource(R.drawable.rating_bar_blank);

                } else if (mainLocationDatum.getRating() == 3) {
                    ratingBar1.setImageResource(R.drawable.rating_bar_first_fill);
                    ratingBar2.setImageResource(R.drawable.rating_bar_second_fill);
                    ratingBar3.setImageResource(R.drawable.rating_bar_third_fill);
                    ratingBar4.setImageResource(R.drawable.rating_bar_blank);
                    ratingBar5.setImageResource(R.drawable.rating_bar_blank);

                } else if (mainLocationDatum.getRating() == 3.5) {
                    ratingBar1.setImageResource(R.drawable.rating_bar_first_fill);
                    ratingBar2.setImageResource(R.drawable.rating_bar_second_fill);
                    ratingBar3.setImageResource(R.drawable.rating_bar_third_fill);
                    ratingBar4.setImageResource(R.drawable.rating_bar_third_half);
                    ratingBar5.setImageResource(R.drawable.rating_bar_blank);

                } else if (mainLocationDatum.getRating() == 4) {
                    ratingBar1.setImageResource(R.drawable.rating_bar_first_fill);
                    ratingBar2.setImageResource(R.drawable.rating_bar_second_fill);
                    ratingBar3.setImageResource(R.drawable.rating_bar_third_fill);
                    ratingBar4.setImageResource(R.drawable.rating_bar_fourth_fill);
                    ratingBar5.setImageResource(R.drawable.rating_bar_blank);

                } else if (mainLocationDatum.getRating() == 4.5) {
                    ratingBar1.setImageResource(R.drawable.rating_bar_first_fill);
                    ratingBar2.setImageResource(R.drawable.rating_bar_second_fill);
                    ratingBar3.setImageResource(R.drawable.rating_bar_third_fill);
                    ratingBar4.setImageResource(R.drawable.rating_bar_fourth_fill);
                    ratingBar5.setImageResource(R.drawable.rating_bar_fourth_half);

                } else if (mainLocationDatum.getRating() == 5) {
                    ratingBar1.setImageResource(R.drawable.rating_bar_first_fill);
                    ratingBar2.setImageResource(R.drawable.rating_bar_second_fill);
                    ratingBar3.setImageResource(R.drawable.rating_bar_third_fill);
                    ratingBar4.setImageResource(R.drawable.rating_bar_fourth_fill);
                    ratingBar5.setImageResource(R.drawable.rating_bar_fifth_fill);

                }

            }
        }
        setFonts();
    }

    private void setFonts() {
        description_tv.setTypeface(CommonUtils.setRegularFont(getActivity()));
        name_of_product_tv.setTypeface(CommonUtils.setBoldFont(getActivity()));
        web_tv.setTypeface(CommonUtils.setBoldFont(getActivity()));
        yelp_tv.setTypeface(CommonUtils.setBoldFont(getActivity()));
        category_name_tv.setTypeface(CommonUtils.setBoldFont(getActivity()));
        product_Address_tv.setTypeface(CommonUtils.setBoldFont(getActivity()));
    }

}