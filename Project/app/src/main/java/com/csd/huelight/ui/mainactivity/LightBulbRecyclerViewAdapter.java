package com.csd.huelight.ui.mainactivity;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public LightBulbRecyclerViewAdapter(List<LightBulb> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lightbulb_item, parent, false);
        return new ViewHolder(view);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mIconView;
        public final TextView mNameView;
        public final MaterialCheckBox mOnView;
        public LightBulb mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIconView = (ImageView) view.findViewById(R.id.iconIV);
            mNameView = (TextView) view.findViewById(R.id.nameTV);
            mOnView = (MaterialCheckBox) view.findViewById(R.id.onCB);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}