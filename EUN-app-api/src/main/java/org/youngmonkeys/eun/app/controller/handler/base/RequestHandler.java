package org.youngmonkeys.eun.app.controller.handler.base;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.app.service.IRequestConverterService;
import org.youngmonkeys.eun.app.service.IUserService;
import org.youngmonkeys.eun.common.constant.ReturnCode;

public abstract class RequestHandler extends EzyLoggable implements IRequestHandler {
    @EzyAutoBind
    protected IRequestConverterService requestConverterService;

    @EzyAutoBind
    protected IUserService userService;

    protected final OperationResponse newInvalidRequestParameters(@NonNull OperationRequest operationRequest) {
        var response = new OperationResponse(operationRequest);
        response.setReturnCode(ReturnCode.InvalidRequestParameters);

        return response;
    }

    protected final OperationResponse newOperationInvalid(@NonNull OperationRequest operationRequest) {
        var response = new OperationResponse(operationRequest);
        response.setReturnCode(ReturnCode.OperationInvalid);

        return response;
    }

    protected final OperationResponse newOperationInvalid(@NonNull OperationRequest operationRequest, String debugMessage) {
        var response = newOperationInvalid(operationRequest);
        response.setDebugMessage(debugMessage);

        return response;
    }

    @Override
    public abstract Integer getCode();

    @Override
    public abstract OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest);
}
