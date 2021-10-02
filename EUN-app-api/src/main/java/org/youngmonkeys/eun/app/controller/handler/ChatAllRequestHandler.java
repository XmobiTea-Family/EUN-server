package org.youngmonkeys.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.controller.handler.base.RequestHandler;
import org.youngmonkeys.eun.app.event.OperationEvent;
import org.youngmonkeys.eun.app.request.ChatAllOperationRequest;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.app.service.IChatAllService;
import org.youngmonkeys.eun.common.constant.EventCode;
import org.youngmonkeys.eun.common.constant.OperationCode;
import org.youngmonkeys.eun.common.constant.ParameterCode;
import org.youngmonkeys.eun.common.constant.ReturnCode;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

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
        var parameters = new CustomHashtable();
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