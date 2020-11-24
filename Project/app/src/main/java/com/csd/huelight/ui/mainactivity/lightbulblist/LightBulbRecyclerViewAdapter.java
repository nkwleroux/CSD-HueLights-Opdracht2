package com.csd.huelight.ui.mainactivity.lightbulblist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.csd.huelight.R;
import com.csd.huelight.data.LightBulb;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * TODO: Replace the implementation with code for your data type.
 */
public class LightBulbRecyclerViewAdapter extends RecyclerView.Adapter<LightBulbRecyclerViewAdapter.ViewHolder> {

    private final List<LightBulb> mValues;
    private LightBulbClickListener listener;

    public LightBulbRecyclerViewAdapter(List<LightBulb> items, LightBulbClickListener listener) {
        mValues = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lightbulb_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(holder.mItem.getName());
        holder.mOnView.setChecked(holder.mItem.isOn());

        Picasso.get()
                .load(R.drawable.lightbulb)
                .placeholder(R.drawable.ic_baseline_sync_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(holder.mIconView);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final ImageView mIconView;
        public final TextView mNameView;
        public final MaterialCheckBox mOnView;
        public LightBulb mItem;
        public LightBulbClickListener mLightBulbClickListener;

        public ViewHolder(View view, LightBulbClickListener lightBulbClickListener) {
            super(view);
            mView = view;
            mIconView = (ImageView) view.findViewById(R.id.iconIV);
            mNameView = (TextView) view.findViewById(R.id.nameTV);
            mOnView = (MaterialCheckBox) view.findViewById(R.id.onCB);
            mLightBulbClickListener = lightBulbClickListener;
            mView.setOnClickListener(

                    (v -> {
                if (mLightBulbClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mLightBulbClickListener.onClickPos(getAdapterPosition());
                    }
                }
            }));

            mOnView.setOnClickListener(

                    (v -> {
                if (mLightBulbClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mLightBulbClickListener.onCheckClick(position);
                    }
                }
            }));

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }

    }
}