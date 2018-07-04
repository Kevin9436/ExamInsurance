/*
    学生保险购买记录适配器
 */
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinlee.examinsurance.R;
import com.example.kevinlee.examinsurance.connectServer.api.RequestBuilder;
import com.example.kevinlee.examinsurance.connectServer.bean.ApplyReq;
import com.example.kevinlee.examinsurance.connectServer.bean.ApplyRes;
import com.example.kevinlee.examinsurance.connectServer.bean.BasicCallModel;
import com.example.kevinlee.examinsurance.model.Order;
import com.example.kevinlee.examinsurance.utils.Netutils;
import com.example.kevinlee.examinsurance.utils.SharedData;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kevin Lee on 2018/5/15.
 */

public class StudentHistoryAdapter extends RecyclerView.Adapter<StudentHistoryAdapter.ViewHolder>{
    private List<Order> orderList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView order_id;
        TextView order_status;
        Button order_action;
        public ViewHolder(View view){
            super(view);
            order_id=(TextView) view.findViewById(R.id.user_history_id);
            order_status=(TextView) view.findViewById(R.id.user_history_status);
            order_action=(Button) view.findViewById(R.id.user_history_action);
        }
    }

    public StudentHistoryAdapter(List<Order> _orderList){
        orderList=_orderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_student_history,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.order_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                if(orderList.get(position).getStatus()==0){
                    apply_action(view.getContext(),position);
                }
                else{
                    check_action(view.getContext(),position);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order=orderList.get(position);
        holder.order_id.setText(order.getId()+" "+order.getTitle());
        if(order.getStatus()==0){
            holder.order_status.setText("进行中");
            holder.order_action.setText("申诉");
        }
        else{
            holder.order_status.setText("已完成");
            holder.order_action.setText("查看");
        }
    }

    @Override
    public int getItemCount() {
        if(orderList==null)
            return 0;
        else
            return orderList.size();
    }

    //向后台申请赔付，返回学生课程成绩和奖励金额
    private void apply_action(final Context context, final int position){
        final Order order=orderList.get(position);
        final AlertDialog.Builder apply_dialog=new AlertDialog.Builder(context);
        apply_dialog.setTitle("投保记录");
        String msg;
        if(order.getType()==0)
            msg="高分奖励\n奖励分数："+order.getThreshold();
        else
            msg="低分赔偿\n赔偿分数："+order.getThreshold();
        apply_dialog.setMessage(order.getId()+" "+order.getTitle()+"\n"+
                                "类型："+msg+"\n"+
                                "投保(元)：1");
        apply_dialog.setCancelable(false);
        apply_dialog.setPositiveButton("申诉", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Netutils.isNetworkConnected(context)){
                    final ProgressDialog applying=ProgressDialog.show(context,
                            "申诉中","请等待...",true,false);
                    ApplyReq req=new ApplyReq();
                    req.setSudentId(SharedData.student.getId());
                    req.setCourseId(order.getId());
                    Gson gson=new Gson();
                    String route=gson.toJson(req);
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), route);
                    Call<BasicCallModel<ApplyRes>> cb = RequestBuilder.buildRequest().applyReq(body);
                    cb.enqueue(new Callback<BasicCallModel<ApplyRes>>() {
                        @Override
                        public void onResponse(Call<BasicCallModel<ApplyRes>> call, Response<BasicCallModel<ApplyRes>> response) {
                            if(response.raw().code()==200){
                                if(response.body().errno==0){
                                    String courseId=response.body().data.getCourseId();
                                    int courseScore=response.body().data.getScore();
                                    int reward=response.body().data.getReward();
                                    SharedData.student.change_score(courseId,courseScore);
                                    SharedData.student.charge(reward);
                                    applying.dismiss();
                                    Toast.makeText(context, "申诉成功,最终成绩为"+courseScore+",获得回报"+reward+"元。",
                                            Toast.LENGTH_SHORT).show();
                                    //更新该记录的UI
                                    notifyItemChanged(position,1);
                                }
                                else{
                                    applying.dismiss();
                                    Toast.makeText(context, response.body().msg, Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                applying.dismiss();
                                Toast.makeText(context,"请求失败",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<BasicCallModel<ApplyRes>> call, Throwable t) {
                            applying.dismiss();
                            Toast.makeText(context,"请求失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(context,"无网络连接",Toast.LENGTH_SHORT).show();
                }
            }
        });
        apply_dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        apply_dialog.show();
    }

    //查看已申诉过的课程，直接从SharedData中获取数据
    private void check_action(Context context,int position){
        Order order=orderList.get(position);
        AlertDialog.Builder check_dialog=new AlertDialog.Builder(context);
        check_dialog.setTitle("投保记录");
        String msg1;
        String msg2;
        if(order.getType()==0){
            msg1="高分奖励\n奖励分数："+order.getThreshold();
            if(order.getScore()>=order.getThreshold()) msg2 = "5";
            else msg2 = "0";
        }
        else{
            msg1="低分赔偿\n赔偿分数："+order.getThreshold();
            if(order.getScore()<=order.getThreshold()) msg2 = "3";
            else msg2="0";
        }
        check_dialog.setMessage(order.getId()+" "+order.getTitle()+"\n"+
                                "类型："+msg1+"\n最终成绩："+order.getScore()+"\n"+
                                "投保(元)：1\n回报(元)："+msg2);
        check_dialog.setCancelable(false);
        check_dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        check_dialog.show();
    }
}
