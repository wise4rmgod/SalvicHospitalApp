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

        public TextView fullname, age, gender;

        public MyViewHolder(View view) {
            super(view);
            //initialize buttons and TextViews
          age =  view.findViewById(R.id.listage);
          fullname =  view.findViewById(R.id.listfullname);
           gender =  view.findViewById(R.id.listgender);

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

        final PatientClass patient = commentsClassadapter.get(position);
//        holder.date.setText(commentsClass.getDate());
          holder.fullname.setText(patient.getFullname());
          holder.age.setText(patient.getAge());
          holder.gender.setText(patient.getSex());

    }

    @Override
    public int getItemCount() {
        return commentsClassadapter.size();
    }
}