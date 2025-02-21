package com.arcis.vgil.autologout;

import android.app.Application;

public class ApplockManager {
    private static ApplockManager instance;
    private DefaultApplock currentAppLocker;

    public static ApplockManager getInstance() {
        if (instance == null) {
            instance = new ApplockManager();
        }
        return instance;
    }

    public  void enableDefaultAppLockIfAvailable(Application currentApp) {

       // currentAppLocker = new DefaultApplock(currentApp);

    }

    public void updateTouch(){
        currentAppLocker.updateTouch();
    }
}
