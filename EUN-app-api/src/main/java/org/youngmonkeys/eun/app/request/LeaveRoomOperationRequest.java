package org.youngmonkeys.eun.app.request;

import lombok.Data;

@Data
public class LeaveRoomOperationRequest extends OperationRequest {
    @Override
    public boolean isValidRequest() {
        return super.isValidRequest();
    }
}