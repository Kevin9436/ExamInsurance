package com.example.kevinlee.examinsurance.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.connectServer.api.RequestBuilder;
import com.example.kevinlee.examinsurance.connectServer.bean.BasicCallModel;
import com.example.kevinlee.examinsurance.connectServer.bean.PurchaseReq;
import com.example.kevinlee.examinsurance.model.Course;
import com.example.kevinlee.examinsurance.model.Order;
import com.example.kevinlee.examinsurance.utils.Netutils;
import com.example.kevinlee.examinsurance.utils.SharedData;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kevin Lee on 2018/5/12.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private List<Course> courseList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView course_title;
        TextView course_id;
        RadioButton course_choose_upper;
        RadioButton course_choose_lower;
        TextView course_upper;
        TextView course_lower;
        Button course_purchase;

        public ViewHolder(View view){
            super(view);
            course_title=(TextView) view.findViewById(R.id.course_title);
            course_id=(TextView) view.findViewById(R.id.course_id);
            course_choose_upper=(RadioButton) view.findViewById(R.id.course_choose_upper);
            course_choose_lower=(RadioButton) view.findViewById(R.id.course_choose_lower);
            course_upper=(TextView) view.findViewById(R.id.course_upper);
            course_lower=(TextView) view.findViewById(R.id.course_lower);
            course_purchase=(Button) view.findViewById(R.id.course_purchase);
        }
    }

    public CourseAdapter(List<Course> _courseList){
        courseList=_courseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.course_unit,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.course_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.course_choose_upper.isChecked()){
                    int position = holder.getAdapterPosition();
                    purchase_action(position,view.getContext(),0);
                }
                else if(holder.course_choose_lower.isChecked()){
                    int position = holder.getAdapterPosition();
                    purchase_action(position,view.getContext(),1);
                }
                else{
                    Toast.makeText(view.getContext(),"请选择保险类型",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(CourseAdapter.ViewHolder holder, int position) {
        Course course=courseList.get(position);
        holder.course_title.setText(course.getTitle());
        holder.course_id.setText(course.getId());
        holder.course_upper.setText(course.getUpper_threshold());
        holder.course_lower.setText(course.getLower_threshold());
    }

    @Override
    public int getItemCount() {
        if(courseList==null)
            return 0;
        else
            return courseList.size();
    }

    private void purchase_action(int position, final Context context, final int type){
        final Course course=courseList.get(position);
        //设置弹出的AlertDialog
        AlertDialog.Builder purchase_confirm = new AlertDialog.Builder(context);
        purchase_confirm.setTitle("已选择保险");
        String msg;
        if(type==0){
            msg="\n类型：高分奖励\n奖励分数："+course.getUpper_threshold();
        }
        else{
            msg="\n类型：低分赔偿\n赔偿分数："+course.getLower_threshold();
        }
        purchase_confirm.setMessage("课程："+course.getId()+" "+course.getTitle()+msg+"\n花费：1元");
        purchase_confirm.setCancelable(false);

        purchase_confirm.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //自动关闭
            }
        });

        purchase_confirm.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(SharedData.student.getAccount()<1){
                    Toast.makeText(context,"余额不足",Toast.LENGTH_SHORT).show();
                }
                else {
                    Order order = new Order();
                    order.setId(course.getId());
                    order.setTitle(course.getTitle());
                    order.setStatus(0);
                    order.setType(type);
                    if(type==0){
                        order.setThreshold(course.getUpper_threshold());
                    }
                    else{
                        order.setThreshold(course.getLower_threshold());
                    }
                    if (!Netutils.isNetworkConnected(context)) {
                        Toast.makeText(context, "无网络连接", Toast.LENGTH_SHORT).show();
                    } else {
                        final ProgressDialog purchasing = ProgressDialog.show(context,
                                "购买提交中", "请等待", true, false);
                        //封装购买请求
                        PurchaseReq req=new PurchaseReq();
                        req.setStudentId(SharedData.student.getId());
                        req.setOrder(order);
                        Gson gson = new Gson();
                        String route = gson.toJson(req);
                        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), route);
                        Call<BasicCallModel<Order>> cb = RequestBuilder.buildRequest().purchaseReq(body);
                        cb.enqueue(new Callback<BasicCallModel<Order>>() {
                            @Override
                            public void onResponse(Call<BasicCallModel<Order>> call, Response<BasicCallModel<Order>> response) {
                                if (response.raw().code() == 200) {
                                    if (response.body().errno==0) {
                                        purchasing.dismiss();
                                        SharedData.student.add_item(response.body().data);
                                        SharedData.student.purchase(1);
                                        Toast.makeText(context, "购买成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        purchasing.dismiss();
                                        Toast.makeText(context, "购买失败", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    purchasing.dismiss();
                                    Toast.makeText(context, "购买失败", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<BasicCallModel<Order>> call, Throwable t) {
                                purchasing.dismiss();
                                Toast.makeText(context, "购买失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
}
