package org.youngmonkeys.eun.app.request;

import lombok.*;
import org.youngmonkeys.eun.common.constant.OperationCode;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

@Data
public class OperationRequest {
    Integer operationCode;
    Integer requestId;
    CustomHashtable parameters;

    boolean isValid;

    public boolean isValidRequest() {
        return isValid;
    }

    @Override
    public String toString() {
        return "" + OperationCode.getOperationName(operationCode) + " " + requestId + " " + parameters.toString();
    }
}