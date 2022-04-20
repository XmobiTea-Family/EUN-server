package org.youngmonkeys.xmobitea.eun.common.entity;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import lombok.var;

import java.util.Collection;
import java.util.Map;

@EzyObjectBinding
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

        if (value instanceof IEUNData) {
            return value;
        }

        if (value instanceof Collection) {
            var list = (Collection) value;

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

        if (value instanceof Object[]) {
            var arrays = (Object[]) value;

            var answer = new EUNArray.Builder()
                    .addAll(arrays)
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

    protected <T> T get(int k, T defaultValue) {
        return defaultValue;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean remove(int k) {
        return false;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public byte getByte(int k) {
        return getByte(k, (byte) 0);
    }

    @Override
    public byte getByte(int k, byte defaultValue) {
        return ((Number) get(k, defaultValue)).byteValue();
    }

    @Override
    public short getShort(int k) {
        return getShort(k, (short) 0);
    }

    @Override
    public short getShort(int k, short defaultValue) {
        return ((Number) get(k, defaultValue)).shortValue();
    }

    @Override
    public int getInt(int k) {
        return getInt(k, 0);
    }

    @Override
    public int getInt(int k, int defaultValue) {
        return ((Number) get(k, defaultValue)).intValue();
    }

    @Override
    public float getFloat(int k) {
        return getFloat(k, 0);
    }

    @Override
    public float getFloat(int k, float defaultValue) {
        return ((Number) get(k, defaultValue)).floatValue();
    }

    @Override
    public long getLong(int k) {
        return getLong(k, 0);
    }

    @Override
    public long getLong(int k, long defaultValue) {
        return ((Number) get(k, defaultValue)).longValue();
    }

    @Override
    public double getDouble(int k) {
        return getDouble(k, 0);
    }

    @Override
    public double getDouble(int k, double defaultValue) {
        return ((Number) get(k, defaultValue)).doubleValue();
    }

    @Override
    public boolean getBool(int k) {
        return getBool(k, false);
    }

    @Override
    public boolean getBool(int k, boolean defaultValue) {
        return get(k, defaultValue);
    }

    @Override
    public String getString(int k) {
        return getString(k, null);
    }

    @Override
    public String getString(int k, String defaultValue) {
        return get(k, defaultValue);
    }

    @Override
    public Object getObject(int k) {
        return getObject(k, null);
    }

    @Override
    public Object getObject(int k, Object defaultValue) {
        return get(k, defaultValue);
    }

    @Override
    public EUNArray getEUNArray(int k) {
        return getEUNArray(k, null);
    }

    @Override
    public EUNArray getEUNArray(int k, EUNArray defaultValue) {
        return get(k, defaultValue);
    }

    @Override
    public EUNHashtable getEUNHashtable(int k) {
        return getEUNHashtable(k, null);
    }

    @Override
    public EUNHashtable getEUNHashtable(int k, EUNHashtable defaultValue) {
        return get(k, defaultValue);
    }

    @Override
    public Object toEzyData() {
        return null;
    }
}
