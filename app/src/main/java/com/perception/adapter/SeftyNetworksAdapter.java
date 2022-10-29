package com.perception.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.perception.R;
import com.perception.SeftyNetworkActivity;
import com.perception.model.SeftyNetwork;

import java.util.ArrayList;


public class SeftyNetworksAdapter extends RecyclerView.Adapter<SeftyNetworksAdapter.Mholder> {

    private Context mContext;
    ArrayList<SeftyNetwork> mSeftyNetworkList;

    // Adapter for the recycler view (list view)
    public SeftyNetworksAdapter(Context mContext, ArrayList<SeftyNetwork> mSeftyNetworkList) {

        this.mContext = mContext;
        this.mSeftyNetworkList = mSeftyNetworkList;

    }
    // On create set safety network list
    @Override
    public Mholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sefty_network_list_item, parent, false);
        return new Mholder(view);
    }

    // Deleting and editing the safety network list in recycler view
    @Override
    public void onBindViewHolder(Mholder holder, final int position) {

        int counter = position + 1;
        holder.txtCounter.setText(counter + "");
        holder.txtName.setText("Name : "+mSeftyNetworkList.get(position).getName());
        holder.txtEmail.setText("Email : "+mSeftyNetworkList.get(position).getEmail());
        holder.txtMobile.setText("Mobile : "+mSeftyNetworkList.get(position).getMobile());



        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SeftyNetworkActivity) mContext).doDeleteData(mSeftyNetworkList.get(position).getId());
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SeftyNetworkActivity) mContext).doEditData(mSeftyNetworkList.get(position));
            }
        });


    }

    // Get list item count
    @Override
    public int getItemCount() {

        return mSeftyNetworkList.size();
    }

    public class Mholder extends RecyclerView.ViewHolder {

        private ImageView imgDelete;
        private ImageView imgEdit;
        private TextView txtName;
        private TextView txtEmail;
        private TextView txtMobile;

        private TextView txtCounter;


        public Mholder(View itemView) {
            super(itemView);

            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);
            txtMobile = (TextView) itemView.findViewById(R.id.txtMobile);

            txtCounter = (TextView) itemView.findViewById(R.id.txtCounter);

        }
    }
}
