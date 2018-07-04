/*
    教师教授课程列表适配器
 */
package com.example.kevinlee.examinsurance.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.activity.TeacherUpload_activity;
import com.example.kevinlee.examinsurance.model.Teaching;

import java.util.List;

/**
 * Created by zheny on 2018/5/28.
 */

public class TeachCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Teaching> courseList;
    private Context context;
    public enum ITEM_TYPE{
        INCOMPLETED,
        COMPLETED
    }

    //未上传过成绩单的课程的列表单元
    static class IncompletedViewHolder extends RecyclerView.ViewHolder{
        TextView course_info;
        Button upload_action;
        public IncompletedViewHolder(View view){
            super(view);
            course_info=(TextView) view.findViewById(R.id.incompleted_course);
            upload_action=(Button) view.findViewById(R.id.upload_action);
        }
    }

    //已上传成绩单的课程的列表单元
    static class CompletedViewHolder extends RecyclerView.ViewHolder{
        TextView course_info;
        public CompletedViewHolder(View view){
            super(view);
            course_info=(TextView) view.findViewById(R.id.completed_course);
        }
    }

    public TeachCourseAdapter(List<Teaching> list,Context _context){
        courseList=list;
        this.context=_context;
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
        final Teaching course=courseList.get(position);
        if(holder instanceof IncompletedViewHolder){
            ((IncompletedViewHolder) holder).course_info.setText(course.getCourse_id()+" "+course.getCourse_title());
            ((IncompletedViewHolder) holder).upload_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String course_id=course.getCourse_id();
                    Intent intent=new Intent(view.getContext(), TeacherUpload_activity.class);
                    intent.putExtra("course_id",course_id);
                    context.startActivity(intent);
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