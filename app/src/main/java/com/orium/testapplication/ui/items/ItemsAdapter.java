package com.orium.testapplication.ui.items;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orium.testapplication.R;
import com.orium.testapplication.model.Item;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.Holder> {

    private List<Item> mData;
    private final View.OnClickListener mClickListener;

    ItemsAdapter(List<Item> data, View.OnClickListener clickListener) {
        mData = data;
        mClickListener = clickListener;
        setHasStableIds(true);
    }

    @Override
    public ItemsAdapter.Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new Holder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(final ItemsAdapter.Holder holder, final int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private final View.OnClickListener mClickListener;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.iv_logo)
        ImageView ivImage;

        public Holder(final View itemView, final View.OnClickListener clickListener) {
            super(itemView);
            mClickListener = clickListener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(mClickListener);
        }

        public void bind(final Item item) {
            tvName.setText(item.getName());

            Glide.with(ivImage.getContext())
                    .load(item.getImage())
//                    .placeholder(R.mipmap.placeholder)
                    .centerCrop()
                    .crossFade()
                    .into(ivImage);
        }
    }
}
