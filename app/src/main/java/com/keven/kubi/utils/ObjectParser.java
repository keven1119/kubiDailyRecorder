package com.keven.kubi.utils;

import java.util.List;

/**
 * Created by kkmike999 on 16/1/14.
 * json序列化
 */
public interface ObjectParser {

    /**
     * 对象序列化成String
     *
     * @param object
     * @return
     */
    String toString(Object object);

    /**
     * string反序列化为对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T parseString(String json, Class<T> clazz);

    /**
     * string反序列化为对象
     *
     * @param json
     * @param clazz
     * @param <T>   列表元素类型
     * @return
     */
    <T> List<T> parseListString(String json, Class<T> clazz);

}
