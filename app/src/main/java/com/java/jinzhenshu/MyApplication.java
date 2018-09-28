package com.java.jinzhenshu;

import android.app.Application;

import com.java.jinzhenshu.untils.DaoUntils;
import com.java.jinzhenshu.untils.SPUtils;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DaoUntils.init(this);
        SPUtils.init(this);
        SPUtils.getInstance().set("checkbox_one",true);
        SPUtils.getInstance().set("checkbox_two",true);
        SPUtils.getInstance().set("checkbox_three",true);
        SPUtils.getInstance().set("checkbox_four",true);
        SPUtils.getInstance().set("checkbox_five",true);
        SPUtils.getInstance().set("checkbox_six",true);
        SPUtils.getInstance().set("checkbox_seven",true);
    }
}
