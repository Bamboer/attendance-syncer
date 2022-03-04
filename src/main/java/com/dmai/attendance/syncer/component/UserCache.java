package com.dmai.attendance.syncer.component;

import com.dmai.attendance.syncer.sdk.UserRecord;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class UserCache implements Map<String, UserRecord> {
    //TODO 改用 redis 服务
    private static final Map<String, UserRecord> USER_MAP = new ConcurrentHashMap<>();

    public static final String NULL = "NULL";

    @Override
    public int size() {
        return USER_MAP.size();
    }

    @Override
    public boolean isEmpty() {
        return USER_MAP.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return USER_MAP.containsValue(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return USER_MAP.containsValue(value);
    }

    @Override
    public UserRecord get(Object key) {
        return USER_MAP.get(key);
    }

    @Override
    public UserRecord put(String key, UserRecord value) {
        return USER_MAP.put(key, value);
    }

    @Override
    public UserRecord remove(Object key) {
        return USER_MAP.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends UserRecord> m) {
        USER_MAP.putAll(m);
    }

    @Override
    public void clear() {
        USER_MAP.clear();
    }

    @Override
    public Set<String> keySet() {
        return USER_MAP.keySet();
    }

    @Override
    public Collection<UserRecord> values() {
        return USER_MAP.values();
    }

    @Override
    public Set<Entry<String, UserRecord>> entrySet() {
        return USER_MAP.entrySet();
    }

    @Override
    public UserRecord getOrDefault(Object key, UserRecord defaultValue) {
        return USER_MAP.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super UserRecord> action) {
        USER_MAP.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super UserRecord, ? extends UserRecord> function) {
        USER_MAP.replaceAll(function);
    }

    @Override
    public UserRecord putIfAbsent(String key, UserRecord value) {
        return USER_MAP.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return USER_MAP.remove(key, value);
    }

    @Override
    public boolean replace(String key, UserRecord oldValue, UserRecord newValue) {
        return USER_MAP.replace(key, oldValue, newValue);
    }

    @Override
    public UserRecord replace(String key, UserRecord value) {
        return USER_MAP.replace(key, value);
    }

    @Override
    public UserRecord computeIfAbsent(String key, Function<? super String, ? extends UserRecord> mappingFunction) {
        return USER_MAP.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public UserRecord computeIfPresent(String key, BiFunction<? super String, ? super UserRecord, ? extends UserRecord> remappingFunction) {
        return USER_MAP.computeIfPresent(key, remappingFunction);
    }

    @Override
    public UserRecord compute(String key, BiFunction<? super String, ? super UserRecord, ? extends UserRecord> remappingFunction) {
        return USER_MAP.compute(key, remappingFunction);
    }

    @Override
    public UserRecord merge(String key, UserRecord value, BiFunction<? super UserRecord, ? super UserRecord, ? extends UserRecord> remappingFunction) {
        return USER_MAP.merge(key, value, remappingFunction);
    }
}
