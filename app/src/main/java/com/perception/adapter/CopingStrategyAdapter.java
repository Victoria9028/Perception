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
import com.perception.model.CopingStrategy;

import java.util.ArrayList;

// Adapter for the recycler view (list view)
public class CopingStrategyAdapter extends RecyclerView.Adapter<CopingStrategyAdapter.Mholder> {

    private Context mContext;
    ArrayList<CopingStrategy> mCopingStrategyList;


    public CopingStrategyAdapter(Context mContext, ArrayList<CopingStrategy> mCopingStrategyList) {

        this.mContext = mContext;
        this.mCopingStrategyList = mCopingStrategyList;

    }

    // On create set coping strategy list
    @Override
    public Mholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coping_startagy_list_item, parent, false);
        return new Mholder(view);
    }

    // Deleting and editing the coping strategy data
    @Override
    public void onBindViewHolder(Mholder holder, final int position) {

        int counter=position+1;
        holder.txtCounter.setText(counter+"");
        holder.txtMassage.setText(mCopingStrategyList.get(position).getMessage());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ((TriggersActivity) mContext).doDeleteData(mCopingStrategyList.get(position).getId());
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((TriggersActivity) mContext).doEditData(mCopingStrategyList.get(position));
            }
        });




    }

    // Get list item count
    @Override
    public int getItemCount() {

        return mCopingStrategyList.size();
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
