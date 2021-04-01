package com.yollpoll.framework.annotation.handler;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.yollpoll.framework.annotation.Permission;
import com.yollpoll.framework.annotation.PermissionAuto;
import com.yollpoll.framework.log.LogUtils;
import com.yollpoll.framework.mvvm.view.IBaseView;
import com.yollpoll.framework.permission.PermissionTool;
import com.yollpoll.framework.permission.RequestPermissionListener;
import com.yollpoll.framework.utils.AppUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by spq on 2021/3/4
 */
public class PermissionHandler {
    private static Map<Method, Permission> methodPermissionMap = new HashMap<>();
    private static List<String> dangerPermissions = new ArrayList<>();

    static {
        dangerPermissions.add(Manifest.permission.READ_CALENDAR);
        dangerPermissions.add(Manifest.permission.WRITE_CALENDAR);

        dangerPermissions.add(Manifest.permission.CAMERA);

        dangerPermissions.add(Manifest.permission.READ_CONTACTS);
        dangerPermissions.add(Manifest.permission.WRITE_CONTACTS);
        dangerPermissions.add(Manifest.permission.GET_ACCOUNTS);

        dangerPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        dangerPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);

        dangerPermissions.add(Manifest.permission.RECORD_AUDIO);

        dangerPermissions.add(Manifest.permission.READ_PHONE_STATE);
        dangerPermissions.add(Manifest.permission.CALL_PHONE);
        dangerPermissions.add(Manifest.permission.ADD_VOICEMAIL);
        dangerPermissions.add(Manifest.permission.USE_SIP);
        dangerPermissions.add(Manifest.permission.PROCESS_OUTGOING_CALLS);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            dangerPermissions.add(Manifest.permission.READ_CALL_LOG);
            dangerPermissions.add(Manifest.permission.WRITE_CALL_LOG);
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            dangerPermissions.add(Manifest.permission.BODY_SENSORS);
        }

        dangerPermissions.add(Manifest.permission.SEND_SMS);
        dangerPermissions.add(Manifest.permission.RECEIVE_SMS);
        dangerPermissions.add(Manifest.permission.READ_SMS);
        dangerPermissions.add(Manifest.permission.RECEIVE_WAP_PUSH);
        dangerPermissions.add(Manifest.permission.RECEIVE_MMS);


        dangerPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            dangerPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    /**
     * 收集类的注解
     *
     * @param method     加注解的方法
     * @param annotation 方法的注解
     */
    public void collectMethodPermission(Method method, Annotation annotation) {
        if (annotation instanceof Permission) {
            methodPermissionMap.put(method, (Permission) annotation);
        }
    }

    /**
     * 加载方法上的权限申请
     *
     * @param annotationOwner 当前注解的activity
     */
    public PermissionTool handleMethodPermission(Object annotationOwner) {
        Class clz = annotationOwner.getClass();
        Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(methodPermissionMap.containsKey(method)){
                    LogUtils.i("spq","before+"+method.getName());
                }
                return annotationOwner;
            }
        });


        for (Map.Entry<Method, Permission> entry : methodPermissionMap.entrySet()) {
            //获取所有缓存的注解
            Permission permission = entry.getValue();
            //注解上面的权限列表
            String[] permissions = permission.value();
            if (permissions.length > 0) {
                RequestPermissionListener listener = null;
                if (annotationOwner instanceof RequestPermissionListener) {
                    listener = (RequestPermissionListener) annotationOwner;
                }
                if (listener == null && annotationOwner instanceof IBaseView) {
                    listener = ((IBaseView) annotationOwner).getPermissionListener();
                }
                if (listener == null) {
                    throw new IllegalArgumentException("please override method of 'getPermissionListener()', and return  the instance of RequestPermissionListener ");
                }
                Activity activity = null;
                if (annotationOwner instanceof Activity) {
                    activity = (Activity) annotationOwner;
                } else if (annotationOwner instanceof android.app.Fragment) {
                    activity = ((android.app.Fragment) annotationOwner).getActivity();
                } else if (annotationOwner instanceof Fragment) {
                    activity = ((Fragment) annotationOwner).getActivity();
                }
                if (activity == null) {
                    throw new IllegalArgumentException("baseView not support");
                }
                if (permissions.length > 1) {// 去重
                    HashSet<String> tempHash = new HashSet<>();
                    for (String p : permissions) {
                        tempHash.add(p);
                    }
                    if (tempHash.size() < permissions.length) {
                        permissions = tempHash.toArray(new String[tempHash.size()]);
                    }
                    tempHash.clear();
                }

                PermissionTool legoPermissions = new PermissionTool(activity);
                legoPermissions.setAnnotationPermissions(permissions);
                legoPermissions.requestPermissionsFromFragment(permissions, listener);
                return legoPermissions;

            }
        }
        return null;
    }

    /**
     * 处理动态权限
     * 加在类上的权限
     *
     * @param baseView
     */
    public static PermissionTool handlePermission(@NonNull IBaseView baseView) {
        String[] permissions = getPermission(baseView);
        if (permissions != null && permissions.length > 0) {
            RequestPermissionListener listener = null;
            if (baseView instanceof RequestPermissionListener) {
                listener = (RequestPermissionListener) baseView;
            }
            if (listener == null) {
                listener = baseView.getPermissionListener();
            }
            if (listener == null) {
                throw new IllegalArgumentException("please override method of 'getPermissionListener()', and return  the instance of RequestPermissionListener ");
            }

            Activity activity = null;
            if (baseView instanceof Activity) {
                activity = (Activity) baseView;
            } else if (baseView instanceof android.app.Fragment) {
                activity = ((android.app.Fragment) baseView).getActivity();
            } else if (baseView instanceof Fragment) {
                activity = ((Fragment) baseView).getActivity();
            }

            if (activity == null) {
                throw new IllegalArgumentException("baseView not support");
            }
            if (permissions.length > 1) {// 去重
                HashSet<String> tempHash = new HashSet<>();
                for (String permission : permissions) {
                    tempHash.add(permission);
                }
                if (tempHash.size() < permissions.length) {
                    permissions = tempHash.toArray(new String[tempHash.size()]);
                }
                tempHash.clear();
            }


            PermissionTool legoPermissions = new PermissionTool(activity);
            legoPermissions.setAnnotationPermissions(permissions);
            legoPermissions.requestPermissionsFromFragment(permissions, listener);
            return legoPermissions;
        }
        return null;
    }

    /**
     * 获取注解 @Permission 的目标权限
     *
     * @param object
     * @return
     */
    public static String[] getPermission(@NonNull Object object) {
        if (object != null && (object instanceof Activity || object instanceof Fragment)) {
            Class<?> objClass = object.getClass();
            PermissionAuto autoPermission = objClass.getAnnotation(PermissionAuto.class);
            if (autoPermission != null) {
                try {
                    Application application = AppUtils.getApp();
                    PackageInfo packageInfo = application.getPackageManager()
                            .getPackageInfo(application.getPackageName(), PackageManager.GET_PERMISSIONS);

                    String[] permissions = packageInfo.requestedPermissions;
                    List<String> result = new ArrayList<>();
                    for (String permission : permissions) {
                        if (dangerPermissions.contains(permission)) {
                            result.add(permission);
                        }
                    }
                    if (result.size() == 0) return null;
                    String[] temp = new String[result.size()];
                    return result.toArray(temp);
                } catch (PackageManager.NameNotFoundException e) {
                    LogUtils.e("动态权限申请发生错误", e);
                }
            } else {
                Permission permission = objClass.getAnnotation(Permission.class);
                if (permission != null) {
                    String[] permissions = permission.value();
                    if (permissions != null && permissions.length > 0) {
                        return permission.value();
                    }
                }
            }

        }
        return null;
    }
}
