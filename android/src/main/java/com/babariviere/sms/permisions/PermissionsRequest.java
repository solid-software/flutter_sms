package com.babariviere.sms.permisions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

import com.newtronlabs.easypermissions.EasyPermissions;
import com.newtronlabs.easypermissions.listeners.IPermissionsListener;

import java.util.Set;

/**
 * Created by Joan Pablo on 4/17/2018.
 */

class PermissionsRequest implements IPermissionsListener {
    private int id;
    private Activity activity;
    private String[] permissions;

    PermissionsRequest(int id, String[] permissions, Activity activity) {
        this.id = id;
        this.permissions = permissions;
        this.activity = activity;
    }

    int getId() {
        return this.id;
    }

    @TargetApi(Build.VERSION_CODES.M)
    void execute() {
        EasyPermissions.getInstance().requestPermissions(this);

        System.out.println("CHECK PERMISSION");
    }

    @Override
    public void onCompleted(Set<String> set, Set<String> set1) {
        System.out.println("PERMISION COMPLETE");
    }

    @Override
    public void onFailure(Throwable throwable) {
        System.out.println("PERMISION FAILURE");
    }
}
