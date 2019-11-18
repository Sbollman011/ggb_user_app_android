package com.tv.GreenGrubBox.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tv.GreenGrubBox.BaseClasses.BaseViewHolder;
import com.tv.GreenGrubBox.R;
import com.tv.GreenGrubBox.data.modal.MainLocationDatum;
import com.tv.GreenGrubBox.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 25/1/18.
 */

public class LocationItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<MainLocationDatum> mainLocationDatumList;
    private Activity mActivity;

    public List<MainLocationDatum> getAllList() {
        return this.mainLocationDatumList;
    }

    public class MyViewHolder extends BaseViewHolder {
        @BindView(R.id.food_name_tv)
        TextView food_name_tv;

        @BindView(R.id.food_location_tv)
        TextView food_location_tv;

        @BindView(R.id.miles_tv)
        TextView miles_tv;

        @BindView(R.id.vendor_icon_iv)
        ImageView vendor_icon_iv;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            food_name_tv.setTypeface(CommonUtils.setRegularFont(mActivity));
            food_location_tv.setTypeface(CommonUtils.setRegularFont(mActivity));
            miles_tv.setTypeface(CommonUtils.setRegularFont(mActivity));
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            MainLocationDatum bean = mainLocationDatumList.get(position);

            if (bean != null) {
                food_name_tv.setText(bean.getBusinessName());
                food_location_tv.setText(bean.getCategory());

                if (bean.getImages() != null && bean.getImages().size() > 0) {
                    String imgUrl = bean.getImages().get(0);
                    Glide.
                            with(mActivity).
                            load(imgUrl)
                            .apply(RequestOptions.circleCropTransform()
                                    .placeholder(R.drawable.default_user_list))
                            .into(vendor_icon_iv);
                } else{
                    vendor_icon_iv.setImageResource(R.drawable.default_user_list);
                }

                miles_tv.setText(bean.getMiles());

            }
        }
    }

    public void clearList() {
        if (this.mainLocationDatumList != null) {
            this.mainLocationDatumList.clear();
        }
        notifyDataSetChanged();
    }

    public void clearAll() {
        mainLocationDatumList.clear();
    }

    public void updateItem(MainLocationDatum beans, int position) {
        mainLocationDatumList.set(position, beans);
        notifyDataSetChanged();
    }

    public void removeItem(int mPosition) {
        mainLocationDatumList.remove(mPosition);
        notifyItemRemoved(mPosition);
        notifyDataSetChanged();
    }

    public void addItems(List<MainLocationDatum> mMainLocationDatumList) {

        this.mainLocationDatumList.addAll(mMainLocationDatumList);
        notifyDataSetChanged();

    }

    public LocationItemAdapter(List<MainLocationDatum> mainLocationDatumList, Activity mActivity) {
        this.mainLocationDatumList = mainLocationDatumList;
        this.mActivity = mActivity;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mainLocationDatumList.size();
    }
}