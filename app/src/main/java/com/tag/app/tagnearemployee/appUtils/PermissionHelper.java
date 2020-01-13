package com.tag.app.tagnearemployee.appUtils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class PermissionHelper
{
    public static final int PERMISSION_REQUEST_CODE = 01;
    public static final int PERMISSIONS_REQUEST_LOCATION = 400;
    /**
     * The Context.
     */
    Context context;

    /**
     * The Current activity.
     */
    Activity current_activity;

    /**
     * Fragment current
     */
    Fragment fragment;

    private PermissionResultCallback permissionResultCallback;

    /**
     * The Permission list.
     */
    ArrayList<String> permission_list = new ArrayList<>();

    /**
     * The List permissions needed.
     */
    ArrayList<String> listPermissionsNeeded = new ArrayList<>();

    /**
     * The Dialog content.
     */
    String dialog_content = "";

    /**
     * The Req code.
     */
    int req_code;



    public PermissionHelper(Activity activity) {
        this.current_activity = activity;
    }


    public PermissionHelper(Context context, PermissionResultCallback callback) {
        this.context = context;
        this.current_activity = (Activity) context;

        permissionResultCallback = callback;
    }

    public boolean isPermissionGranted(String permission) {
        if (fragment != null) {
            return ContextCompat.checkSelfPermission(fragment.getContext(), permission) == PackageManager.PERMISSION_GRANTED; }
        else { return ContextCompat.checkSelfPermission(current_activity.getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED;
        } }

    public boolean isAllPermissionGranted(String... permissions)
    {   for (String permission : permissions) {
        if (!isPermissionGranted(permission))
        { return false; } }
        return true; }

    public void checkAndRequestPermissions(int requestCode,String... permissions)
    {   if (collectDeniedPermissions(permissions).length > 0)
    { requestPermissions(requestCode, collectDeniedPermissions(permissions)); } }

    private String[] collectDeniedPermissions(String... permissions)
    {   ArrayList<String> deniedPermissionsList = new ArrayList<>();
        for (String permission : permissions) {
            if (!isPermissionGranted(permission))
            { deniedPermissionsList.add(permission); } }
        return deniedPermissionsList.toArray(new String[deniedPermissionsList.size()]); }

    public ArrayList<String> collectDeniedPermissionsFomResult(String permissions[], int[] grantResults)
    {   ArrayList<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED)
            { deniedPermissions.add(permissions[i]); } }
        return deniedPermissions; }

    public void requestPermissions(int requestCode, String... permissions)
    {   if (fragment!=null) {
        Log.v("Permissions","request Permissions for fragment"+ fragment.getClass().getSimpleName());
        fragment.requestPermissions(permissions, requestCode); }
    else {
        Log.v("Permissions","request Permissions for activity " + current_activity.getClass().getSimpleName());
        ActivityCompat.requestPermissions(current_activity, permissions, requestCode); } }

    /* Custom methods*/
    public boolean isAllLocationPermissionGranted()
    { return isAllPermissionGranted( Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE); }

    public void requestAllPermissionForLocation()
    { checkAndRequestPermissions(PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE); }

    public boolean isAllReadSMSPermissionGranted()
    { return isAllPermissionGranted(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS); }

    public void requestAllPermissionForReadSMS()
    { checkAndRequestPermissions(PERMISSION_REQUEST_CODE, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS); }


    public  final int PERMISSIONS_REQUEST_CAMERA = 300;

    public  boolean is_CAMERA_PermissionGranted()
    {   if( Build.VERSION.SDK_INT >= 23)
    { return  (ContextCompat.checkSelfPermission(fragment.getContext(),Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED); }
    else
    { // Permission is granted by default
        return true; } }


    public  boolean is_LOCATION_PermissionGranted()
    {   if (Build.VERSION.SDK_INT >= 23)
    { return (ContextCompat.checkSelfPermission(fragment.getContext(),Manifest.permission.ACCESS_FINE_LOCATION )==PackageManager.PERMISSION_GRANTED);
    }
    else
    { // Permission is granted by default
        return true; } }

    public  boolean is_PHONE_STATE_PermissionGranted()
    {   if(Build.VERSION.SDK_INT >= 23)
    { return  (ContextCompat.checkSelfPermission(fragment.getContext(),Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED); }
    else
    { // Permission is granted by default
        return true; }}

    /**
     * Check the API Level & Permission
     *
     * @param permissions    the permissions
     * @param dialog_content the dialog content
     * @param request_code   the request code
     */
    public void check_permission(ArrayList<String> permissions, String dialog_content, int request_code) {
        this.permission_list = permissions;
        this.dialog_content = dialog_content;
        this.req_code = request_code;

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkAndRequestPermission(permissions, request_code)) {
                permissionResultCallback.PermissionGranted(request_code);
                Log.i("all permissions", "granted");
                Log.i("proceed", "to callback");
            }
        } else {
            permissionResultCallback.PermissionGranted(request_code);

            Log.i("all permissions", "granted");
            Log.i("proceed", "to callback");
        }

    }

    /**
     * The interface Permission result callback.
     */
    public interface PermissionResultCallback {
        /**
         * Permission granted.
         *
         * @param request_code the request code
         */
        void PermissionGranted(int request_code);

        /**
         * Partial permission granted.
         *
         * @param request_code        the request code
         * @param granted_permissions the granted permissions
         */
        void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions);

        /**
         * Permission denied.
         *
         * @param request_code the request code
         */
        void PermissionDenied(int request_code);

        /**
         * Never ask again.
         *
         * @param request_code the request code
         */
        void NeverAskAgain(int request_code);
    }


    /**
     * Check and request the Permissions
     *
     * @param permissions
     * @param request_code
     * @return
     */

    private boolean checkAndRequestPermission(ArrayList<String> permissions, int request_code) {

        if (permissions.size() > 0) {
            listPermissionsNeeded = new ArrayList<>();

            for (int i = 0; i < permissions.size(); i++) {
                int hasPermission = ContextCompat.checkSelfPermission(current_activity, permissions.get(i));

                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(permissions.get(i));
                }

            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(current_activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), request_code);
                return false;
            }
        }

        return true;
    }


}
