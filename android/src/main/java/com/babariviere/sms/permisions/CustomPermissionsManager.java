package com.babariviere.sms.permisions;

import android.content.Context;
import android.widget.Toast;

import com.intentfilter.androidpermissions.PermissionManager;
import com.intentfilter.androidpermissions.models.DeniedPermission;
import com.intentfilter.androidpermissions.models.DeniedPermissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singleton;

public class CustomPermissionsManager {
    private final Context context;
    private List<String> requestedPermissions = new ArrayList<>();
    private final PermissionManager permissionManager;


    CustomPermissionsManager(Context context){
        this.context = context;
        permissionManager = PermissionManager.getInstance(context);
        permissionManager.getResultCode();
    }

    void checkAndRequestPermission(final List<String> permissions,final PermissionHandler permissionHandler){
        if (requestedPermissions.contains(permissions)) {
            return;
        }
        requestedPermissions.addAll(permissions);

        permissionManager.checkPermissions(permissions, new PermissionManager.PermissionRequestListener() {
            @Override
            public void onPermissionGranted() {
                requestedPermissions.removeAll(permissions);
                permissionHandler.callback();
                Toast.makeText(context, "Permissions Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(DeniedPermissions deniedPermissions) {
                requestedPermissions.removeAll(permissions);
                String deniedPermissionsText = "Denied: " + Arrays.toString(deniedPermissions.toArray());
                Toast.makeText(context, deniedPermissionsText, Toast.LENGTH_SHORT).show();

                for (DeniedPermission deniedPermission : deniedPermissions) {
                    if (deniedPermission.shouldShowRationale()) {
                        // Display a rationale about why this permission is required
                    }
                }
            }
        });
    }
}
