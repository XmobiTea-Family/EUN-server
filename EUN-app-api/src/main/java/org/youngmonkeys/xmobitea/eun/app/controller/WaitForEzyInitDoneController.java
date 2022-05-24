package org.youngmonkeys.xmobitea.eun.app.controller;

import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationAfter;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.controller.handler.base.IWaitForEzyInitDone;

import java.util.List;

@EzyConfigurationAfter
public class WaitForEzyInitDoneController implements EzyBeanConfig {
    @EzyAutoBind
    private EzyBeanContext beanContext;

    @Override
    public void config() {
        List<IWaitForEzyInitDone> handlers = beanContext.getSingletonsOf(IWaitForEzyInitDone.class);
        for (var handler : handlers) {
            handler.config();
        }
    }
}
