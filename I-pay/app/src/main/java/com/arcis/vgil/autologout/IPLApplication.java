package com.arcis.vgil.autologout;

import android.app.Application;

import com.arcis.vgil.autologout.ApplockManager;


public class IPLApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ApplockManager.getInstance().enableDefaultAppLockIfAvailable(this);
    }


    public void touch() {
        ApplockManager.getInstance().updateTouch();
    }

}
