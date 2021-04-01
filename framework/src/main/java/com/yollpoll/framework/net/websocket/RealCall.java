package com.yollpoll.framework.net.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by wangqian on 2019/1/18.
 */

class RealCall<T> implements Call<T> {

    private OnResponse<T> response;
    private WebSocketRetrofit.ServiceMethod nServiceMethod;
    private Object[] nArg;
    private WebSocketClientComponent nWebSocketClientComponent;

    // 每个消息唯一的UUID
    private String msgUUId;

    // 消息类型，可包含多个消息
    private String msgType;

    public RealCall(WebSocketRetrofit.ServiceMethod serviceMethod, Object[] args,
                    WebSocketClientComponent webSocketClientComponent) {
        nServiceMethod = serviceMethod;
        nArg = args;
        nWebSocketClientComponent = webSocketClientComponent;
    }

    @Override
    public void enqueue(OnResponse<T> response) {
        this.response = response;
        // 每个消息唯一的UUID
        msgUUId = nWebSocketClientComponent.getRequestDispatch().createMsgId();

        String jsonStr = makeMsg(nArg[0]);
        nWebSocketClientComponent.getRequestDispatch().sendMsg(new RequestMessage(jsonStr, msgUUId, RealCall.this));
        nWebSocketClientComponent.getResponseDispatch().addCall(msgUUId, RealCall.this);
    }

    @Override
    public void enqueueNoCache(OnResponse<T> response) {
        this.response = response;
        msgUUId = nWebSocketClientComponent.getRequestDispatch().createMsgId();
        String jsonStr = makeMsg(nArg[0]);
        nWebSocketClientComponent.getRequestDispatch().sendMsg(new RequestMessage(jsonStr, msgUUId, RealCall.this),
                false);
        nWebSocketClientComponent.getResponseDispatch().addCall(msgUUId, RealCall.this);
    }

    public void enqueue() {
        msgUUId = nWebSocketClientComponent.getRequestDispatch().createMsgId();
        String jsonStr = makeMsg(nArg[0]);
        nWebSocketClientComponent.getRequestDispatch().sendMsgNoReturn(new RequestMessage(jsonStr, msgUUId, null));
    }

    @Override
    public void enqueueNoCache() {
        msgUUId = nWebSocketClientComponent.getRequestDispatch().createMsgId();
        String jsonStr = makeMsg(nArg[0]);
        nWebSocketClientComponent.getRequestDispatch().sendMsgNoReturn(new RequestMessage(jsonStr, msgUUId, RealCall.this), false);
    }

    @Override
    public OnResponse<T> getResponse() {
        return this.response;
    }

    @Override
    public Type getReturnType() {
        return getActualTypeArgumentOfRetureType(nServiceMethod.returnType);
    }

    @Override
    public void finishRequest() {
        nWebSocketClientComponent.getRequestDispatch().finishRequest(msgUUId, this);
    }

    private Type getActualTypeArgumentOfRetureType(Type returnType) {
        if (returnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) returnType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                return actualTypeArguments[0];
            }
        }
        return null;
    }

    private String makeMsg(Object argObject) {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);

//        builder.serializeNulls();
//        builder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory());

        BaseMsg baseMsg = getCommonMsg(argObject);
        baseMsg.setMsgUUId(msgUUId);
        Gson gson = builder.create();

        String jsonResult = gson.toJson(argObject);
        return jsonResult;
    }

    private BaseMsg getCommonMsg(Object argObject) {
        return (BaseMsg) argObject;
    }


    public class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringNullAdapter();
        }
    }

    public class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            // TODO Auto-generated method stub
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            // TODO Auto-generated method stub
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }
}