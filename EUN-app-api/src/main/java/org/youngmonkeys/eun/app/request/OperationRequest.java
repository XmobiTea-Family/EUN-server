package org.youngmonkeys.eun.app.request;

import lombok.*;
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

    public String toString() {
        return "" + operationCode + " " + requestId + " " + parameters.toData();
    }
}