package org.youngmonkeys.eun.app.request;

import lombok.Data;
import org.youngmonkeys.eun.app.entity.EzyDataMember;
import org.youngmonkeys.eun.common.constant.ParameterCode;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

@Data
public class ChangeRoomInfoOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.CustomHashtable)
    CustomHashtable customHashtable;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (customHashtable == null || customHashtable.size() == 0) return false;

        return true;
    }
}