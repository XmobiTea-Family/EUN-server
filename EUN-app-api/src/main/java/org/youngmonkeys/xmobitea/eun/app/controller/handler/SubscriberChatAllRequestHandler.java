package org.youngmonkeys.xmobitea.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.controller.handler.base.RequestHandler;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.request.SubscriberChatAllOperationRequest;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.app.service.IChatAllService;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ReturnCode;

@EzySingleton
public class SubscriberChatAllRequestHandler extends RequestHandler {
    @EzyAutoBind
    private IChatAllService chatAllService;

    @Override
    public Integer getCode() {
        return OperationCode.SubscriberChatAll;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, SubscriberChatAllOperationRequest.class);

        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var userId = peer.getName();
        if (request.isSubscribe()) chatAllService.addChatPeer(userId);
        else chatAllService.removeChatPeer(userId);

        var response = new OperationResponse(operationRequest);
        response.setReturnCode(ReturnCode.Ok);

        return response;
    }
}