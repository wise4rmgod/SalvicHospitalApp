package com.developer.wise4rmgod.salvichospitalapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.wise4rmgod.salvichospitalapp.R;
import com.developer.wise4rmgod.salvichospitalapp.model.PatientClass;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyViewHolder> {

    private List<PatientClass> commentsClassadapter;
    public Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView fullname, message, date,count_comment,count_likes;

        public MyViewHolder(View view) {
            super(view);
            //initialize buttons and TextViews
          //  message =  view.findViewById(R.id.listdesignergallerycommentmessage);
           // fullname =  view.findViewById(R.id.listdesignergalleryfullname);
            //date =  view.findViewById(R.id.listpostfeeddate);

        }
    }

    //constructor
    public PatientAdapter(Context mContext, List<PatientClass> commentsClassadapter) {
        this.commentsClassadapter = commentsClassadapter;
        this.mContext = mContext;
    }

    @Override
    public PatientAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //set layout to itemView using Layout inflater
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listpatient, parent, false);
        return new PatientAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PatientAdapter.MyViewHolder holder, final int position) {

        final PatientClass commentsClass = commentsClassadapter.get(position);
//        holder.date.setText(commentsClass.getDate());
     //   holder.fullname.setText(commentsClass.getFullname());
      //  holder.message.setText(commentsClass.getMessage());
        //  holder.city.setText(postfeedsClassadapter.size()+"");

    }

    @Override
    public int getItemCount() {
        return commentsClassadapter.size();
    }
}