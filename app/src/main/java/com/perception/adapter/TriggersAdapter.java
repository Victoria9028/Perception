package com.perception.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.perception.R;
import com.perception.TriggersActivity;
import com.perception.model.Triggers;

import java.util.ArrayList;

// Adapter for the recycler view (list view)
public class TriggersAdapter extends RecyclerView.Adapter<TriggersAdapter.Mholder> {

    private Context mContext;
    ArrayList<Triggers> mTriggersList;


    public TriggersAdapter(Context mContext, ArrayList<Triggers> mTriggersList) {

        this.mContext = mContext;
        this.mTriggersList = mTriggersList;

    }

    // On create set triggers list in recycler view
    @Override
    public Mholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.triger_list_item, parent, false);
        return new Mholder(view);
    }

    // Deleting and editing the triggers list in recycler view
    @Override
    public void onBindViewHolder(Mholder holder, final int position) {

        int counter=position+1;
        holder.txtCounter.setText(counter+"");
        holder.txtMassage.setText(mTriggersList.get(position).getMessage());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TriggersActivity) mContext).doDeleteData(mTriggersList.get(position).getId());
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TriggersActivity) mContext).doEditData(mTriggersList.get(position));
            }
        });




    }

    // Get list item count
    @Override
    public int getItemCount() {

        return mTriggersList.size();
    }

    public class Mholder extends RecyclerView.ViewHolder {

        private ImageView imgDelete;
        private ImageView imgEdit;
        private TextView txtMassage;
        private TextView txtCounter;


        public Mholder(View itemView) {
            super(itemView);

            imgDelete = (ImageView)itemView.findViewById( R.id.imgDelete );
            imgEdit = (ImageView) itemView.findViewById( R.id.imgEdit );
            txtMassage = (TextView)itemView.findViewById( R.id.txtMassage );
            txtCounter = (TextView)itemView.findViewById( R.id.txtCounter );

        }
    }
}
