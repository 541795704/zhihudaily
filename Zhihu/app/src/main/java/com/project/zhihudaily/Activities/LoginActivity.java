package com.project.zhihudaily.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.zhihudaily.Bean.User;
import com.project.zhihudaily.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity{
    private EditText edit_name;
    private EditText edit_pwd;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_name = (EditText) findViewById(R.id.login_edit_name);
        edit_pwd = (EditText) findViewById(R.id.login_edit_pwd);
        user = new User();

    }

    public void login(View view){

        if(TextUtils.isEmpty(edit_name.getText())||TextUtils.isEmpty(edit_pwd.getText())){
            Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
        }
        else{
            BmobQuery<User> query = new BmobQuery<>();
            //查询name的数据
            query.addWhereEqualTo("name", edit_name.getText().toString().trim());
            //执行查询方法
            query.findObjects(this, new FindListener<User>(){
                @Override
                public void onSuccess(List<User> object){
                    if(object.size() == 0){
                        Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                    }
                    else if(object.get(0).getPwd().equals(edit_pwd.getText().toString().trim())){
                        Intent intent = new Intent();
                        intent.putExtra("name", edit_name.getText().toString().trim());
                        setResult(2, intent);
                        finish();
                    }
                    else {
                        Log.d("LoginActivity", "密码错误");
                    }
                }

                @Override
                public void onError(int code, String msg){
                    //                Toast.makeText(LoginActivity.this, ("查询失败：" + msg), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void enroll(View view){
        Intent intent = new Intent(this,EnrollActivity.class);
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2&&resultCode==2){
            Intent intent = new Intent();
            intent.putExtra("name",data.getStringExtra("name"));
            this.setResult(1,intent);
            finish();
        }

    }
}
