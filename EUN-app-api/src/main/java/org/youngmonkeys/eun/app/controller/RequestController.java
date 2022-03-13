package org.youngmonkeys.eun.app.controller;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyDoHandle;
import com.tvd12.ezyfox.core.annotation.EzyRequestController;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.var;
import org.youngmonkeys.eun.app.controller.handler.base.IWaitForEzyInitDone;
import org.youngmonkeys.eun.common.constant.Commands;
import org.youngmonkeys.eun.app.controller.handler.base.IRequestHandler;
import org.youngmonkeys.eun.app.request.base.Request;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.app.service.IRequestConverterService;
import org.youngmonkeys.eun.app.service.IUserService;
import org.youngmonkeys.eun.common.constant.ReturnCode;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EzySingleton
@EzyRequestController
public final class RequestController extends EzyLoggable implements IWaitForEzyInitDone {
    @EzyAutoBind
    private IUserService userService;

    @EzyAutoBind
    private EzyBeanContext beanContext;

    @EzyAutoBind
    private IRequestConverterService requestConverterService;

    private Map<Integer, IRequestHandler> requestHandlerDic;

    private ExecutorService threadPool;

    @EzyDoHandle(Commands.RequestCmd)
    public void handleRequest(Request request, EzyUser peer) {
        threadPool.execute(() -> {
            var operationRequest = requestConverterService.createOperationRequest(request);
            if (operationRequest == null) return;

            if (operationRequest.getRequestId() != -1) {
                logger.info("[RECV] " + operationRequest.toString());
            }

            var requestHandler = getRequestHandler(operationRequest.getOperationCode());

            if (requestHandler == null) {
                logger.error(operationRequest.getOperationCode() + " is null request ");
                return;
            }

            OperationResponse operationResponse;

            try {
                operationResponse = requestHandler.handle(peer, operationRequest);
            }
            catch (Exception ex) {
                logger.error("handleRequest", ex);

                operationResponse = new OperationResponse(operationRequest);
                operationResponse.setReturnCode(ReturnCode.InternalServerError);
            }

            if (operationResponse != null && operationResponse.getResponseId() >= 0) {
                logger.info("[SEND] " + operationResponse);
                userService.sendOperationResponse(peer, operationResponse);
            }
        });
    }

    private IRequestHandler getRequestHandler(int requestTypeCode) {
        return requestHandlerDic.getOrDefault(requestTypeCode, null);
    }

    public RequestController() {
        threadPool = Executors.newFixedThreadPool(32);
        requestHandlerDic = new Hashtable<>();
    }

    @Override
    public void config() {
        List<IRequestHandler> handlers = beanContext.getSingletonsOf(IRequestHandler.class);
        for (var handler : handlers) {
            if (handler.getCode() != null) requestHandlerDic.put(handler.getCode(), handler);
        }

        logger.info("RequestController handler count " + handlers.size() + "/" + requestHandlerDic.size());
    }
}
