package com.example.kevinlee.examinsurance.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.model.Teaching;

import java.util.List;

/**
 * Created by zheny on 2018/5/28.
 */

public class TeachCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Teaching> courseList;
    public enum ITEM_TYPE{
        INCOMPLETED,
        COMPLETED
    }

    static class IncompletedViewHolder extends RecyclerView.ViewHolder{
        TextView course_info;
        Button upload_action;
        public IncompletedViewHolder(View view){
            super(view);
            course_info=(TextView) view.findViewById(R.id.incompleted_course);
            upload_action=(Button) view.findViewById(R.id.upload_action);
        }
    }

    static class CompletedViewHolder extends RecyclerView.ViewHolder{
        TextView course_info;
        public CompletedViewHolder(View view){
            super(view);
            course_info=(TextView) view.findViewById(R.id.completed_course);
        }
    }

    public TeachCourseAdapter(List<Teaching> list){
        courseList=list;
    }

    public int getItemViewType(int position) {
        switch(courseList.get(position).getStatus()){
            case 0:
                return ITEM_TYPE.INCOMPLETED.ordinal();
        }
        return ITEM_TYPE.COMPLETED.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        if(viewType==ITEM_TYPE.INCOMPLETED.ordinal()){
            return new IncompletedViewHolder(layoutInflater.inflate(R.layout.unit_incompleted_course,parent,false));
        }
        else{
            return new CompletedViewHolder(layoutInflater.inflate(R.layout.unit_completed_course,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Teaching course=courseList.get(position);
        if(holder instanceof IncompletedViewHolder){
            ((IncompletedViewHolder) holder).course_info.setText(course.getCourse_id()+" "+course.getCourse_title());
            ((IncompletedViewHolder) holder).upload_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder upload_file=new AlertDialog.Builder(view.getContext());
                    upload_file.setTitle("上传成绩单");
                    upload_file.setMessage("选择文件...");
                    upload_file.setCancelable(false);
                    upload_file.setPositiveButton("上传", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    upload_file.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                }
            });
        }
        else if(holder instanceof CompletedViewHolder){
            ((CompletedViewHolder) holder).course_info.setText(course.getCourse_id()+" "+course.getCourse_title());
        }
    }

    @Override
    public int getItemCount() {
        if(courseList==null)
            return 0;
        else
            return courseList.size();
    }
}
