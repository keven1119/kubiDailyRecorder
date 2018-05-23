package com.keven.kubi.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by kkmike999 on 17/1/25.
 * <p/>
 * fastJson 代替序列化工具
 */
public class FastJSONParser implements ObjectParser {

    /**
     * 对象序列化成String
     *
     * @param object
     * @return
     */
    @Override
    public String toString(Object object) {
        String string = JSONObject.toJSONString(object);
        return string;
    }

    /**
     * string反序列化为对象
     *
     * @param string
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> T parseString(String string, Class<T> clazz) {
        T t = JSON.parseObject(string, clazz);
        return t;
    }

    /**
     * string反序列化为对象
     *
     * @param string
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> parseListString(String string, Class<T> clazz) {
        List<T> list = JSONArray.parseArray(string, clazz);
        return list;
    }


}
