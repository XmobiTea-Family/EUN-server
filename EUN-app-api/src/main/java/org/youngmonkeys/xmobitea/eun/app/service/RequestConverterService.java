package org.youngmonkeys.xmobitea.eun.app.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyArrayList;
import com.tvd12.ezyfox.entity.EzyHashMap;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.util.EzyLoggable;
import org.youngmonkeys.xmobitea.eun.app.entity.EzyDataMember;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.request.base.Request;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNArray;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;
import lombok.*;

import java.lang.reflect.Field;
import java.util.*;

@EzySingleton
public class RequestConverterService extends EzyLoggable implements IRequestConverterService {
    private Map<Class, Field[]> declaredFieldsMap;

    @Override
    public <T extends OperationRequest> T createOperationRequest(@NonNull OperationRequest operationRequest, @NonNull Class<T> objectType) {
        try {
            var fields = declaredFieldsMap.getOrDefault(objectType, null);
            if (fields == null) {
                var tempFields = objectType.getDeclaredFields();

                var fieldLst = new ArrayList<Field>();
                for (var field : tempFields) {
                    var ann = field.getAnnotation(EzyDataMember.class);
                    if (ann != null) fieldLst.add(field);
                }

                fields = fieldLst.toArray(new Field[0]);
                declaredFieldsMap.put(objectType, fields);
            }

            var parameters = operationRequest.getParameters();

            var isValidRequest = true;

            var object = objectType.getDeclaredConstructor().newInstance();

            for (var field : fields) {
                var ann = field.getAnnotation(EzyDataMember.class);
                if (ann.isOptional()) {
                    var value = parameters.getObject(ann.code());
                    if (value != null) {
                        field.setAccessible(true);

                        var fieldType = field.getType();

                        if (value instanceof EzyHashMap) {
                            var valueEzyHashMap = (EzyHashMap)value;

                            if (fieldType.equals(EUNHashtable.class)) {
                                field.set(object, new EUNHashtable.Builder().addAll(valueEzyHashMap.toMap()).build());
                            }
                            else if (fieldType.equals(EzyObject.class)) {
                                field.set(object, valueEzyHashMap);
                            }
                            else if (fieldType.equals(Map.class)) {
                                field.set(object, valueEzyHashMap.toMap());
                            }
                        }
                        else if (value instanceof EzyArrayList) {
                            var valueEzyArrayList = (EzyArrayList)value;

                            if (fieldType.equals(EUNArray.class)) {
                                field.set(object, new EUNArray.Builder().addAll(valueEzyArrayList.toList()).build());
                            }
                            else if (fieldType.equals(EzyArray.class)) {
                                field.set(object, valueEzyArrayList);
                            }
                            else if (fieldType.equals(Collection.class)) {
                                field.set(object, valueEzyArrayList.toList());
                            }
                        }
                        else field.set(object, value);
                    }
                }
                else {
                    if (parameters.containsKey(ann.code())) {
                        var value = parameters.getObject(ann.code());

                        field.setAccessible(true);

                        if (value != null) {
                            var fieldType = field.getType();

                            if (value instanceof EzyHashMap) {
                                var valueEzyHashMap = (EzyHashMap)value;

                                if (fieldType.equals(EUNHashtable.class)) {
                                    field.set(object, new EUNHashtable.Builder().addAll(valueEzyHashMap.toMap()).build());
                                }
                                else if (fieldType.equals(EzyObject.class)) {
                                    field.set(object, valueEzyHashMap);
                                }
                                else if (fieldType.equals(Map.class)) {
                                    field.set(object, valueEzyHashMap.toMap());
                                }
                            }
                            else if (value instanceof EzyArrayList) {
                                var valueEzyArrayList = (EzyArrayList)value;

                                if (fieldType.equals(EUNArray.class)) {
                                    field.set(object, new EUNArray.Builder().addAll(valueEzyArrayList.toList()).build());
                                }
                                else if (fieldType.equals(EzyArray.class)) {
                                    field.set(object, valueEzyArrayList);
                                }
                                else if (fieldType.equals(Collection.class)) {
                                    field.set(object, valueEzyArrayList.toList());
                                }
                            }
                            else field.set(object, value);
                        }
                        else field.set(object, null);
                    }
                    else {
                        isValidRequest = false;
                        break;
                    }
                }
            }

            object.setOperationCode(operationRequest.getOperationCode());
            object.setRequestId(operationRequest.getRequestId());
            object.setParameters(operationRequest.getParameters());

            object.setValid(isValidRequest);
            return object;
        }
        catch (Exception ex) {
            logger.error("createOperationRequest", ex);
        }

        return null;
    }

    @Override
    public OperationRequest createOperationRequest(@NonNull Request request) {
        try {
            var operationRequest = new OperationRequest();

            var data = request.getData();
            operationRequest.setOperationCode(data.get(0));

            if (data.size() > 1) {
                var data1 = data.get(1, EzyObject.class);
                operationRequest.setParameters(data1 == null ? new EUNHashtable() : new EUNHashtable.Builder().addAll(data1.toMap()).build());
            }
            else {
                operationRequest.setParameters(new EUNHashtable());
            }

            operationRequest.setRequestId(data.size() > 2 ? data.get(2) : -1);

            return operationRequest;
        } catch (Exception ex) {
            logger.error("createOperationRequest", ex);
        }

        return null;
    }

    public RequestConverterService() {
        declaredFieldsMap = new Hashtable<>();
    }
}