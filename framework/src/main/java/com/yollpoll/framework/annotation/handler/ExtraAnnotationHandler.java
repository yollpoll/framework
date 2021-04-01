package com.yollpoll.framework.annotation.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.yollpoll.framework.annotation.Extra;
import com.yollpoll.framework.log.LogUtils;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by spq on 2021/3/2
 * Extra 注解的处理
 */
public class ExtraAnnotationHandler {
    private Map<Field, Extra> extraAnnotations = null;

    /**
     * 收集extra注解
     *
     * @param field      field
     * @param annotation 注解
     */
    public void collectHandleFieldAnnotation(Field field, Annotation annotation) {
        if (annotation instanceof Extra) {
            if (null == extraAnnotations) {
                extraAnnotations = new HashMap<>();
            }
            extraAnnotations.put(field, (Extra) annotation);
        }
    }

    /**
     * 处理注解
     *
     * @param annotationOwner 注解拥有者
     */
    public void handleExtraAnnotations(Object annotationOwner) {
        if (extraAnnotations == null || extraAnnotations.size() == 0) {
            return;
        }
        Bundle intent = null;
        if (annotationOwner instanceof Activity) {
            Activity activity = (Activity) annotationOwner;
            intent = activity.getIntent().getExtras();
        } else if (annotationOwner instanceof Fragment) {
            Fragment fragment = (Fragment) annotationOwner;
            intent = fragment.getActivity().getIntent().getExtras();
            if (intent == null) intent = new Bundle();
            Bundle arguments = fragment.getArguments();
            if (arguments != null) {
                intent.putAll(arguments);
            }
        } else if (annotationOwner instanceof android.app.Fragment) {
            android.app.Fragment fragment = (android.app.Fragment) annotationOwner;
            intent = fragment.getActivity().getIntent().getExtras();
            if (intent == null) intent = new Bundle();
            Bundle arguments = fragment.getArguments();
            if (arguments != null) {
                intent.putAll(arguments);
            }
        } else {
            return;
        }
        Gson gson = null;
        for (Field field : extraAnnotations.keySet()) {
            Extra extra = extraAnnotations.get(field);
            String extraValue = extra.value();
            String paraName = "".equals(extraValue) ? field.getName() : extraValue;

            Class<?> fieldType = field.getType();
            String typeCanonicalName = fieldType.getCanonicalName();
            Type genericType = field.getGenericType();
            boolean isAccessible = field.isAccessible();

            try {
                if (!isAccessible) {
                    field.setAccessible(true);
                }
                if ("int".equals(typeCanonicalName)) {
                    field.setInt(annotationOwner, intent.getInt(paraName, field.getInt(annotationOwner)));
                } else if ("java.lang.Integer".equals(typeCanonicalName)) {
                    Integer integer = (Integer) field.get(annotationOwner);
                    field.set(annotationOwner, intent.getInt(paraName, integer == null ? 0 : integer));
                } else if ("char".equals(typeCanonicalName)) {
                    field.setChar(annotationOwner, intent.getChar(paraName, field.getChar(annotationOwner)));
                } else if ("java.lang.Character".equals(typeCanonicalName)) {
                    Character character = (Character) field.get(annotationOwner);
                    field.set(annotationOwner, intent.getChar(paraName, character == null ? 0 : character));
                } else if ("boolean".equals(typeCanonicalName)) {
                    field.setBoolean(annotationOwner, intent.getBoolean(paraName, field.getBoolean(annotationOwner)));
                } else if ("java.lang.Boolean".equals(typeCanonicalName)) {
                    Boolean aBoolean = (Boolean) field.get(annotationOwner);
                    field.set(annotationOwner, intent.getBoolean(paraName, aBoolean == null ? false : aBoolean));
                } else if ("byte".equals(typeCanonicalName)) {
                    field.setByte(annotationOwner, intent.getByte(paraName, field.getByte(annotationOwner)));
                } else if ("java.lang.Byte".equals(typeCanonicalName)) {
                    Byte aByte = (Byte) field.get(annotationOwner);
                    field.set(annotationOwner, intent.getByte(paraName, aByte == null ? 0 : aByte));
                } else if ("short".equals(typeCanonicalName)) {
                    field.setShort(annotationOwner, intent.getShort(paraName, field.getShort(annotationOwner)));
                } else if ("java.lang.Short".equals(typeCanonicalName)) {
                    Short aShort = (Short) field.get(annotationOwner);
                    field.set(annotationOwner, intent.getShort(paraName, aShort == null ? 0 : aShort));
                } else if ("long".equals(typeCanonicalName)) {
                    field.setLong(annotationOwner, intent.getLong(paraName, field.getLong(annotationOwner)));
                } else if ("java.lang.Long".equals(typeCanonicalName)) {
                    Long aLong = (Long) field.get(annotationOwner);
                    field.set(annotationOwner, intent.getLong(paraName, aLong == null ? 0 : aLong));
                } else if ("float".equals(typeCanonicalName)) {
                    field.setFloat(annotationOwner, intent.getFloat(paraName, field.getFloat(annotationOwner)));
                } else if ("java.lang.Float".equals(typeCanonicalName)) {
                    Float aFloat = (Float) field.get(annotationOwner);
                    field.set(annotationOwner, intent.getFloat(paraName, aFloat == null ? 0 : aFloat));
                } else if ("double".equals(typeCanonicalName)) {
                    field.setDouble(annotationOwner, intent.getDouble(paraName, field.getDouble(annotationOwner)));
                } else if ("java.lang.Double".equals(typeCanonicalName)) {
                    Double aDouble = (Double) field.get(annotationOwner);
                    field.set(annotationOwner, intent.getDouble(paraName, aDouble == null ? 0 : aDouble));
                } else if ("int[]".equals(typeCanonicalName)) {
                    field.set(annotationOwner, intent.getIntArray(paraName));
                } else if ("char[]".equals(typeCanonicalName)) {
                    field.set(annotationOwner, intent.getCharArray(paraName));
                } else if ("boolean[]".equals(typeCanonicalName)) {
                    field.set(annotationOwner, intent.getBooleanArray(paraName));
                } else if ("byte[]".equals(typeCanonicalName)) {
                    field.set(annotationOwner, intent.getByteArray(paraName));
                } else if ("short[]".equals(typeCanonicalName)) {
                    field.set(annotationOwner, intent.getShortArray(paraName));
                } else if ("long[]".equals(typeCanonicalName)) {
                    field.set(annotationOwner, intent.getLongArray(paraName));
                } else if ("double[]".equals(typeCanonicalName)) {
                    field.set(annotationOwner, intent.getDoubleArray(paraName));
                } else if ("float[]".equals(typeCanonicalName)) {
                    field.set(annotationOwner, intent.getFloat(paraName));
                } else if ("java.io.Serializable".equals(typeCanonicalName)) {
                    // 防止畸形数据，为通过安全平台检测
                    try {
                        field.set(annotationOwner, intent.getSerializable(paraName));
                    } catch (Exception e) {

                    }

                } else if ("java.lang.String".equals(typeCanonicalName)) {
                    field.set(annotationOwner, intent.getString(paraName));
                } else if ("String[]".equals(typeCanonicalName)) {
                    field.set(annotationOwner, intent.getStringArray(paraName));
                } else if ("java.util.ArrayList".equals(typeCanonicalName) || "java.util.List".equals(typeCanonicalName)) {
                    if (genericType instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) genericType;
                        // 获取List泛型
                        Class<?> subclass = (Class<?>) pt.getActualTypeArguments()[0];

                        if (String.class.isAssignableFrom(subclass)) {
                            field.set(annotationOwner, intent.getStringArrayList(paraName));
                        } else if (Integer.class.isAssignableFrom(subclass)) {
                            field.set(annotationOwner, intent.getIntegerArrayList(paraName));
                        } else if (CharSequence.class.isAssignableFrom(subclass)) {
                            field.set(annotationOwner, intent.getCharSequenceArrayList(paraName));
                        } else if (Parcelable.class.isAssignableFrom(subclass)) {
                            field.set(annotationOwner, intent.getParcelableArrayList(paraName));
                        } else {// 如果List泛型不是intent支持的类型，则为自定义实体类
                            String jsonString = intent.getString(paraName);
                            if (!TextUtils.isEmpty(jsonString)) {
                                if (gson == null) {
                                    gson = new Gson();
                                }
                                Type type = new TypeToken<ArrayList<JsonObject>>() {
                                }.getType();
                                ArrayList<JsonObject> jsonObjects = new Gson().fromJson(jsonString, type);
                                ArrayList arrayList = new ArrayList();
                                for (JsonObject jsonObject : jsonObjects) {
                                    arrayList.add(new Gson().fromJson(jsonObject, subclass));
                                }


                                field.set(annotationOwner, arrayList);
                            }
                        }

                    }
                } else if ("android.os.Bundle".equals(typeCanonicalName)) {
                    if (intent.getBundle(paraName) == null) {
                        setUnSeriableObj(intent, paraName, gson, field, fieldType, annotationOwner);
                    } else {
                        field.set(annotationOwner, intent.getBundle(paraName));
                    }

                } else if (Parcelable.class.isAssignableFrom(fieldType)) {

                    Object value = intent.getParcelable(paraName);
                    // 以解决使用LegoInten的putObjectExtra方法传递实现Parcelable接口的类
                    if (value == null || value instanceof String) {
                        setUnSeriableObj(intent, paraName, gson, field, fieldType, annotationOwner);
                    } else {
                        field.set(annotationOwner, value);
                    }
                } else {
                    boolean isSetValue = false;
                    boolean isArray = fieldType.isArray();
                    if (isArray) {
                        Class<?> omponentType = fieldType.getComponentType();
                        if (Parcelable.class == omponentType) {
                            field.set(annotationOwner, intent.getParcelableArray(paraName));
                            continue;
                        }

                    } else {
                        // 获取实现的接口看是不是实现了Serializable
                        if (Serializable.class.isAssignableFrom(fieldType)) {
                            // 防止畸形数据，为通过安全平台检测
                            try {
                                Object value = intent.getSerializable(paraName);
                                // 以解决使用LegoInten的putObjectExtra方法传递实现Serializable接口的类
                                if (value == null || value instanceof String) {
                                    setUnSeriableObj(intent, paraName, gson, field, fieldType, annotationOwner);
                                } else {
                                    field.set(annotationOwner, value);
                                }
                                isSetValue = true;
                            } catch (Exception e) {

                            }

                        }

                    }
                    if (!isSetValue) {
                        setUnSeriableObj(intent, paraName, gson, field, fieldType, annotationOwner);
                    }


                }
                field.setAccessible(isAccessible);
            } catch (Exception e) {
                LogUtils.w(Log.getStackTraceString(e));
                e.printStackTrace();
            }
        }
        extraAnnotations.clear();
        extraAnnotations = null;
    }

    /**
     * 设置未序列化的类
     *
     * @param intent
     * @param paraName
     * @param gson
     * @param field
     * @param fieldType
     * @param annotationOwner
     */
    private void setUnSeriableObj(Bundle intent, String paraName, Gson gson, Field field, Class<?> fieldType, Object annotationOwner) throws Exception {
        String jsonString = intent.getString(paraName);
        if (!TextUtils.isEmpty(jsonString)) {
            if (gson == null) {
                gson = new Gson();
            }
            field.set(annotationOwner, gson.fromJson(jsonString, fieldType));
        }
    }

}
