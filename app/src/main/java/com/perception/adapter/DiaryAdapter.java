package com.perception.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.perception.DiaryActivity;
import com.perception.DiaryViewActivity;
import com.perception.R;
import com.perception.TriggersActivity;
import com.perception.model.Diary;

import java.util.ArrayList;


public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.Mholder> {

    private Context mContext;
    ArrayList<Diary> mDiaryList;


    // Adapter for the recycler view (list view)
    public DiaryAdapter(Context mContext, ArrayList<Diary> mDiaryList) {

        this.mContext = mContext;
        this.mDiaryList = mDiaryList;

    }

    // On create set dairy list
    @Override
    public Mholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_list_item, parent, false);
        return new Mholder(view);
    }

    // Deleting and editing the diary data
    @Override
    public void onBindViewHolder(Mholder holder, final int position) {

        int counter = position + 1;
        holder.txtCounter.setText(counter + "");
        holder.txtMood.setText(mDiaryList.get(position).getIntense());
        holder.txtDateTime.setText(mDiaryList.get(position).getDate() + " " + mDiaryList.get(position).getTime());
        holder.txtLocation.setText(mDiaryList.get(position).getLocation());
        holder.txtHappened.setText(mDiaryList.get(position).getHappened());
        holder.txtNegative.setText(mDiaryList.get(position).getNegative());
        holder.txtAdvice.setText(mDiaryList.get(position).getAdvice());

        String feel = mDiaryList.get(position).getFeel().trim();
        if (feel.matches("1")) {
            holder.imgFeel.setImageResource(R.drawable.icon_cry);
        } else if (feel.matches("2")) {
            holder.imgFeel.setImageResource(R.drawable.icon_terrible);
        } else if (feel.matches("3")) {
            holder.imgFeel.setImageResource(R.drawable.icon_natural);
        } else if (feel.matches("4")) {
            holder.imgFeel.setImageResource(R.drawable.icon_smile);

        } else if (feel.matches("5")) {
            holder.imgFeel.setImageResource(R.drawable.icon_happy);
        }

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ((DiaryViewActivity) mContext).doDeleteData(mDiaryList.get(position).getId());
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((DiaryActivity) mContext).doEditData(mDiaryList.get(position));
            }
        });


    }

    // Get list item count
    @Override
    public int getItemCount() {

        return mDiaryList.size();
    }

    public class Mholder extends RecyclerView.ViewHolder {

        private TextView txtCounter;
        private ImageView imgFeel;
        private TextView txtMood;
        private TextView txtDateTime;
        private TextView txtLocation;
        private TextView txtHappened;
        private TextView txtNegative;
        private TextView txtAdvice;
        private ImageView imgEdit;
        private ImageView imgDelete;


        public Mholder(View itemView) {
            super(itemView);

            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            txtCounter = (TextView) itemView.findViewById(R.id.txtCounter);

            imgFeel = (ImageView) itemView.findViewById(R.id.imgFeel);
            txtMood = (TextView) itemView.findViewById(R.id.txtMood);
            txtDateTime = (TextView) itemView.findViewById(R.id.txtDateTime);
            txtLocation = (TextView) itemView.findViewById(R.id.txtLocation);
            txtHappened = (TextView) itemView.findViewById(R.id.txtHappened);
            txtNegative = (TextView) itemView.findViewById(R.id.txtNegative);
            txtAdvice = (TextView) itemView.findViewById(R.id.txtAdvice);


        }
    }
}

