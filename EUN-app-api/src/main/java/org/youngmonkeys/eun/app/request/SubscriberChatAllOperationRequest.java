package org.youngmonkeys.eun.app.request;

import lombok.Data;
import org.youngmonkeys.eun.app.entity.EzyDataMember;
import org.youngmonkeys.eun.common.constant.ParameterCode;

@Data
public class SubscriberChatAllOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.Subscribe)
    boolean isSubscribe;

    @Override
    public boolean isValidRequest() {
        return super.isValidRequest();
    }
}
