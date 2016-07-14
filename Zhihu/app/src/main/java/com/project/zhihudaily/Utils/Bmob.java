package com.project.zhihudaily.Utils;

import android.content.Context;

import com.project.zhihudaily.Bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/7/8.
 */
public class Bmob{

    private static Boolean bool = false;
    private static User user = new User();

    /**
     * 查询
     */
    public static User query(String name, Context context){

        BmobQuery<User> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("name", name);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        //query.setLimit(50);
        //执行查询方法
        query.findObjects(context, new FindListener<User>(){
            @Override
            public void onSuccess(List<User> object){
                if(object.size() == 0){

                }else{
                    //获得用户信息
                    user.setId(object.get(0).getObjectId());
                    user.setName(object.get(0).getName());
                    user.setPwd(object.get(0).getPwd());
                }
            }

            @Override
            public void onError(int code, String msg){
                // TODO Auto-generated method stub
//                Toast.makeText(LoginActivity.this, ("查询失败：" + msg), Toast.LENGTH_SHORT).show();
            }
        });
        return user;
    }

    /**
     * 修改
     */
    public static void alter(){
    }

    /**
     * 删除
     */
    public static void delete(){
    }

    /**
     * 增加
     */
    public static Boolean increase(String name,String pwd,Context context){

        User user = new User();
        user.setName(name);
        user.setPwd(pwd);
        user.save(context, new SaveListener(){
            @Override
            public void onSuccess(){
                bool = true;
            }

            @Override
            public void onFailure(int i, String s){
                bool = false;
            }
        });
        return bool;
    }
}
