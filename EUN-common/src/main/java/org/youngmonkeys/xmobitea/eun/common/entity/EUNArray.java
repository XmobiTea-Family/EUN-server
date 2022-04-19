package org.youngmonkeys.xmobitea.eun.common.entity;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import lombok.var;

import java.util.ArrayList;
import java.util.List;

@EzyObjectBinding
public class EUNArray extends EUNData {
    public static class Builder {
        private List<Object> originArray;

        public Builder add(Object value) {
            originArray.add(value);

            return this;
        }

        public Builder addAll(List list) {
            for (var o : list) {
                add(o);
            }

            return this;
        }

        public EUNArray build() {
            var answer = new EUNArray();

            for (var o : originArray) {
                answer.add(o);
            }

            return answer;
        }

        public Builder() {
            originArray = new ArrayList<>();
        }
    }

    private List<Object> originArray;

    public EUNArray() {
        this.originArray = new ArrayList<>();
    }

    public void add(Object value) {
        originArray.add(createUseDataFromOriginData(value));
    }

    public List<Object> values() {
        return originArray;
    }

    public boolean contains(Object value) {
        return originArray.contains(value);
    }

    @Override
    public void clear() {
        originArray.clear();
    }

    @Override
    public boolean remove(Integer index) {
        return originArray.remove(index);
    }

    @Override
    public Integer count() {
        return originArray.size();
    }

    @Override
    protected <T> T get(Integer k, T defaultValue) {
        if (k < 0 || k > originArray.size() - 1) return defaultValue;

        var value = originArray.get(k);

        if (value == null) return defaultValue;

        try {
            return (T)value;
        }
        catch (Exception ex) {
            return defaultValue;
        }
    }

//    public List<Object> toList() {
//        return values();
//    }

    public <T> List<T> toList() {
        var answer = new ArrayList<T>();

        var values = values();
        for (var i = 0; i < values.size(); i++) {
            answer.add((T)values.get(i));
        }

        return answer;
    }

    @Override
    public Object toEzyData ()
    {
        var eunArray = EzyEntityFactory.newArray();

        for (var i = 0; i < originArray.size(); i++) {
            eunArray.add(createEUNDataFromUseData(originArray.get(i)));
        }

        return eunArray;
    }
}
