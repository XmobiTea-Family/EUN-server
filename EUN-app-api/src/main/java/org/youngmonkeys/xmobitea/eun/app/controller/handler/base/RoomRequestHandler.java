package org.youngmonkeys.xmobitea.eun.app.controller.handler.base;

import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.entity.ILobby;
import org.youngmonkeys.xmobitea.eun.app.entity.IRoom;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.common.constant.PeerPropertyCode;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;

import java.util.LinkedList;
import java.util.List;

public class RoomRequestHandler extends LobbyRequestHandler {
    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        return null;
    }

    protected final IRoom getCurrentRoomPeer(@NonNull EzyUser peer) {
        var currentRoom = peer.getProperty(PeerPropertyCode.Room, IRoom.class);

        return currentRoom;
    }

    public static List<IRoom> getExpectedRoomLst(ILobby currentLobby, EUNHashtable expectedProperties, int targetExpectedCount) {
        var roomIterator = currentLobby.getRoomIterator();

        var expectedRoomLst = new LinkedList<IRoom>();
        while (roomIterator.hasNext()) {
            var conditionTrueCount = 0;

            var roomItem = roomIterator.next();

            if (!roomItem.getIsOpen()) continue;
            if (!roomItem.getIsVisible()) continue;
            if (roomItem.getPlayerCount() == 0) continue;
            if (roomItem.getPlayerCount() >= roomItem.getMaxPlayer()) continue;
            if (!EzyStrings.isNoContent(roomItem.getPassword())) continue;

            if (expectedProperties != null) {

                var roomCustomRoomProperties = roomItem.getCustomRoomProperties();

                var keySet = expectedProperties.keySet();
                for (var key : keySet) {
                    var expectedValue = expectedProperties.getObject(key, null);
                    var roomValue = roomCustomRoomProperties.getObject(key, null);

                    if (expectedValue == null && roomValue == null) conditionTrueCount++;
                    else {
                        if (expectedValue != null) {
                            if (expectedValue.equals(roomValue)) {
                                conditionTrueCount++;
                            }
                        }
                    }

                    if (conditionTrueCount >= targetExpectedCount) break;
                }
            }

            if (conditionTrueCount < targetExpectedCount) continue;

            expectedRoomLst.add(roomItem);
        }

        return expectedRoomLst;
    }
}
