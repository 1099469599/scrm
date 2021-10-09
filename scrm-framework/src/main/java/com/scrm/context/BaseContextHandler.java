package com.scrm.context;

import cn.hutool.core.util.NumberUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程帮助类
 */
public class BaseContextHandler {

    private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();
    private static final String CONTEXT_KEY_ID = "currentId";
    private static final String CONTEXT_KEY_USER_ID = "currentUserId";
    private static final String CONTEXT_KEY_USERNAME = "currentUserName";
    private static final String CONTEXT_KEY_CORP_ID = "currentCorpId";

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static void setId(Long id) {
        set(CONTEXT_KEY_ID, id);
    }

    public static Long getId() {
        Object value = get(CONTEXT_KEY_ID);
        return NumberUtil.parseLong(returnObjectValue(value));
    }

    public static void setUserID(String userID) {
        set(CONTEXT_KEY_USER_ID, userID);
    }

    public static String getUserID() {
        Object value = get(CONTEXT_KEY_USER_ID);
        return returnObjectValue(value);
    }

    public static void setUsername(String username) {
        set(CONTEXT_KEY_USERNAME, username);
    }

    public static String getUsername() {
        Object value = get(CONTEXT_KEY_USERNAME);
        return returnObjectValue(value);
    }

    public static void setCorpId(String corpId) {
        set(CONTEXT_KEY_CORP_ID, corpId);
    }

    public static String getCorpId() {
        Object value = get(CONTEXT_KEY_CORP_ID);
        return returnObjectValue(value);
    }

    private static String returnObjectValue(Object value) {
        return value == null ? null : value.toString();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
