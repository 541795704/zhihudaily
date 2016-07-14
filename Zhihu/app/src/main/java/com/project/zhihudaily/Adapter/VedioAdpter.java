package com.project.zhihudaily.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.zhihudaily.Bean.Vedio;
import com.project.zhihudaily.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

/**
 * Created by tian on 2016/7/7.
 */
public class VedioAdpter extends BaseAdapter{
    private List<Vedio> list;
    private Context context;
    private LayoutInflater inflater;
    private final MediaPlayer mediaPlayer;
    //表示当前正在播放的item的position，-1表示当前没有item的播放
    private int currentPlayPosition = -1;

    public VedioAdpter(List<Vedio> list, Context context){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        //建立MediaPlayer
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            @Override
            public void onPrepared(MediaPlayer mp){
                mediaPlayer.start();
            }
        });
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        MyViewHolder myViewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.vedio_listview_item, parent, false);
            myViewHolder = new MyViewHolder();
            myViewHolder.surfaceView = (SurfaceView) convertView.findViewById(R.id.surfaceview_vedio);
            myViewHolder.cover = (ImageView) convertView.findViewById(R.id.cover_vedio);
            myViewHolder.text = (TextView) convertView.findViewById(R.id.text_vedio);
            myViewHolder.passtime = (TextView) convertView.findViewById(R.id.passtime_vedio);
            convertView.setTag(myViewHolder);
        }else{
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        Vedio vedioItem = list.get(position);
        ViewGroup.LayoutParams params = myViewHolder.cover.getLayoutParams();
        params.width = vedioItem.getWidth();
        params.height = vedioItem.getHeight();
        myViewHolder.surfaceView.setLayoutParams(params);
        myViewHolder.cover.setLayoutParams(params);
        //下载图片
        Picasso.with(context).load(vedioItem.getCover()).into(myViewHolder.cover);
        //设置标题
        myViewHolder.text.setText(vedioItem.getText());
        myViewHolder.passtime.setText(vedioItem.getPasstime());
        Object tag = myViewHolder.cover.getTag();
        if(tag != null){
            Integer tag1 = (Integer) tag;
            if(currentPlayPosition == tag1 && tag != position){
                mediaPlayer.stop();
                currentPlayPosition = -1;
            }
        }
        //设置标记
        myViewHolder.cover.setTag(position);
        if(currentPlayPosition == position){
            //如果是想要播放的位置
            //隐藏图片
            myViewHolder.cover.setVisibility(View.INVISIBLE);
            //显示视频
            myViewHolder.surfaceView.setVisibility(View.VISIBLE);
            //重置视频
            mediaPlayer.reset();
            //给视频设置能播放的控件
            mediaPlayer.setDisplay(myViewHolder.surfaceView.getHolder());
            try{
                //添加地址
                mediaPlayer.setDataSource(context, Uri.parse(vedioItem.getVideoUrl()));
                mediaPlayer.prepareAsync();
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            //显示图片
            myViewHolder.cover.setVisibility(View.VISIBLE);
            myViewHolder.surfaceView.setVisibility(View.INVISIBLE);
        }
        //设置图片的点击事件，点击播发视频
        myViewHolder.cover.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                v.setVisibility(View.INVISIBLE);
                currentPlayPosition = (int) v.getTag();
                if(mediaPlayer != null && mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                //跟新界面
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public void stop(){
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    class MyViewHolder{
        SurfaceView surfaceView;
        ImageView cover;
        TextView text;
        TextView passtime;
    }
}
