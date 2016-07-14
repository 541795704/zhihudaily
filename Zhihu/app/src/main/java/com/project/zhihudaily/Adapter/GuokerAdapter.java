package com.project.zhihudaily.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.zhihudaily.Bean.GuokerBean;
import com.project.zhihudaily.R;

import java.util.List;

/**
 * Created by Thinkpad on 2016/7/5.
 */
public class GuokerAdapter extends RecyclerView.Adapter{

    private List<GuokerBean> list;
    private Context context;
    private LayoutInflater inflater;

    public GuokerAdapter(List<GuokerBean> list, Context context){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    //初始化ViewHolder，类似于BaseAdapter中getView方法的if(){}功能
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = inflater.inflate(R.layout.guoker_recycleview_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    //数据绑定，类似于BaseAdapter中getView方法中数据绑定的功能
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        ((MyViewHolder) holder).tvTitle.setText(list.get(position).getTitle());//绑定title
        Glide.with(context).load(list.get(position).getHeadline_img()).centerCrop().into(((MyViewHolder) holder).imageView);
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView tvTitle;

        //itemView表示布局文件
        public MyViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.guokr_img);
            tvTitle = (TextView) itemView.findViewById(R.id.guokr_title);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    //定义接口
    public interface OnItemClickListener{
        public void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
