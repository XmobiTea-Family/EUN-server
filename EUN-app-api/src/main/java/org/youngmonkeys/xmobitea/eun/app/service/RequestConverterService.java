package org.youngmonkeys.xmobitea.eun.app.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.entity.EzyArrayList;
import com.tvd12.ezyfox.entity.EzyHashMap;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.util.EzyLoggable;
import org.youngmonkeys.xmobitea.eun.app.entity.EzyDataMember;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.request.base.Request;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNArray;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;
import lombok.*;

@EzySingleton
public class RequestConverterService extends EzyLoggable implements IRequestConverterService {
    @Override
    public <T extends OperationRequest> T createOperationRequest(@NonNull OperationRequest operationRequest, @NonNull Class<T> objectType) {
        try {
            var fields = objectType.getDeclaredFields();
            var parameters = operationRequest.getParameters();

            var isValidRequest = true;

            var object = objectType.newInstance();

            for (var field : fields) {
                var ann = field.getAnnotation(EzyDataMember.class);
                if (ann != null) {
                    if (ann.isOptional()) {
                        var value = parameters.getObject(ann.code());
                        if (value != null) {
                            field.setAccessible(true);

                            var fieldType = field.getType();

                            if (value instanceof EzyHashMap && fieldType.equals(EUNHashtable.class)) {
                                var valueEzyHashMap = (EzyHashMap)value;

                                field.set(object, new EUNHashtable.Builder().addAll(valueEzyHashMap.toMap()).build());
                            }
                            else if (value instanceof EzyArrayList && fieldType.equals(EUNArray.class)) {
                                var valueEzyArrayList = (EzyArrayList)value;

                                field.set(object, new EUNArray.Builder().addAll(valueEzyArrayList.toList()).build());
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

                                if (value instanceof EzyHashMap && fieldType.equals(EUNHashtable.class)) {
                                    var valueEzyHashMap = (EzyHashMap)value;

                                    field.set(object, new EUNHashtable.Builder().addAll(valueEzyHashMap.toMap()).build());
                                }
                                else if (value instanceof EzyArrayList && fieldType.equals(EUNArray.class)) {
                                    var valueEzyArrayList = (EzyArrayList)value;

                                    field.set(object, new EUNArray.Builder().addAll(valueEzyArrayList.toList()).build());
                                }
                                else field.set(object, value);
                            }
                            else field.set(object, value);
                        }
                        else {
                            isValidRequest = false;
                            break;
                        }
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

            operationRequest.setParameters(data.size() > 1 ?  new EUNHashtable.Builder().addAll(data.get(1, EzyObject.class).toMap()).build() : new EUNHashtable());
            operationRequest.setRequestId(data.size() > 2 ? data.get(2) : -1);

            return operationRequest;
        } catch (Exception ex) {
            logger.error("createOperationRequest", ex);
        }

        return null;
    }
}