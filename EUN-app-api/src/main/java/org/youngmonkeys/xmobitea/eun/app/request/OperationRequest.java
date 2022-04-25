package org.youngmonkeys.xmobitea.eun.app.request;

import lombok.*;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;
import org.youngmonkeys.xmobitea.eun.common.helper.CodeHelper;

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
        return CodeHelper.getOperationCodeName(operationCode) + " " + requestId + " " + parameters;
    }
}
