package com.example.kevinlee.examinsurance.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.activity.TeachCourse_activity;
import com.example.kevinlee.examinsurance.activity.UserInfo_activity;
import com.example.kevinlee.examinsurance.activity.UserInstruction_activity;

/**
 * Created by zheny on 2018/5/27.
 */

public class TeacherPageColumnAdapter extends RecyclerView.Adapter<TeacherPageColumnAdapter.ViewHolder> {
    private final String[] text_column=new String[]{
            "个人信息",
            "教授课程",
            "使用说明"
    };

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView user_page_column;
        public ViewHolder(View v){
            super(v);
            view=v;
            user_page_column=(TextView) v.findViewById(R.id.user_page_column);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_user_page_list,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                switch (position){
                    case 0:
                        Intent info_intent = new Intent(v.getContext(),UserInfo_activity.class);
                        v.getContext().startActivity(info_intent);
                        break;
                    case 1:
                        Intent account_intent = new Intent(v.getContext(),TeachCourse_activity.class);
                        v.getContext().startActivity(account_intent);
                        break;
                    case 2:
                        Intent history_intent = new Intent(v.getContext(),UserInstruction_activity.class);
                        v.getContext().startActivity(history_intent);
                        break;
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(TeacherPageColumnAdapter.ViewHolder holder, int position) {
        holder.user_page_column.setText(text_column[position]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
