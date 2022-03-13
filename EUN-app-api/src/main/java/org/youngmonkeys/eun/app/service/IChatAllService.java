package org.youngmonkeys.eun.app.service;

import lombok.NonNull;

import java.util.Iterator;

public interface IChatAllService {
    boolean addChatPeer(@NonNull String userId);

    boolean removeChatPeer(@NonNull String userId);

    Iterator<String> getChatPeerUserIdIterator();
}
