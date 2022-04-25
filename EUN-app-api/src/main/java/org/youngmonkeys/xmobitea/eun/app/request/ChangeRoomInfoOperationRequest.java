package org.youngmonkeys.xmobitea.eun.app.request;

import lombok.Data;
import org.youngmonkeys.xmobitea.eun.app.entity.EzyDataMember;
import org.youngmonkeys.xmobitea.eun.common.constant.ParameterCode;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;

@Data
public class ChangeRoomInfoOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.EUNHashtable)
    EUNHashtable eunHashtable;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (eunHashtable == null || eunHashtable.count() == 0) return false;

        return true;
    }
}
