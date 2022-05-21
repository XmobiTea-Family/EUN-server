package org.youngmonkeys.xmobitea.eun.app.request;

import lombok.Data;
import org.youngmonkeys.xmobitea.eun.app.entity.EzyDataMember;
import org.youngmonkeys.xmobitea.eun.common.constant.ParameterCode;

@Data
public class JoinLobbyOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.LobbyId)
    int lobbyId;

    @Override
    public boolean isValidRequest() {
        return super.isValidRequest();
    }
}
