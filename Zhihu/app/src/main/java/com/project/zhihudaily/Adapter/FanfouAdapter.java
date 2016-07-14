package com.project.zhihudaily.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.project.zhihudaily.Bean.FanfouBean;
import com.project.zhihudaily.R;

import java.util.List;

/**
 * Created by Thinkpad on 2016/7/6.
 */
public class FanfouAdapter extends RecyclerView.Adapter{

    private List<FanfouBean> list;
    private Context context;
    private LayoutInflater inflater;

    public FanfouAdapter(Context context, List<FanfouBean> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = inflater.inflate(R.layout.fanfou_recycle_item, parent, false);
        FanfouviewHolder viewHolder = new FanfouviewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        FanfouBean fanfouBean = list.get(position);
        ((FanfouviewHolder) holder).avatar.setImageURI(Uri.parse(fanfouBean.getAvatarurl()));
        ((FanfouviewHolder) holder).realname.setText(fanfouBean.getUsername());
        ((FanfouviewHolder) holder).msg.setText(fanfouBean.getMsg());
        ((FanfouviewHolder) holder).time.setText(fanfouBean.getTime());
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    class FanfouviewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView avatar;
        private TextView realname, msg, time;

        public FanfouviewHolder(View itemView){
            super(itemView);
            avatar = (SimpleDraweeView) itemView.findViewById(R.id.avatar);//加载圆形图片
            realname = (TextView) itemView.findViewById(R.id.realname);
            msg = (TextView) itemView.findViewById(R.id.msg);
            time = (TextView) itemView.findViewById(R.id.time);
            //给itemView设置点击事件
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null){
                        onItemClickListener.onItemclick(getLayoutPosition());
                    }
                }
            });
        }
    }

    //定义接口
    public interface OnItemClickListener{
        void onItemclick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
