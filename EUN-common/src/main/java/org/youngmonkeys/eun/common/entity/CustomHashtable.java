package org.youngmonkeys.eun.common.entity;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import lombok.var;

import java.util.*;

@EzyObjectBinding
public class CustomHashtable {
    private EzyObject ezyObject;

    public CustomHashtable() {
        ezyObject = EzyEntityFactory.newObject();
    }

    public CustomHashtable(EzyObject ezyObject) {
        this.ezyObject = EzyEntityFactory.newObject();

        if (ezyObject != null) {
            var keys = ezyObject.keySet();
            for (var key : keys) {
                if (key instanceof String) {
                    var keyStr = (String) key;
                    this.ezyObject.put(Integer.parseInt(keyStr), ezyObject.get(key));
                } else if (key instanceof Integer)
                {
                    var keyInt = (Integer) key;
                    this.ezyObject.put(keyInt, ezyObject.get(key));
                }
            }
        }
    }

    public int size() {
        return ezyObject.size();
    }

    public Object getOrDefault(Integer key, Object defaultValue) {
        if (containsKey(key)) return get(key);
        return defaultValue;
    }

    public boolean isEmpty() {
        return ezyObject.isEmpty();
    }

    public boolean containsKey(Integer key) {
        return ezyObject.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return ezyObject.isNotNullValue(value);
    }

    public Object get(Integer key) {
        return ezyObject.get(key);
    }

    public Object put(Integer key, Object value) {
        return ezyObject.put(key, value);
    }

    public Object remove(Integer key) {
        return ezyObject.remove(key);
    }

    public Object replace(Integer key, Object value) {
        Object answer = null;
        if (containsKey(key)) answer = remove(key);
        put(key, value);

        return answer;
    }

    public void putAll(CustomHashtable customHashtable) {
        ezyObject.putAll(customHashtable.ezyObject.toMap());
    }

    public void clear() {
        ezyObject.clear();
    }

    public Set<Integer> keySet() {
        var answer = new HashSet<Integer>();

        var keySet0 = ezyObject.keySet();

        for (var key : keySet0) {
            answer.add((Integer) key);
        }

        return answer;
    }

    private <V> V Get(int key, Class<V> vClass)
    {
        return ezyObject.get(key, vClass);
    }

//
//    public Collection<Object> values() {
//        return null;
//    }
//
//    public Set<Map.Entry<Object, Object>> entrySet() {
//        return ezyObject.entrySet();
//    }



    public Object toData() {
        return ezyObject.toMap();
    }
}
