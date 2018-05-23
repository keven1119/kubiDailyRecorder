package com.keven.kubi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.StringRes;

import com.keven.kubi.MainApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SharedPresUtil {
    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCES_NEW";

    private static Context mContext;
    private SharedPreferences sp = null;
    private Editor edit = null;

    /** 对象 序列化/反序列化 */
    private ObjectParser parser;

    /**
     * 是否 开启事务
     */
    private boolean isTransaction;

    private static Map<String, SharedPresUtil> map = new HashMap<>();

    private SharedPresUtil(String name) {
        this((mContext == null ? mContext = MainApp.getInstances() : mContext), name);
    }

    public SharedPresUtil(Context context, String name) {
        sp = context.getSharedPreferences(name, 0);
        edit = sp.edit();

        parser = new FastJSONParser();
    }

//    /**
//     * {@linkplain FastJSONParser} or {@linkplain GsonParser} or else
//     */
    public void setJSONParser(ObjectParser parser) {
        this.parser = parser;
    }

    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public static SharedPresUtil get(String name) {
//        if (DevModeUtils.getDevMode()
//                        .isTestServer()) {
//            name = name + "_testServer";
//        }
        if (map.containsKey(name)) {
            return map.get(name);
        }
        synchronized (map) {
            if (!map.containsKey(name)) {
                SharedPresUtil sharedPresUtil = new SharedPresUtil(name);
                map.put(name, sharedPresUtil);
            }
        }
        return map.get(name);
    }

    public static SharedPresUtil getDefault() {
        return get(SHARED_PREFERENCES);
    }


    public boolean hasKey(String key) {
        return sp.contains(key);
    }

    /////////////////////// Setter ///////////////////////

    // Boolean
    public void putBoolean(String key, boolean value) {
        edit.putBoolean(key, value);

        apply();
    }

    public void putBoolean(@StringRes int resKey, boolean value) {
        putBoolean(mContext.getString(resKey), value);
    }

    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    // Float
    public void putFloat(String key, float value) {
        edit.putFloat(key, value);

        apply();
    }

    public void putFloat(@StringRes int resKey, float value) {
        putFloat(mContext.getString(resKey), value);
    }

    // Integer
    public void putInt(String key, int value) {
        edit.putInt(key, value);

        apply();
    }

    public void putInt(@StringRes int resKey, int value) {
        putInt(mContext.getString(resKey), value);
    }

    // Long
    public void putLong(String key, long value) {
        edit.putLong(key, value);

        apply();
    }

    public void putLong(@StringRes int resKey, long value) {
        putLong(mContext.getString(resKey), value);
    }

    // String
    public void putString(String key, String value) {
        edit.putString(key, value);

        apply();
    }

    public void putString(@StringRes int resKey, String value) {
        putString(mContext.getString(resKey), value);
    }

    public void putStringSet(String key, Set<String> set) {
        edit.putStringSet(key, set);

        apply();
    }

    /**
     * 储存任意对象
     *
     * @param key
     * @param object 任意对象（注意List、Map类型要慎重，put的时候object是什么类型，get的时候就传什么类型）
     */
    public void putObject(String key, Object object) {
        try {
            if (object == null){
                remove(key);
                return;
            }
            String string = parser.toString(object);

            putString(key, string);
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }

    /**
     * 储存对象列表
     *
     * @param key
     * @param collection
     * @param <T>
     */
    public <T> void putList(String key, List<T> collection) {
        putObject(key, new ArrayList<>(collection));// 在get的时候，注意转换成ArrayList，不一定是这里的collection类型
    }

    /////////////////////// Getter ///////////////////////

    // Boolean
    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public boolean getBoolean(int resKey, boolean defaultValue) {
        return getBoolean(mContext.getString(resKey), defaultValue);
    }

    // Float
    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public float getFloat(@StringRes int resKey, float defaultValue) {
        return getFloat(mContext.getString(resKey), defaultValue);
    }

    // Integer
    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public int getInt(@StringRes int resKey, int defaultValue) {
        return getInt(mContext.getString(resKey), defaultValue);
    }

    // Long
    public long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public long getLong(@StringRes int resKey, long defaultValue) {
        return getLong(mContext.getString(resKey), defaultValue);
    }

    // String
    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public Set<String> getStringSet(String key) {
        return sp.getStringSet(key, new HashSet<String>());
    }

    public String getString(@StringRes int resKey, String defaultValue) {
        return getString(mContext.getString(resKey), defaultValue);
    }

    public <T> T get(String key, Class<T> clazz) {
        try {
            if (hasKey(key)) {
                String string = getString(key, "");

                T t = parser.parseString(string, clazz);

                return t;
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }

        return null;
    }

    /**
     * @param key
     * @param clazz List元素类型
     * @param <T>
     *
     * @return
     */
    public <T> List<T> getList(String key, Class<T> clazz) {
        try {
            if (hasKey(key)) {
                String string = getString(key, "");

                List<T> list = parser.parseListString(string, clazz);

                return list;
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }

        return new ArrayList<>();
    }

    /////////////////////// Remove ///////////////////////

    // Delete
    public void remove(String key) {
        edit.remove(key);

        apply();
    }

    public void clear() {
        edit.clear();

        apply();
    }

    /**
     * 开启事务
     * <br><br>
     * 开启事务后，此SharedPresUtil.put(...)方法，全部不commit()，直到调用 {@linkplain #endTransaction()}才会提交数据。
     * (调用 {@linkplain #apply()} 并不会结束事务)
     */
    public void beginTransaction() {
        isTransaction = true;
    }

    /**
     * 结束事务，SharedPresUtil.put(...)方法会自动commit()
     */
    public void endTransaction() {
        isTransaction = false;

        apply();
    }

    /**
     * 提交数据（持久化）
     */
    public void apply() {
        if (!isTransaction) {
            edit.apply();
        }
    }

    public void commit() {
        edit.commit();
    }

}