package com.yollpoll.framework.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yollpoll.framework.log.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by spq on 2021/3/3
 */
public class PermissionTool {
    static final String TAG = "LegoPermissions";

    LegoPermissionsFragment nPermissionsFragment;
    String[] nPermissions;
    public PermissionTool(@NonNull Activity activity) {
        nPermissionsFragment = getPermissionsFragment(activity);
    }

    private LegoPermissionsFragment getPermissionsFragment(Activity activity) {
        LegoPermissionsFragment permissionsFragment = findPermissionsFragment(activity);
        if (permissionsFragment == null) {
            permissionsFragment = new LegoPermissionsFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(permissionsFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return permissionsFragment;
    }

    private LegoPermissionsFragment findPermissionsFragment(Activity activity) {
        return (LegoPermissionsFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsFromFragment(String[] permissions, RequestPermissionListener listener) {
        nPermissionsFragment.requestPermissions(permissions, listener);
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsFromFragment(RequestPermissionListener listener) {
        nPermissionsFragment.requestPermissions(nPermissions, listener);
    }
    /**
     * 设置注解中需要请求的权限
     * @param permissions
     */
    public void setAnnotationPermissions(String[] permissions){
        nPermissions = permissions;
    }

    public void checkAllPermission(RequestPermissionListener listener){
        if(nPermissions!=null){
            List<String> deniedPermissions = new ArrayList<>();
            List<String> grantedPermissions = new ArrayList<>();
            for(String permission:nPermissions){
                if(isGranted(permission)){
                    grantedPermissions.add(permission);
                }else {
                    deniedPermissions.add(permission);
                }
            }
            if(listener!=null){
                listener.onPermissionAllowed(grantedPermissions.toArray(new String[grantedPermissions.size()]));
                listener.onPermissionDenied(deniedPermissions.toArray(new String[deniedPermissions.size()]));
            }
        }
    }
    boolean isGranted(String permission) {
        return !isMarshmallow() || nPermissionsFragment.isGranted(permission);
    }


    boolean isRevoked(String permission) {
        return isMarshmallow() && nPermissionsFragment.isRevoked(permission);
    }

    boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 是否存在有永久拒绝的权限
     * @param perms
     * @return
     */
    public static boolean somePermissionPermanentlyDenied(@NonNull Activity activity,@NonNull String[] perms) {
        for (String deniedPermission : perms) {
            if (permissionPermanentlyDenied(activity,deniedPermission)) {
                return true;
            }
        }

        return false;
    }
    /**
     * 权限是否永久拒绝，即用户选择了拒绝并且不再提醒
     * @param perms
     * @return
     */
    public static boolean permissionPermanentlyDenied(@NonNull Activity activity,@NonNull String perms) {

        return !ActivityCompat.shouldShowRequestPermissionRationale(activity, perms);
    }

    /**
     * 权限是否永久拒绝，即用户选择了拒绝并且不再提醒
     * @param perms
     * @return
     */
    public boolean permissionPermanentlyDenied(@NonNull String perms) {

        return !ActivityCompat.shouldShowRequestPermissionRationale(nPermissionsFragment.getActivity(), perms);
    }


    /**
     * @author : yuanbingbing
     * @time : 2018/5/30 16:11
     */
    public static class LegoPermissionsFragment extends Fragment {

        private static final int PERMISSIONS_REQUEST_CODE = 42;
        private HashMap<Integer, RequestPermissionListener> nPermissionListenerMap = new HashMap<>();


        public LegoPermissionsFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        public static boolean hasPermissions(Context context, @NonNull String[] permissions) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return true;
            } else if (context == null) {
                throw new IllegalArgumentException("Can't check permissions for null context");
            } else {
                int var3 = permissions.length;

                for (int i = 0; i < var3; ++i) {
                    String perm = permissions[i];
                    if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                        return false;
                    }
                }

                return true;
            }
        }

        /**
         * 申请指定权限
         *
         * @param permissions
         */
        @TargetApi(Build.VERSION_CODES.M)
        public void requestPermissions(String[] permissions, RequestPermissionListener listener) {
            if (permissions == null || permissions.length == 0) {
//                if (listener != null) listener.onPermissionAllAllowed();
                return;
            }
            //随机生成请求标识码
            int requestCode = new Random().nextInt(1000);
            synchronized (this) {
                //向用户申请指定权限
                if (!hasPermissions(getActivity(), permissions)) {

                    //android 6.0 动态申请
                    if (isMarshmallow()) {
                        removeRepeatListener(listener);
                        nPermissionListenerMap.put(requestCode, listener);
                        requestPermissions(permissions, requestCode);
                        // ActivityCompat.requestPermissions(getActivity(), shouldRequestPermission.toArray(new String[shouldRequestPermission.size()]), requestCode);
                    } else {
//                        listener.onPermissionDenied(shouldRequestPermission.toArray(new String[shouldRequestPermission.size()]));
                    }
                } else {
                    //申请的权限，用户已经授权
                    listener.onPermissionAllowed(permissions);
                }
            }
        }

        /**
         * 移除重复的监听器
         */
        private void removeRepeatListener(RequestPermissionListener listener) {
            if (nPermissionListenerMap.containsValue(listener)) {
                Set<Map.Entry<Integer, RequestPermissionListener>> entrySet = nPermissionListenerMap.entrySet();
                List<Integer> keys = new ArrayList<>();
                Iterator<Map.Entry<Integer, RequestPermissionListener>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Integer, RequestPermissionListener> next = iterator.next();
                    if (next.getValue() == listener || next.getValue().equals(listener)) {
                        keys.add(next.getKey());
                    }
                }
                for (Integer key : keys) {
                    nPermissionListenerMap.remove(key);
                }
            }
        }

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (nPermissionListenerMap.containsKey(requestCode)) {
                synchronized (this) {

                    RequestPermissionListener listener = nPermissionListenerMap.remove(requestCode);

                    List<String> deniedPermissions = new ArrayList<>();
                    List<String> grantedPermissions = new ArrayList<>();
                    List<String> shouldShowPermissions = new ArrayList<>();
                    for (int i = 0; i < permissions.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            String permission = permissions[i];
                            deniedPermissions.add(permission);
                            //用户已拒绝，但没有点击不再提示，则可弹框解释说明
                            if (shouldShowRequestPermissionRationale(permission)) {
                                shouldShowPermissions.add(permission);
                                LogUtils.d("shouldShowPermissions:permission"+permission);
                            }
                        } else {
                            grantedPermissions.add(permissions[i]);
                        }
                    }
                    if (deniedPermissions.size() > 0) {
                        if (shouldShowPermissions.size()>0) {
                            //用户已禁用授权提示Dialog，需要弹出自定义对话框，解释原因
                            listener.onShowRationale(shouldShowPermissions.toArray(new String[shouldShowPermissions.size()]));
                        } else {
                            //用户拒绝授权
                            listener.onPermissionDenied(deniedPermissions.toArray(new String[deniedPermissions.size()]));
                        }
                    }
                    if (grantedPermissions.size() > 0) {
                        listener.onPermissionAllowed(grantedPermissions.toArray(new String[grantedPermissions.size()]));
                    }

                }
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            nPermissionListenerMap.clear();
            nPermissionListenerMap = null;
        }

        @TargetApi(Build.VERSION_CODES.M)
        boolean isGranted(String permission) {
            return ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED;
        }

        @TargetApi(Build.VERSION_CODES.M)
        boolean isRevoked(String permission) {
            return getActivity().getPackageManager().isPermissionRevokedByPolicy(permission, getActivity().getPackageName());
        }


        boolean isMarshmallow() {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        }

    }
}
