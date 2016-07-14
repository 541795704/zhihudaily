package com.project.zhihudaily.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.project.zhihudaily.R;

public class FanfouDetailActivity extends AppCompatActivity{

    private TextView msg;
    private TextView realname;
    private TextView time;
    private SimpleDraweeView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fanfou_detail);
        initView();
        initData();

    }

    private void initData(){
        Intent intent = getIntent();
        String delrealname = intent.getStringExtra("realname");
        String delavatar = intent.getStringExtra("avatar");
        String deltime = intent.getStringExtra("time");
        String delmsg = intent.getStringExtra("msg");
        msg.setText(delmsg);
        realname.setText(delrealname);
        time.setText(deltime);
        avatar.setImageURI(Uri.parse(delavatar));
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        msg = (TextView) findViewById(R.id.deatail_msg);
        realname = (TextView) findViewById(R.id.deatail_realname);
        time = (TextView) findViewById(R.id.deatail_time);
        avatar = (SimpleDraweeView) findViewById(R.id.deatail_vatar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
