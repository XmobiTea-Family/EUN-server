package org.youngmonkeys.xmobitea.eun.app.request.base;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.entity.EzyArray;
import lombok.Data;

@Data
@EzyObjectBinding
public class Request {
    EzyArray d;

    public EzyArray getData() {
        return d;
    }
}
