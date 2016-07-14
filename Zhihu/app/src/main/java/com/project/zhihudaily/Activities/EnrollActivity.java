package com.project.zhihudaily.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.zhihudaily.Bean.User;
import com.project.zhihudaily.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class EnrollActivity extends AppCompatActivity{
    private EditText enroll_name;
    private EditText enroll_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        enroll_name = (EditText) findViewById(R.id.enroll_edit_name);
        enroll_pwd = (EditText) findViewById(R.id.enroll_edit_pwd);
    }

    public void enroll(View view){
        if(TextUtils.isEmpty(enroll_name.getText())||TextUtils.isEmpty(enroll_pwd.getText())){
            Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
        }else{
            BmobQuery<User> query = new BmobQuery<>();
            //查询name的数据
            query.addWhereEqualTo("name", enroll_name.getText().toString());
            //执行查询方法
            query.findObjects(this, new FindListener<User>(){
                @Override
                public void onSuccess(List<User> object){
                    if(object.size() == 0){
                        User user = new User();
                        user.setName(enroll_name.getText().toString());
                        user.setPwd(enroll_pwd.getText().toString());
                        user.save(EnrollActivity.this, new SaveListener(){
                            @Override
                            public void onSuccess(){
                                Toast.makeText(EnrollActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("name", enroll_name.getText().toString());
                                EnrollActivity.this.setResult(2, intent);
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s){
                                Toast.makeText(EnrollActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(EnrollActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(int code, String msg){
                }
            });
        }
    }


}
