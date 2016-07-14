package com.project.zhihudaily.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhihudaily.Bean.News;
import com.project.zhihudaily.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 16-7-5.
 */
public class HomeAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private Context context;
    private List<News> list;
    private OnRecyclerItmClickListene myRecyclerItm;

    public HomeAdapter(List<News> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_layout,parent,false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Picasso.with(context)
                .load(list.get(position).getImgurl())
                .placeholder(R.mipmap.ic_launcher)
                .into(((MyViewHolder)holder).image);
        ((MyViewHolder)holder).text.setText(list.get(position).getTitle());

        //将编号保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Item点击事件
     * @param v 相当于holder.itemView
     */
    @Override
    public void onClick(View v){
        myRecyclerItm.onItemClickListene(v, (Integer) v.getTag());
    }

    /**
     * 自定义Item的回调接口
     */
    public interface OnRecyclerItmClickListene{
        void onItemClickListene(View v, int position);
    }

    /**
     * 绑定Recycler的Item点击事件监听
     * @param onRecyclerItmClickListene
     */
    public void setOnRecyclerItmClickListene(OnRecyclerItmClickListene onRecyclerItmClickListene){
        this.myRecyclerItm = onRecyclerItmClickListene;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView text;
        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.card_image);
            text = (TextView) itemView.findViewById(R.id.card_text);
        }
    }
}
