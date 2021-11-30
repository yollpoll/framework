package com.yollpoll.arch.permission;

/**
 * Created by spq on 2021/3/3
 */
public interface RequestPermissionListener {
    /**
     * 已禁止弹出授权提示，需要弹框提示
     *
     * @param permissions 用户拒绝授权使用的权限列表
     */
    void onShowRationale(String[] permissions);

    /**
     * 用户拒绝授权的权限
     *
     * @param permissions 用户拒绝授权使用的权限列表
     */
    void onPermissionDenied(String[] permissions);

    /**
     * 用户授权通过的权限
     */
    void onPermissionAllowed(String[] permissions);
}
