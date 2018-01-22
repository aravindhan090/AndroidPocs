package com.miraldemoproject.uiretrofitrx.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miraldemoproject.R;
import com.miraldemoproject.uiretrofitrx.model.Rides;

import java.util.ArrayList;



public class RideAdapter extends RecyclerView.Adapter<RideAdapter.RideViewHolder> {

    ArrayList<Rides> mArrayList;
    Context mContext;

    public RideAdapter(ArrayList<Rides> ridesList, Context context) {
        this.mArrayList = ridesList;
        this.mContext = context;
    }

    @Override
    public RideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride_recycler_row, parent, false);
        return new RideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RideViewHolder holder, int position) {
        final Rides rideInfo = mArrayList.get(position);
        holder.mrideName.setText(rideInfo.getRideName());
        holder.mrideDes.setText(rideInfo.getRideDescrption());
        int image_id = mContext.getResources().getIdentifier(rideInfo.getRideImage(), "drawable",
                mContext.getPackageName());
        //holder.mridePicture.setImageResource(image_id);
        holder.mRelativeLayout.setBackgroundResource(image_id);

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class RideViewHolder extends RecyclerView.ViewHolder {

        private TextView mrideName, mrideDes;
        private ImageView mridePicture;
        private RelativeLayout mRelativeLayout;

        public RideViewHolder(View itemView) {
            super(itemView);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.card_layout);
            mrideName = (TextView) itemView.findViewById(R.id.ride_name);
            mrideDes = (TextView) itemView.findViewById(R.id.ride_description);
            //mridePicture=(ImageView) itemView.findViewById(R.id.ride_picture);

        }
    }
}
