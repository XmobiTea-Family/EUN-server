package org.youngmonkeys.eun.app.request;

import com.tvd12.ezyfox.io.EzyStrings;
import lombok.Data;
import org.youngmonkeys.eun.app.entity.EzyDataMember;
import org.youngmonkeys.eun.common.constant.ParameterCode;

@Data
public class ChatAllOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.Message)
    String message;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (EzyStrings.isNoContent(message)) return false;

        return true;
    }
}