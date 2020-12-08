package cn.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;

public class YZConvertUtil {
    public static <T> T fromJson(Response json, Type type) throws JsonIOException, JsonSyntaxException {
        String jsonString = "";
        T read = null;
        try {
            if (json.body() != null) {
                if (type.toString().equals("byte[]")) {
                    read = (T) json.body().bytes();
                } else {
                    jsonString = new String(json.body().bytes());
                    read = JSON.parseObject(jsonString, type);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return read;
    }

    public static <T> T fromJson(Response json, Class<T> type) throws JsonIOException, JsonSyntaxException {
        String jsonString = "";
        T read = null;
        try {
            if (json.body() != null) {
                if (type.toString().equals("byte[]")) {
                    read = (T) json.body().bytes();
                } else {
                    jsonString = new String(json.body().bytes());
                    read = JSON.parseObject(jsonString, type);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return read;
    }

    public static <T> T fromJson(String json, Class<T> type) throws JsonIOException, JsonSyntaxException {
        return JSON.parseObject(json, type);
    }

    public static <T> T fromJson(String json, Type type) throws JsonIOException, JsonSyntaxException {
        return JSON.parseObject(json, type);
    }

    public static String toJson(Object src) {
        return JSON.toJSONString(src);
    }
}
