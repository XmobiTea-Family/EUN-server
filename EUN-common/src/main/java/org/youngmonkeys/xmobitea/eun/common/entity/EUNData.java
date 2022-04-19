package org.youngmonkeys.xmobitea.eun.common.entity;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import lombok.var;

import java.util.List;
import java.util.Map;

public class EUNData implements IEUNData {

    protected static Object createUseDataFromOriginData(Object value) {
        if (value == null) return null;

        if (value instanceof EzyArray) {
            var ezyArray = (EzyArray) value;

            var answer = new EUNArray.Builder()
                    .addAll(ezyArray.toList())
                    .build();

            return answer;
        }

        if (value instanceof EzyObject) {
            var ezyObject = (EzyObject) value;

            var answer = new EUNHashtable.Builder()
                    .addAll(ezyObject.toMap())
                    .build();

            return answer;
        }

        if (value instanceof EUNHashtable) {
            return value;
        }

        if (value instanceof EUNArray) {
            return value;
        }

        if (value instanceof List) {
            var list = (List) value;

            var answer = new EUNArray.Builder()
                    .addAll(list)
                    .build();

            return answer;
        }

        if (value instanceof Map) {
            var map = (Map) value;

            var answer = new EUNHashtable.Builder()
                    .addAll(map)
                    .build();

            return answer;
        }

        return value;
    }

    protected static Object createEUNDataFromUseData(Object value) {
        if (value == null) return null;

        if (value instanceof IEUNData) {
            var eunData = (IEUNData) value;
            return eunData.toEzyData();
        }

        return value;
    }

    protected <T> T get(Integer k, T defaultValue) {
        return defaultValue;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean remove(Integer k) {
        return false;
    }

    @Override
    public Integer count() {
        return 0;
    }

    @Override
    public byte getByte(Integer k) {
        return getByte(k, (byte) 0);
    }

    @Override
    public byte getByte(Integer k, byte defaultValue) {
        return ((Number) get(k, defaultValue)).byteValue();
    }

    @Override
    public short getShort(Integer k) {
        return getShort(k, (short) 0);
    }

    @Override
    public short getShort(Integer k, short defaultValue) {
        return ((Number) get(k, defaultValue)).shortValue();
    }

    @Override
    public Integer getInt(Integer k) {
        return getInt(k, 0);
    }

    @Override
    public Integer getInt(Integer k, Integer defaultValue) {
        return ((Number) get(k, defaultValue)).intValue();
    }

    @Override
    public float getFloat(Integer k) {
        return getFloat(k, 0);
    }

    @Override
    public float getFloat(Integer k, float defaultValue) {
        return ((Number) get(k, defaultValue)).floatValue();
    }

    @Override
    public long getLong(Integer k) {
        return getLong(k, 0);
    }

    @Override
    public long getLong(Integer k, long defaultValue) {
        return ((Number) get(k, defaultValue)).longValue();
    }

    @Override
    public double getDouble(Integer k) {
        return getDouble(k, 0);
    }

    @Override
    public double getDouble(Integer k, double defaultValue) {
        return ((Number) get(k, defaultValue)).doubleValue();
    }

    @Override
    public boolean getBool(Integer k) {
        return getBool(k, false);
    }

    @Override
    public boolean getBool(Integer k, boolean defaultValue) {
        return get(k, defaultValue);
    }

    @Override
    public String getString(Integer k) {
        return getString(k, null);
    }

    @Override
    public String getString(Integer k, String defaultValue) {
        return get(k, defaultValue);
    }

    @Override
    public Object getObject(Integer k) {
        return getObject(k, null);
    }

    @Override
    public Object getObject(Integer k, Object defaultValue) {
        return get(k, defaultValue);
    }

    @Override
    public EUNArray getEUNArray(Integer k) {
        return getEUNArray(k, null);
    }

    @Override
    public EUNArray getEUNArray(Integer k, EUNArray defaultValue) {
        return get(k, defaultValue);
    }

    @Override
    public EUNHashtable getEUNHashtable(Integer k) {
        return getEUNHashtable(k, null);
    }

    @Override
    public EUNHashtable getEUNHashtable(Integer k, EUNHashtable defaultValue) {
        return get(k, defaultValue);
    }

    @Override
    public Object toEzyData() {
        return null;
    }
}
