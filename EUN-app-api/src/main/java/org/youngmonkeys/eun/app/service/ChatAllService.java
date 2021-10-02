package org.youngmonkeys.eun.app.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.NonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

@EzySingleton
public class ChatAllService implements IChatAllService {
    private List<String> chatUserIdLst;

    @Override
    public boolean addChatPeer(@NonNull String userId) {
        if (chatUserIdLst.contains(userId)) return false;

        chatUserIdLst.add(userId);
        return true;
    }

    @Override
    public boolean removeChatPeer(@NonNull String userId) {
        if (!chatUserIdLst.contains(userId)) return false;

        chatUserIdLst.remove(userId);
        return true;
    }

    @Override
    public Iterator<String> getChatPeerUserIdIterator() {
        return chatUserIdLst.iterator();
    }

    public ChatAllService() {
        chatUserIdLst = new Vector<>();
    }
}
