package com.github.evan.common_utils_demo.designMode;

import com.github.evan.common_utils_demo.event.SingletonEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Evan on 2018/9/15.
 */

public class Singleton {

    /**
     * 懒汉式,初始化成员变量时就声明mInstance, final关键字保证mInstance只能被赋值一次，无法更改引用对象, 私有化构造函数保证无法通过new关键字创建对象。
     */
    public static class LazySingleton{
        private static final LazySingleton mInstance = new LazySingleton();

        private LazySingleton(){

        }

        public static LazySingleton getInstance(){
            return mInstance;
        }

    }


    public static class HungrySingleton{
        private static HungrySingleton mInstance = null;

        private HungrySingleton(){

        }

        public static HungrySingleton getInstance(){
            String log = "called getInstance() method！ Thread ID: " + Thread.currentThread().getId();
            EventBus.getDefault().post(new SingletonEvent(log));
            if(null == mInstance){
                //第一个null判断，为了提高效率，如果mInstance已经初始化，后续的调用判断不为null，直接返回
                EventBus.getDefault().post("mInstance == null");
                synchronized (HungrySingleton.class){
                    EventBus.getDefault().post(new SingletonEvent("进入同步代码块"));
                    //多线程操作加锁
                    if(null == mInstance){
                        //第一次初始化new出对象
                        mInstance = new HungrySingleton();
                        EventBus.getDefault().post(new SingletonEvent("创建mInstance并返回"));
                        return mInstance;
                    }
                }
            }

            EventBus.getDefault().post(new SingletonEvent("mInstance已创建，直接返回"));
            return mInstance;
        }

        public static void clearInstance(){
            mInstance = null;
        }

    }


}
