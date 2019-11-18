package com.tv.GreenGrubBox.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tv.GreenGrubBox.BaseClasses.BaseDialog;
import com.tv.GreenGrubBox.BaseClasses.DialogInterface;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.utils.Constant;
import com.tv.GreenGrubBox.utils.Views.ImageViewTouchViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/**
 * Created by tv-1 on 08/02/18.
 */

public class ImagesDetailDialog extends BaseDialog {
    @Override
    public void showLoadingGif() {

    }

    @Override
    public void showDialog(String title, String msg1, String msg2, int type, DialogInterface mDialogInterface) {

    }

    private List<String> mImagesArray = new ArrayList<>();

    @Override
    protected void setUp(View view) {

        if (getArguments() != null) {
            if (getArguments().containsKey(Constant.IMAGES_LIST)) {

                mImagesArray = getArguments().getStringArrayList(Constant.IMAGES_LIST);
                CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getActivity());
                img_vp.setAdapter(mCustomPagerAdapter);
            }
        }

    }

    @BindView(R.id.img_vp)
    ImageViewTouchViewPager img_vp;

    @BindView(R.id.close_iv)
    ImageView close_iv;

    @OnClick(R.id.close_iv)
    void onClickclose_iv(View v) {
        dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.images_details_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mImagesArray.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item_images_zoom, container, false);

            ImageViewTouch imageView = itemView.findViewById(R.id.imageView);
            Glide.
                    with(mContext).
                    load(mImagesArray.get(position)).apply(RequestOptions.centerCropTransform()
                    .placeholder(R.drawable.default_user_pop_up))
                    .into(imageView);


           /* Glide.
                    with(mContext)
                    .load(mImagesArray.get(position))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            imageView.setBackgroundDrawable(resource);
                        }
                    });*/

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}