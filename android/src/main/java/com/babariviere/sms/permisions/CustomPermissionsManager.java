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
    private List<String> requestedPermissions = new ArrayList();
    private boolean hasPermission;
    private final PermissionManager permissionManager;


    CustomPermissionsManager(Context context){
        this.context = context;
        permissionManager = PermissionManager.getInstance(context);
    }

    boolean checkAndRequestPermission(final String permission){
        hasPermission = false;
        if (requestedPermissions.contains(permission)) return false;

        System.out.println("more and more");
        requestedPermissions.add(permission);

        permissionManager.checkPermissions(singleton(permission), new PermissionManager.PermissionRequestListener() {
            @Override
            public void onPermissionGranted() {
                requestedPermissions.remove(permission);
                hasPermission = true;
                Toast.makeText(context, "Permissions Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(DeniedPermissions deniedPermissions) {
                requestedPermissions.remove(permission);
                hasPermission = false;
                String deniedPermissionsText = "Denied: " + Arrays.toString(deniedPermissions.toArray());
                Toast.makeText(context, deniedPermissionsText, Toast.LENGTH_SHORT).show();

                for (DeniedPermission deniedPermission : deniedPermissions) {
                    if (deniedPermission.shouldShowRationale()) {
                        // Display a rationale about why this permission is required
                    }
                }
            }
        });
        return hasPermission;
    }
}
