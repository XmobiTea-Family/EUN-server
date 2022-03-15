package org.youngmonkeys.eun.app.event;

import lombok.Data;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

@Data
public class OperationEvent {
    Integer eventCode;
    CustomHashtable parameters;

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
