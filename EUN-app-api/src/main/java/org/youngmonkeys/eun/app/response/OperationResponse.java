package org.youngmonkeys.eun.app.response;

import lombok.Data;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

@Data
public class OperationResponse {
    int operationCode;
    int returnCode;
    String debugMessage;
    CustomHashtable parameters;

    Integer responseId;

    public OperationResponse(OperationRequest operationRequest)
    {
        operationCode = operationRequest.getOperationCode();
        responseId = operationRequest.getRequestId();
    }

    public Object toData() {
        return new Object[] {
                returnCode,
                returnCode == 0 ? (parameters == null ? null : parameters.toData()) : debugMessage,
                responseId
        };
    }
}