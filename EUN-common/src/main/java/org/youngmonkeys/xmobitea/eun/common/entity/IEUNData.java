package org.youngmonkeys.xmobitea.eun.common.entity;

public interface IEUNData {
    void clear();

    boolean remove(int k);

    int count();

    byte getByte(int k);

    byte getByte(int k, byte defaultValue);

    short getShort(int k);

    short getShort(int k, short defaultValue);

    int getInt(int k);

    int getInt(int k, int defaultValue);

    float getFloat(int k);

    float getFloat(int k, float defaultValue);

    long getLong(int k);

    long getLong(int k, long defaultValue);

    double getDouble(int k);

    double getDouble(int k, double defaultValue);

    boolean getBool(int k);

    boolean getBool(int k, boolean defaultValue);

    String getString(int k);

    String getString(int k, String defaultValue);

    Object getObject(int k);

    Object getObject(int k, Object defaultValue);

    //T[] GetArray<T>(int k, T[] defaultValue = null);

    //IList<T> GetList<T>(int k, IList<T> defaultValue = null);

    EUNArray getEUNArray(int k);

    EUNArray getEUNArray(int k, EUNArray defaultValue);

    EUNHashtable getEUNHashtable(int k);

    EUNHashtable getEUNHashtable(int k, EUNHashtable defaultValue);

    Object toEzyData();
}
