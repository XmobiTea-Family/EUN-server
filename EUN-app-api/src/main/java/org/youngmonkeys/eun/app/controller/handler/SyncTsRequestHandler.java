package org.youngmonkeys.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.controller.handler.base.LobbyRequestHandler;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.request.SyncTsOperationRequest;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.common.constant.OperationCode;
import org.youngmonkeys.eun.common.constant.ParameterCode;
import org.youngmonkeys.eun.common.constant.ReturnCode;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

@EzySingleton
public class SyncTsRequestHandler extends LobbyRequestHandler {
    @Override
    public Integer getCode() {
        return OperationCode.SyncTs;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, SyncTsOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var response = new OperationResponse(operationRequest);
        response.setReturnCode(ReturnCode.Ok);

        var parameters = new CustomHashtable();
        parameters.put(ParameterCode.Ts, System.currentTimeMillis());
        response.setParameters(parameters);

        return response;
    }
}