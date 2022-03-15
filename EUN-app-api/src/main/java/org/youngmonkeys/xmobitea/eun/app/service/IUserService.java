package org.youngmonkeys.xmobitea.eun.app.service;

import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import org.youngmonkeys.xmobitea.eun.app.event.OperationEvent;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.common.entity.SendParameters;

import java.util.Iterator;

public interface IUserService {
    boolean containsPeer(@NonNull String userId);

    EzyUser getPeer(@NonNull String userId);

    void sendOperationResponse(@NonNull EzyUser peer, @NonNull OperationResponse response);

    void sendOperationResponse(@NonNull EzyUser peer, @NonNull OperationResponse response, SendParameters sendParameters);

    void sendEvent(@NonNull String userId, @NonNull OperationEvent event);

    void sendEvent(@NonNull String userId, @NonNull OperationEvent event, SendParameters sendParameters);

    void sendEvent(@NonNull EzyUser peer, @NonNull OperationEvent event);

    void sendEvent(@NonNull EzyUser peer, @NonNull OperationEvent event, SendParameters sendParameters);

    void sendEventToSomePeer(@NonNull Iterable<EzyUser> peerLst, @NonNull OperationEvent event);

    void sendEventToSomePeer(@NonNull Iterable<EzyUser> peerLst, @NonNull OperationEvent event, SendParameters sendParameters);

    void sendEventToSomePeer(@NonNull Iterator<EzyUser> peerIterator, @NonNull OperationEvent event);

    void sendEventToSomePeer(@NonNull Iterator<EzyUser> peerIterator, @NonNull OperationEvent event, SendParameters sendParameters);

    void sendEventToSomePeerByUserIds(@NonNull Iterable<String> peerUserIdLst, @NonNull OperationEvent event);

    void sendEventToSomePeerByUserIds(@NonNull Iterable<String> peerUserIdLst, @NonNull OperationEvent event, SendParameters sendParameters);

    void sendEventToSomePeerByUserIds(@NonNull Iterator<String> peerUserIdIterator, @NonNull OperationEvent event);

    void sendEventToSomePeerByUserIds(@NonNull Iterator<String> peerUserIdIterator, @NonNull OperationEvent event, SendParameters sendParameters);

    void sendEventToAllOnlinePeer(@NonNull OperationEvent event);

    void sendEventToAllOnlinePeer(@NonNull OperationEvent event, SendParameters sendParameters);
}