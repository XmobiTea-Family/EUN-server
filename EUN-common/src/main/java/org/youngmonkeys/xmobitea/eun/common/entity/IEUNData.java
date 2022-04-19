package org.youngmonkeys.xmobitea.eun.common.entity;

public interface IEUNData {
    void clear();

    boolean remove(Integer k);

    Integer count();

    byte getByte(Integer k);

    byte getByte(Integer k, byte defaultValue);

    short getShort(Integer k);

    short getShort(Integer k, short defaultValue);

    Integer getInt(Integer k);

    Integer getInt(Integer k, Integer defaultValue);

    float getFloat(Integer k);

    float getFloat(Integer k, float defaultValue);

    long getLong(Integer k);

    long getLong(Integer k, long defaultValue);

    double getDouble(Integer k);

    double getDouble(Integer k, double defaultValue);

    boolean getBool(Integer k);

    boolean getBool(Integer k, boolean defaultValue);

    String getString(Integer k);

    String getString(Integer k, String defaultValue);

    Object getObject(Integer k);

    Object getObject(Integer k, Object defaultValue);

    //T[] GetArray<T>(int k, T[] defaultValue = null);

    //IList<T> GetList<T>(int k, IList<T> defaultValue = null);

    EUNArray getEUNArray(Integer k);

    EUNArray getEUNArray(Integer k, EUNArray defaultValue);

    EUNHashtable getEUNHashtable(Integer k);

    EUNHashtable getEUNHashtable(Integer k, EUNHashtable defaultValue);

    Object toEzyData();
}
