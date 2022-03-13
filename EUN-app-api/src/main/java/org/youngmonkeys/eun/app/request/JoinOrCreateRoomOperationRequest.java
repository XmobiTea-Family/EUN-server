package org.youngmonkeys.eun.app.request;

import lombok.Data;
import org.youngmonkeys.eun.app.entity.EzyDataMember;
import org.youngmonkeys.eun.common.constant.ParameterCode;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

import java.util.List;

@Data
public class JoinOrCreateRoomOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.MaxPlayer)
    int maxPlayer;

    @EzyDataMember(code = ParameterCode.TargetExpectedCount, isOptional = true)
    int targetExpectedCount;

    @EzyDataMember(code = ParameterCode.ExpectedProperties, isOptional = true)
    CustomHashtable expectedProperties;

    @EzyDataMember(code = ParameterCode.CustomRoomProperties, isOptional = true)
    CustomHashtable customRoomProperties;

    @EzyDataMember(code = ParameterCode.IsVisible, isOptional = true)
    boolean isVisible = true;

    @EzyDataMember(code = ParameterCode.IsOpen, isOptional = true)
    boolean isOpen = true;

    @EzyDataMember(code = ParameterCode.CustomRoomPropertiesForLobby, isOptional = true)
    List<Integer> customRoomPropertiesForLobby;

    @EzyDataMember(code = ParameterCode.Password, isOptional = true)
    String password;

    @EzyDataMember(code = ParameterCode.Ttl, isOptional = true)
    int ttl;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (maxPlayer < 2) return false;
        if (expectedProperties != null && targetExpectedCount <= 0) return false;
        if (expectedProperties == null && targetExpectedCount > 0) return false;

        return true;
    }
}