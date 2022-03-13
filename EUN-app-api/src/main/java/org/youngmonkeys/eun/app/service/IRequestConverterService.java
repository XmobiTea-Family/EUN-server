package org.youngmonkeys.eun.app.service;

import lombok.NonNull;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.request.base.Request;

public interface IRequestConverterService {
    <T extends OperationRequest> T createOperationRequest(@NonNull OperationRequest operationRequest, @NonNull Class<T> objectType);

    OperationRequest createOperationRequest(@NonNull Request request);
}