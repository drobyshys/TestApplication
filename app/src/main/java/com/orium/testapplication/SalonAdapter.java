package com.orium.testapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orium.testapplication.model.Salon;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SalonAdapter extends RecyclerView.Adapter<SalonAdapter.Holder> {

    private List<Salon> mData;

    public SalonAdapter(List<Salon> data) {
        mData = data;
    }

    @Override
    public SalonAdapter.Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_salon, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final SalonAdapter.Holder holder, final int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_website)
        TextView tvWebsite;
        @Bind(R.id.iv_logo)
        ImageView ivImage;

        public Holder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Salon salon) {
            tvName.setText(salon.getName());
            tvWebsite.setText(salon.getWebsite());

            Glide.with(ivImage.getContext())
                    .load(salon.getImage())
                    .placeholder(R.mipmap.placeholder)
                    .crossFade()
                    .into(ivImage);
        }
    }
}
