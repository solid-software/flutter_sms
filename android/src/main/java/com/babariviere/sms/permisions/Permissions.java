package com.babariviere.sms.permisions;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import com.intentfilter.androidpermissions.PermissionManager;
import com.intentfilter.androidpermissions.models.DeniedPermission;
import com.intentfilter.androidpermissions.models.DeniedPermissions;

import java.util.Arrays;


import static java.util.Collections.singleton;

import io.flutter.plugin.common.PluginRegistry;


/**
 * Created by babariviere on 08/03/18.
 */

public class Permissions{
    public static final int RECV_SMS_ID_REQ = 1;
    public static final int SEND_SMS_ID_REQ = 2;
    public static final int READ_SMS_ID_REQ = 3;
    public static final int READ_CONTACT_ID_REQ = 4;
    public static final int BROADCAST_SMS = 5;
    public static final int READ_PHONE_STATE = 6;
    private static final PermissionsRequestHandler requestsListener = new PermissionsRequestHandler();
    private final PermissionManager permissionManager;
    private final Activity activity;
    private final Context context;
    boolean hasPermission;

    public Permissions(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        permissionManager = PermissionManager.getInstance(context);
    }

    private boolean hasPermission(String permission) {
        permissionManager.checkPermissions(singleton(permission), new PermissionManager.PermissionRequestListener() {
            @Override
            public void onPermissionGranted() {
                System.out.println(1);
                hasPermission = true;
                Toast.makeText(context, "Permissions Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(DeniedPermissions deniedPermissions) {
                System.out.println(2);
                hasPermission = false;
                String deniedPermissionsText = "Denied: " + Arrays.toString(deniedPermissions.toArray());
                Toast.makeText(context, deniedPermissionsText, Toast.LENGTH_SHORT).show();

                for (DeniedPermission deniedPermission : deniedPermissions) {
                    if(deniedPermission.shouldShowRationale()) {
                        // Display a rationale about why this permission is required
                    }
                }
            }
        });
        System.out.println(3);
        return hasPermission;
    }
    private boolean hasPermissions(String[] permissions) {
        for (String perm : permissions) {
            if (!hasPermission(perm)) {
                return false;
            }
        }
        return true;
    }

    public static PluginRegistry.RequestPermissionsResultListener getRequestsResultsListener() {
        return requestsListener;
    }

    public boolean checkAndRequestPermission(String[] permissions, int id) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (!hasPermissions(permissions)) {
            PermissionsRequestHandler.requestPermissions(
                    new PermissionsRequest(id, permissions, activity)
            );
            return false;
        }
        return true;
    }
}
