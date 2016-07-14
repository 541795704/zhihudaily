package com.project.zhihudaily.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.zhihudaily.Bean.Collect;
import com.project.zhihudaily.R;

import java.util.List;

/**
 * Created by tian on 2016/7/11.
 */
public class CollectAdapter extends BaseAdapter{
    private List<Collect> list;
    private Context context;
    private LayoutInflater inflater;
    private MyListviewViewHolder myViewHolder;

    public CollectAdapter(List<Collect> list, Context context){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
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
        if(convertView == null){
            convertView = inflater.inflate(R.layout.collect_item, parent, false);
            myViewHolder = new MyListviewViewHolder();
            myViewHolder.title = ((TextView) convertView.findViewById(R.id.title_collect));
            convertView.setTag(myViewHolder);
        }else{
            myViewHolder = (MyListviewViewHolder) convertView.getTag();
        }
        myViewHolder.title.setText(list.get(position).getTitle());
        return convertView;
    }

    class MyListviewViewHolder{
        TextView title;
    }
}
