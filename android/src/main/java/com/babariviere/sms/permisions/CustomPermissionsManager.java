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
    private List<String> requestedPermissions = new ArrayList<>();
    private final PermissionManager permissionManager;


    CustomPermissionsManager(Context context){
        permissionManager = PermissionManager.getInstance(context);
    }

    void checkAndRequestPermission(final List<String> permissions,final PermissionHandler permissionHandler){
        if(requestedPermissions.containsAll(permissions)){
            System.out.println("Wait for accepting");
            return;
        }
        requestedPermissions.addAll(permissions);

        permissionManager.checkPermissions(permissions, new PermissionManager.PermissionRequestListener() {
            @Override
            public void onPermissionGranted() {
                requestedPermissions.removeAll(permissions);
                permissionHandler.callback();
            }

            @Override
            public void onPermissionDenied(DeniedPermissions deniedPermissions) {
                requestedPermissions.removeAll(permissions);
                String deniedPermissionsText = "Denied: " + Arrays.toString(deniedPermissions.toArray());
                for (DeniedPermission deniedPermission : deniedPermissions) {
                    if (deniedPermission.shouldShowRationale()) {
                        // Display a rationale about why this permission is required
                    }
                }
            }
        });
    }
}
