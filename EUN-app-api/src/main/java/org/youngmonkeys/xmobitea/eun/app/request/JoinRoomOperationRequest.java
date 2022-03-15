package org.youngmonkeys.xmobitea.eun.app.request;

import lombok.Data;
import org.youngmonkeys.xmobitea.eun.app.entity.EzyDataMember;
import org.youngmonkeys.xmobitea.eun.common.constant.ParameterCode;

@Data
public class JoinRoomOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.RoomId)
    int roomId;

    @EzyDataMember(code = ParameterCode.Password, isOptional = true)
    String password;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (roomId < 0) return false;

        return true;
    }
}