package com.yollpoll.framework.router;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;


import com.yollpoll.arch.log.LogUtils;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

public class Dispatcher {
    private static final String TAG = "Dispatcher";
    private static HashMap<String,OnDispatchListener> moduleMap=new HashMap<>();


    /**
     * 验证协议头
     * @param url
     * @return
     */
    public static boolean isPrefixValid(String url) {
        return (url != null) &&
                (url.startsWith("http://") || url.startsWith("https://")
                        || url.startsWith("react://")
                        || url.startsWith("native://") || url.startsWith("behavior://"));

    }

    /**
     * 注册各个模块的分发器
     * @param map 指定模块对应的处理器
     */
    public static void registerDispatch(HashMap<String, OnDispatchListener> map) {
        moduleMap = map;
    }

    /**
     * 分发请求
     * @param url       请求url
     */
    public static void dispatch(String url) {
        dispatch(url, null, null);
    }
    /**
     * 带回调的分发请求
     * @param url       请求url
     * @param onBackListener  请求完成后的回调
     */
    public static void dispatch(String url, OnBackListener onBackListener) {
        dispatch(url, null, onBackListener);
    }

    /**
     * 分发请求
     * @param url       请求url
     * @param context   Context
     */
    public static void dispatch(String url, Context context) {
        dispatch(url, context, null);
    }
    /**
     * 带回调的分发请求
     * @param url       请求url
     * @param context   Context
     * @param onBackListener  请求完成后的回调
     */
    public static void dispatch(final String url, final Context context, final OnBackListener onBackListener) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                realDispatch(url, context, onBackListener);
            }
        });
    }
    public static void realDispatch(String url, Context context, OnBackListener onBackListener) {
        try {
            if (!isPrefixValid(url)) {
                return;
            }

            URI uri = new URI(url);
            String query = uri.getRawQuery();
            HashMap<String, String> param = getParams(query);

            if (url.startsWith("native://")) {
                String module = uri.getHost();
                if (module == null || moduleMap.get(module) == null) {
                    return;
                }
                OnDispatchListener onDispatchListener = moduleMap.get(module);
                onDispatchListener.onDispatch(param, context, onBackListener);
                return;
            }

            if (url.startsWith("behavior://")) {
                String module = uri.getHost();
                if (module == null) {
                    return;
                }
                param.put("module", module);
                param.put("url", url);
                BehaviorManager.INSTANCE.onDispatch(param, context ,onBackListener);
                return;
            }

            //TODO:修改web跳转方式
            if (url.startsWith("http://") || url.startsWith("https://")) {
                param.put("url", url);
                String dispatchUrl = "native://web/?act=web";
                for (String key : param.keySet()) {
                    try {
                        dispatchUrl += "&" + key + "="
                                + URLEncoder.encode(param.get(key), "UTF-8");
                    } catch (Exception e) {
                        LogUtils.e(e.getMessage(), e);
                    }
                }
                realDispatch(dispatchUrl, context, onBackListener);
                //WebManager.INSTANCE.onDispatch(param, context ,onBackListener);
                return;
            }

//            if (url.startsWith("react://")) {
//                String bundleId = uri.getHost();
//                if (bundleId == null) {
//                    return;
//                }
//                param.put("bundleId", bundleId);
//                ReactManager.INSTANCE.onDispatch(param, context, onBackListener);
//                return;
//            }
        } catch (Exception e) {
            LogUtils.e(e, e.getMessage());
        }

    }

    private static HashMap<String, String> getParams(String query) {
        HashMap<String, String> params = new HashMap<String, String>();
        if (query == null) {
            return params;
        }
        try {
            String[] arrSplit = query.split("[&]");
            for (String strSplit : arrSplit) {
                String[] arrSplitEqual = null;
                arrSplitEqual = strSplit.split("[=]");
                if (arrSplitEqual.length > 1) {
                    params.put(arrSplitEqual[0], URLDecoder.decode(arrSplitEqual[1], "UTF-8"));

                } else {
                    if (arrSplitEqual[0] != "") {
                        params.put(arrSplitEqual[0], "");
                    }
                }
            }
            return params;
        } catch (Exception e) {
            return params;
        }
    }

}
