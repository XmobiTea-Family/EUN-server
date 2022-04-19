package org.youngmonkeys.xmobitea.eun.app.response;

import lombok.Data;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;

@Data
public class OperationResponse {
    int operationCode;
    int returnCode;
    String debugMessage;
    EUNHashtable parameters;

    Integer responseId;

    public OperationResponse(OperationRequest operationRequest)
    {
        operationCode = operationRequest.getOperationCode();
        responseId = operationRequest.getRequestId();
    }

    public Object toData() {
        return new Object[] {
                returnCode,
                returnCode == 0 ? (parameters == null ? null : parameters.toEzyData()) : debugMessage,
                responseId
        };
    }
}