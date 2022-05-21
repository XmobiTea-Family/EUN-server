package org.youngmonkeys.xmobitea.eun.common.entity;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import lombok.var;

import java.util.*;

@EzyObjectBinding
public class EUNHashtable extends EUNData {
    public static class Builder
    {
        private final Map<Integer, Object> originObject;

        public Builder add(int key, Object value)
        {
            originObject.put(key, value);

            return this;
        }

        public Builder addAll(Map map)
        {
            var keySet = map.keySet();
            for (var key : keySet)
            {
                if (key instanceof String)
                {
                    var keyStr = (String)key;

                    try {
                        var keyInt = Integer.parseInt(keyStr);

                        add(keyInt, map.get(key));
                    }
                    catch (Exception ex) { }
                }
                else if (key instanceof Integer)
                {
                    var keyInt = (int)key;

                    add(keyInt, map.get(key));
                }
            }

            return this;
        }

        public EUNHashtable build()
        {
            var answer = new EUNHashtable();

            var keys = originObject.keySet();
            for (var key : keys)
            {
                answer.add(key, originObject.get(key));
            }

            return answer;
        }

        public Builder()
        {
            originObject = new HashMap<>();
        }
    }

    private final Map<Integer, Object> originObject;

    public EUNHashtable() {
        originObject = new HashMap<>();
    }

    public Map<Integer, Object> toMap() {
        return originObject;
    }

    public void add(int k, Object value)
    {
        if (!originObject.containsKey(k)) originObject.put(k, createUseDataFromOriginData(value));
        else originObject.replace(k, createUseDataFromOriginData(value));
    }

    public Collection<Object> values()
    {
        return originObject.values();
    }

    public Set<Integer> keySet()
    {
        return originObject.keySet();
    }

    public boolean containsKey(int key)
    {
        return originObject.containsKey(key);
    }

    @Override
    protected <T> T get(int k, T defaultValue) {
        if (originObject.containsKey(k))
        {
            var value = originObject.get(k);

            if (value == null) return defaultValue;

            try {
                return (T)value;
            }
            catch (Exception ex) {
                return defaultValue;
            }
        }

        return defaultValue;
    }

    @Override
    public void clear() {
        originObject.clear();
    }

    @Override
    public boolean remove(int k) {
        return originObject.remove(k) != null;
    }

    @Override
    public int count() {
        return originObject.size();
    }

    @Override
    public Object toEzyData() {
        var ezyObject = EzyEntityFactory.newObject();

        var keySet = originObject.keySet();

        for (var key : keySet)
        {
            ezyObject.put(key, createEUNDataFromUseData(originObject.get(key)));
        }

        return ezyObject;
    }

    @Override
    public String toString() {
        return this.originObject.toString();
    }
}
