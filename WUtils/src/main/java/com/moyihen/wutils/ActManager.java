package com.moyihen.wutils;

import android.app.Activity;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.LinkedList;
import java.util.List;

/**
 * 创建日期：2021/4/28 9:09
 *
 * @author moyihen
 * 包名： com.moyihen.informationcollection.utils
 * 类说明：
 */
public class ActManager {

    private static volatile ActManager sActManager;

    private List<SoftReference<Activity>> activityList = new LinkedList<>();

    public static ActManager getInstance() {
        if (sActManager == null) {
            synchronized (ActManager.class) {
                if (sActManager == null)
                    sActManager = new ActManager();
            }
        }
        return sActManager;
    }


    /** 添加act到软应用容器
     * @param softReference .
     */
    public void addAct(SoftReference<Activity> softReference){
        activityList.add(softReference);
    }


    /**
     * 遍历所有act finish
     */
    public void exit(){
        Log.i("TT", "exit: 111111111111111111111111111111111111111111111111");
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i).get();
            if (null!=activity){
                activity.finish();
                Log.i("TT", "exit: "+i +activity.getClass().getSimpleName()+"关闭" );
            }
        }

       // System.exit(0);
    }

    /**
     * 保留登录页  退出其他
     * @param s
     * ActManager.getInstance().exit(getClass().getSimpleName());
     * ActManager.getInstance().addAct(new SoftReference<>(this));
     */
    public void exit(String s) {
        Log.i("TT", "exit: "+s +"SIZE:"+activityList.size());
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i).get();
            if (null!=activity){
                if (activity.getClass().getSimpleName().equals(s)){
                    Log.i("TT", "exit:跳过主页 "+i+activity.getClass().getSimpleName());
                }else {
                    activity.finish();
                    Log.i("TT", "exit: "+i +activity.getClass().getSimpleName()+"关闭" );
                }

            }
        }
    }
}
