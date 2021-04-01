package com.yollpoll.framework.net.websocket;


import androidx.annotation.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangqian on 2019/1/16.
 */

public class WebSocketRetrofit {

    private final Map<Method, ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();
    WebSocketClientComponent nNVWebSocketClient;

    public WebSocketRetrofit(WebSocketClientComponent webSocketClient) {
        nNVWebSocketClient = webSocketClient;
    }

    private static <T> void validateServiceInterface(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        // Prevent API interfaces from extending other interfaces. This not only avoids a bug in
        // Android (http://b.android.com/58753) but it forces composition of API declarations which is
        // the recommended pattern.
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }

    /**
     * 重新设置tWebSocketClientComponent
     */
    public void reSetWebSocketClientComponent(WebSocketClientComponent webSocketClient) {
        serviceMethodCache.clear();
        nNVWebSocketClient = webSocketClient;
    }

    public <T> T create(final Class<T> service) {
        validateServiceInterface(service);
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, @Nullable Object[] args)
                            throws Throwable {
                        // If the method is a method from Object then defer to normal invocation.
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }
                        ServiceMethod serviceMethod = loadServiceMethod(method);
                        return new RealCall(serviceMethod, args, nNVWebSocketClient);
                    }
                });
    }

    private ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod result = serviceMethodCache.get(method);
        if (result != null) return result;
        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod(method).build();
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }

    public static class ServiceMethod {
        Method method;
        Type returnType;
        Type[] parameterTypes;

        public ServiceMethod(Method method) {
            this.method = method;
        }

        public ServiceMethod build() {
            returnType = method.getGenericReturnType();
            parameterTypes = method.getGenericParameterTypes();
            if (method.getReturnType() != Call.class) {
                throw methodError("return type is not CallBack");
            }
            if (parameterTypes.length == 0) {
                throw methodError("argments' length is 0");
            }
            return this;
        }

        private RuntimeException methodError(String message, Object... args) {
            return methodError(null, message, args);
        }

        private RuntimeException methodError(Throwable cause, String message, Object... args) {
            message = String.format(message, args);
            return new IllegalArgumentException(message
                    + "\n    for method "
                    + method.getDeclaringClass().getSimpleName()
                    + "."
                    + method.getName(), cause);
        }

        public Type getReturnType() {
            return returnType;
        }

        public void setReturnType(Type returnType) {
            this.returnType = returnType;
        }

        public Type[] getParameterTypes() {
            return parameterTypes;
        }

        public void setParameterTypes(Type[] parameterTypes) {
            this.parameterTypes = parameterTypes;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

    }
}
