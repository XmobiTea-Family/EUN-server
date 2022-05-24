package org.youngmonkeys.xmobitea.eun.app.service;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import com.tvd12.ezyfoxserver.wrapper.EzyUserManager;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.event.OperationEvent;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.common.constant.Commands;
import org.youngmonkeys.xmobitea.eun.common.entity.SendParameters;

import java.util.Iterator;
import java.util.LinkedList;

@EzySingleton
public class UserService extends EzyLoggable implements IUserService {
    @EzyAutoBind
    private EzyResponseFactory responseFactory;

    @EzyAutoBind
    private EzyUserManager userManager;

    @Override
    public boolean containsPeer(@NonNull String userId) {
        return userManager.containsUser(userId);
    }

    @Override
    public EzyUser getPeer(@NonNull String userId) {
        return userManager.getUser(userId);
    }

    @Override
    public void sendOperationResponse(@NonNull EzyUser peer, @NonNull OperationResponse response) {
        sendOperationResponse(peer, response, null);
    }

    @Override
    public void sendOperationResponse(@NonNull EzyUser peer, @NonNull OperationResponse response, SendParameters sendParameters) {
        var ezyResponse = responseFactory.newArrayResponse()
                .command(Commands.ResponseCmd)
                .user(peer)
                .data(response.toData());

        if (sendParameters != null) {
            if (sendParameters.isUnreliable() && peer.getSession().getUdpClientAddress() != null) ezyResponse.udpTransport();
            if (sendParameters.isEncrypted()) ezyResponse.encrypted();
        }

        ezyResponse.execute();
    }

    @Override
    public void sendEvent(@NonNull String userId, @NonNull OperationEvent event) {
        sendEvent(userId, event, null);
    }

    @Override
    public void sendEvent(@NonNull String userId, @NonNull OperationEvent event, SendParameters sendParameters) {
        var ezyResponse = responseFactory.newArrayResponse()
                .command(Commands.EventCmd)
                .username(userId)
                .data(event.toData());

        if (sendParameters != null) {
//            if (sendParameters.isUnreliable()) ezyResponse.udpTransport();
            if (sendParameters.isEncrypted()) ezyResponse.encrypted();
        }

        ezyResponse.execute();
    }

    @Override
    public void sendEvent(@NonNull EzyUser peer, @NonNull OperationEvent event) {
        sendEvent(peer, event, null);
    }

    @Override
    public void sendEvent(@NonNull EzyUser peer, @NonNull OperationEvent event, SendParameters sendParameters) {
        var ezyResponse = responseFactory.newArrayResponse()
                .command(Commands.EventCmd)
                .user(peer)
                .data(event.toData());

        if (sendParameters != null) {
            if (sendParameters.isUnreliable() && peer.getSession().getUdpClientAddress() != null) ezyResponse.udpTransport();
            if (sendParameters.isEncrypted()) ezyResponse.encrypted();
        }

        ezyResponse.execute();
    }

    @Override
    public void sendEventToSomePeer(@NonNull Iterable<EzyUser> peerLst, @NonNull OperationEvent event) {
        sendEventToSomePeer(peerLst, event, null);
    }

    @Override
    public void sendEventToSomePeer(@NonNull Iterable<EzyUser> peerLst, @NonNull OperationEvent event, SendParameters sendParameters) {
        var ezyResponse = responseFactory.newArrayResponse()
                .command(Commands.EventCmd)
                .users(peerLst)
                .data(event.toData());

        if (sendParameters != null) {
//            if (sendParameters.isUnreliable()) ezyResponse.udpTransport();
            if (sendParameters.isEncrypted()) ezyResponse.encrypted();
        }

        ezyResponse.execute();
    }

    @Override
    public void sendEventToSomePeer(@NonNull Iterator<EzyUser> peerIterator, @NonNull OperationEvent event) {
        sendEventToSomePeer(peerIterator, event, null);
    }

    @Override
    public void sendEventToSomePeer(@NonNull Iterator<EzyUser> peerIterator, @NonNull OperationEvent event, SendParameters sendParameters) {
        var peerLst = new LinkedList<EzyUser>();
        while (peerIterator.hasNext()) {
            peerLst.add(peerIterator.next());
        }

        if (peerLst.size() == 0) return;

        sendEventToSomePeer(peerLst, event, sendParameters);
    }

    @Override
    public void sendEventToSomePeerByUserIds(@NonNull Iterable<String> peerUserIdLst, @NonNull OperationEvent event) {
        sendEventToSomePeerByUserIds(peerUserIdLst, event, null);
    }

    @Override
    public void sendEventToSomePeerByUserIds(@NonNull Iterable<String> peerUserIdLst, @NonNull OperationEvent event, SendParameters sendParameters) {
        var ezyResponse = responseFactory.newArrayResponse()
                .command(Commands.EventCmd)
                .usernames(peerUserIdLst)
                .data(event.toData());

        if (sendParameters != null) {
//            if (sendParameters.isUnreliable()) ezyResponse.udpTransport();
            if (sendParameters.isEncrypted()) ezyResponse.encrypted();
        }

        ezyResponse.execute();
    }

    @Override
    public void sendEventToSomePeerByUserIds(@NonNull Iterator<String> peerUserIdIterator, @NonNull OperationEvent event) {
        sendEventToSomePeerByUserIds(peerUserIdIterator, event, null);
    }

    @Override
    public void sendEventToSomePeerByUserIds(@NonNull Iterator<String> peerUserIdIterator, @NonNull OperationEvent event, SendParameters sendParameters) {
        var peerUserIdLst = new LinkedList<String>();
        while (peerUserIdIterator.hasNext()) {
            peerUserIdLst.add(peerUserIdIterator.next());
        }

        if (peerUserIdLst.size() == 0) return;

        sendEventToSomePeerByUserIds(peerUserIdLst, event, sendParameters);
    }

    @Override
    public void sendEventToAllOnlinePeer(@NonNull OperationEvent event) {
        sendEventToAllOnlinePeer(event, null);
    }

    @Override
    public void sendEventToAllOnlinePeer(@NonNull OperationEvent event, SendParameters sendParameters) {
        var ezyResponse =         responseFactory.newArrayResponse()
                .command(Commands.EventCmd)
                .users(userManager.getUserList())
                .data(event.toData());

        if (sendParameters != null) {
//            if (sendParameters.isUnreliable()) ezyResponse.udpTransport();
            if (sendParameters.isEncrypted()) ezyResponse.encrypted();
        }

        ezyResponse.execute();
    }
}
