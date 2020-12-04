package com.csd.huelight.ui.mainactivity.lightbulblist;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.csd.huelight.R;
import com.csd.huelight.data.LightBulb;
import com.csd.huelight.ui.mainactivity.LightBulbViewModel;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * TODO: Replace the implementation with code for your data type.
 */
public class LightBulbRecyclerViewAdapter extends RecyclerView.Adapter<LightBulbRecyclerViewAdapter.ViewHolder> {

    private static final String LOGTAG = LightBulbRecyclerViewAdapter.class.getName();

    private final List<LightBulb> mValues;
    private LightBulbClickListener listener;
    private LightBulbViewModel lightBulbViewModel;

    public LightBulbRecyclerViewAdapter(List<LightBulb> items, LightBulbClickListener listener, LightBulbViewModel lightBulbViewModel) {
        this.mValues = items;
        this.listener = listener;
        this.lightBulbViewModel = lightBulbViewModel;
    }


    public void updateLightBulbs(List<LightBulb> lightBulbs) {
        //ugliest code in the land
//        boolean updated = false;
//        for (LightBulb lightBulb : lightBulbs){
//            if (mValues.contains(lightBulb)){
//                for (LightBulb oldLightBulb : mValues){
//                    if (lightBulb.equals(oldLightBulb)){
//                        if (!lightBulb.completeEqual(oldLightBulb)){
//                            oldLightBulb.setSettings(lightBulb);
//                            Log.i(LOGTAG, "lightBulb " + oldLightBulb.getUID() + " changed");
//                            updated = true;
//                        }
//                        break;
//                    }
//                }
//            }else {
//                Log.i(LOGTAG, "lightBulb " + lightBulb.getUID() + " was added");
//                mValues.add(lightBulb);
//                updated = true;
//            }
//        }
//
//        if (updated){
//            notifyDataSetChanged();
//        }
//        Log.i(LOGTAG, "updating lightBulbs " + lightBulbs.size());

        mValues.clear();
        mValues.addAll(lightBulbs);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lightbulb_item, parent, false);
        return new ViewHolder(view, listener, lightBulbViewModel);
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
                .into(holder.mIconView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.mIconView.setColorFilter(holder.mItem.isOn() ? Color.HSVToColor(
                                new float[]{
                                        holder.mItem.getHue() / (float) Short.MAX_VALUE * 180f,
                                        holder.mItem.getSaturation() / 256f,
                                        holder.mItem.getBrightness() / 256f}) : Color.WHITE,
                                PorterDuff.Mode.MULTIPLY);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
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
        public LightBulbClickListener mLightBulbClickListener;

        public ViewHolder(View view, LightBulbClickListener lightBulbClickListener, LightBulbViewModel lightBulbViewModel) {
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
                                mItem.setOn(((MaterialCheckBox) v).isChecked());
                                lightBulbViewModel.setLightBulbState(mItem);
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