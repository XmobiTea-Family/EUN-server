package org.youngmonkeys.xmobitea.eun.app.event;

import lombok.Data;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;

@Data
public class OperationEvent {
    Integer eventCode;
    EUNHashtable parameters;

    public OperationEvent(Integer eventCode) {
        this.eventCode = eventCode;
    }

    public Object toData() {
        return new Object[] {
                eventCode,
                parameters == null ? null : parameters.toData()
        };
    }
}