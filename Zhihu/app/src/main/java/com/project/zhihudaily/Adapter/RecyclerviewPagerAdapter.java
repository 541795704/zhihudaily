package com.project.zhihudaily.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.zhihudaily.Bean.ThemeContent;
import com.project.zhihudaily.R;

import java.util.List;

/**
 * Created by tian on 2016/7/6.
 */
public class RecyclerviewPagerAdapter extends RecyclerView.Adapter{
    private List<ThemeContent> list;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public RecyclerviewPagerAdapter(List<ThemeContent> list, Context context){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.theme_recyclerview_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        ThemeContent content = list.get(position);
        //如何没有图片吧图片设置为gone,有图片就加载图片
        ImageView img = ((MyViewHolder) holder).img;
        if(content.getFirstImg() == null){
            img.setVisibility(View.GONE);
        }else{
            Glide.with(context).load(content.getFirstImg()).into(img);
        }
        ((MyViewHolder) holder).title.setText(content.getTitle());
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView title;
        private CardView cardView;

        public MyViewHolder(View itemView){
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img_recyclerview);
            title = (TextView) itemView.findViewById(R.id.title_recyclerview);
            cardView = (CardView) itemView.findViewById(R.id.cardview_recyclerview);
            //在点击事件中调用接口里的方法
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

    //定义一个接口
    public interface OnItemClickListener{
        public void onItemClick(int position);
    }

    //初始化接口变量
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
