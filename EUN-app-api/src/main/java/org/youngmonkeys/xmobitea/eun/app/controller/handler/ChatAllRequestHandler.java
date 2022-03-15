package org.youngmonkeys.xmobitea.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.event.OperationEvent;
import org.youngmonkeys.xmobitea.eun.app.request.ChatAllOperationRequest;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.app.controller.handler.base.RequestHandler;
import org.youngmonkeys.xmobitea.eun.app.service.IChatAllService;
import org.youngmonkeys.xmobitea.eun.common.constant.EventCode;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ParameterCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ReturnCode;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EzySingleton
public class ChatAllRequestHandler extends RequestHandler {
    protected ExecutorService threadPool;

    @EzyAutoBind
    private IChatAllService chatAllService;

    @Override
    public Integer getCode() {
        return OperationCode.ChatAll;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, ChatAllOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var userId = peer.getName();
        var message = request.getMessage();

        var onChatAllEvent = new OperationEvent(EventCode.OnChatAll);
        var parameters = new EUNHashtable();
        parameters.put(ParameterCode.Message, new Object[] {
                userId,
                message,
                System.currentTimeMillis()
        });
        onChatAllEvent.setParameters(parameters);

        threadPool.execute(() -> userService.sendEventToSomePeerByUserIds(chatAllService.getChatPeerUserIdIterator(), onChatAllEvent));

        var response = new OperationResponse(operationRequest);
        response.setReturnCode(ReturnCode.Ok);
        return response;
    }

    public ChatAllRequestHandler() {
        threadPool = Executors.newSingleThreadExecutor();
    }
}