package com.yzt.cmljswebviewjiaohu;

/**
 * Created by chenmingliang on 2017/11/2.
 */

public class DemoBean {

    public String name;
    public Class<?> activityClass;

    public DemoBean(String name,Class activityClass){
        this.name = name;
        this.activityClass = activityClass;
    }

    @Override
    public String toString() {
        return name;
    }
}
