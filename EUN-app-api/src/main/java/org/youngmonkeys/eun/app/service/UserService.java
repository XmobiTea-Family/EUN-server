package org.youngmonkeys.eun.app.service;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import com.tvd12.ezyfoxserver.support.factory.EzyResponseFactory;
import com.tvd12.ezyfoxserver.wrapper.EzyUserManager;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.event.OperationEvent;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.common.constant.Commands;

import java.util.Iterator;
import java.util.LinkedList;

@EzySingleton
public class UserService implements IUserService {
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
        responseFactory.newArrayResponse()
                .command(Commands.ResponseCmd)
                .user(peer)
                .data(response.toData())
                .execute();
    }

    @Override
    public void sendEvent(@NonNull String userId, @NonNull OperationEvent event) {
        responseFactory.newArrayResponse()
                .command(Commands.EventCmd)
                .username(userId)
                .data(event.toData())
                .execute();
    }

    @Override
    public void sendEvent(@NonNull EzyUser peer, @NonNull OperationEvent event) {
        responseFactory.newArrayResponse()
                .command(Commands.EventCmd)
                .user(peer)
                .data(event.toData())
                .execute();
    }

    @Override
    public void sendEventToSomePeer(@NonNull Iterable<EzyUser> peerLst, @NonNull OperationEvent event) {
        responseFactory.newArrayResponse()
                .command(Commands.EventCmd)
                .users(peerLst)
                .data(event.toData())
                .execute();
    }

    @Override
    public void sendEventToSomePeer(@NonNull Iterator<EzyUser> peerIterator, @NonNull OperationEvent event) {
        var peerLst = new LinkedList<EzyUser>();
        while (peerIterator.hasNext()) {
            peerLst.add(peerIterator.next());
        }

        if (peerLst.size() == 0) return;

        sendEventToSomePeer(peerLst, event);
    }

    @Override
    public void sendEventToSomePeerByUserIds(@NonNull Iterable<String> peerUserIdLst, @NonNull OperationEvent event) {
        responseFactory.newArrayResponse()
                .command(Commands.EventCmd)
                .usernames(peerUserIdLst)
                .data(event.toData())
                .execute();
    }

    @Override
    public void sendEventToSomePeerByUserIds(@NonNull Iterator<String> peerUserIdIterator, @NonNull OperationEvent event) {
        var peerUserIdLst = new LinkedList<String>();
        while (peerUserIdIterator.hasNext()) {
            peerUserIdLst.add(peerUserIdIterator.next());
        }

        if (peerUserIdLst.size() == 0) return;

        sendEventToSomePeerByUserIds(peerUserIdLst, event);
    }

    @Override
    public void sendEventToAllOnlinePeer(@NonNull OperationEvent event) {
        responseFactory.newArrayResponse()
                .command(Commands.EventCmd)
                .users(userManager.getUserList())
                .data(event.toData())
                .execute();
    }
}