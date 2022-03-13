package org.youngmonkeys.eun.app.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.entity.EzyHashMap;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.util.EzyLoggable;
import org.youngmonkeys.eun.app.entity.EzyDataMember;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.request.base.Request;
import org.youngmonkeys.eun.common.entity.CustomHashtable;
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
                        var value = parameters.get(ann.code());
                        if (value != null) {
                            field.setAccessible(true);

                            if (value instanceof EzyHashMap && field.getType().equals(CustomHashtable.class)) {
                                var valueEzyHashMap = (EzyHashMap)value;

                                var ezyObject = EzyEntityFactory.newObject();
                                ezyObject.putAll(valueEzyHashMap.toMap());

                                field.set(object, new CustomHashtable(ezyObject));
                            }
                            else field.set(object, value);
                        }
                    }
                    else {
                        if (parameters.containsKey(ann.code())) {
                            var value = parameters.get(ann.code());

                            field.setAccessible(true);

                            if ((value != null) && value instanceof EzyHashMap && field.getType().equals(CustomHashtable.class)) {
                                var valueEzyHashMap = (EzyHashMap)value;

                                var ezyObject = EzyEntityFactory.newObject();
                                ezyObject.putAll(valueEzyHashMap.toMap());

                                field.set(object, new CustomHashtable(ezyObject));
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
            var parameters1 = data.get(1, EzyObject.class);
            var parameters = new CustomHashtable(parameters1);
            operationRequest.setParameters(parameters != null ? parameters : new CustomHashtable());
            operationRequest.setRequestId(data.size() > 2 ? data.get(2) : -1);

            return operationRequest;
        } catch (Exception ex) {
            logger.error("createOperationRequest", ex);
        }

        return null;
    }
}