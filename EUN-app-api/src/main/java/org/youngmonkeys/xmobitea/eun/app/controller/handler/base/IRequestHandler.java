package org.youngmonkeys.xmobitea.eun.app.controller.handler.base;

import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;

public interface IRequestHandler {
    Integer getCode();

    OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest);
}
