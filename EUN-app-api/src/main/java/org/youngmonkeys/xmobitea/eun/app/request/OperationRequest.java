package org.youngmonkeys.xmobitea.eun.app.request;

import lombok.*;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;

@Data
public class OperationRequest {
    Integer operationCode;
    Integer requestId;
    EUNHashtable parameters;

    boolean isValid;

    public boolean isValidRequest() {
        return isValid;
    }

    @Override
    public String toString() {
        return "" + OperationCode.getOperationName(operationCode) + " " + requestId + " " + parameters.toString();
    }
}