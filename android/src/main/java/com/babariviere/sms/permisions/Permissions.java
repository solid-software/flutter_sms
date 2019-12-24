package com.babariviere.sms.permisions;

import android.app.Activity;
import android.content.Context;
import android.os.Build;


import java.util.Arrays;
import java.util.List;

import io.flutter.plugin.common.PluginRegistry;


/**
 * Created by babariviere on 08/03/18.
 */

public class Permissions {
    public static final int RECV_SMS_ID_REQ = 1;
    public static final int SEND_SMS_ID_REQ = 2;
    public static final int READ_SMS_ID_REQ = 3;
    public static final int READ_CONTACT_ID_REQ = 4;
    public static final int BROADCAST_SMS = 5;
    public static final int READ_PHONE_STATE = 6;
    private static final PermissionsRequestHandler requestsListener = new PermissionsRequestHandler();
    private final CustomPermissionsManager customPermissionsManager;
    public Permissions(Context context) {
        customPermissionsManager = new CustomPermissionsManager(context);
    }

    private void hasPermission(final String permission, PermissionHandler permissionHandler) {
        customPermissionsManager.checkAndRequestPermission(permission,permissionHandler);
    }

    private void hasPermissions(String[] permissions, PermissionHandler permissionHandler) {
        for (String perm : permissions) {
            hasPermission(perm,permissionHandler);
        }
    }

    public static PluginRegistry.RequestPermissionsResultListener getRequestsResultsListener() {
        return requestsListener;
    }

    public void checkAndRequestPermission(String[] permissions, int id, PermissionHandler pemissionHandler) {
        final List<String> list = Arrays.asList(permissions);
        hasPermissions(permissions,pemissionHandler);
    }
}
