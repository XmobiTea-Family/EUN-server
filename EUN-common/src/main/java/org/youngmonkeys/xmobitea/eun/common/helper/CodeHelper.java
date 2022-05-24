package org.youngmonkeys.xmobitea.eun.common.helper;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.common.constant.EventCode;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ReturnCode;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@EzySingleton
public class CodeHelper extends EzyLoggable {
    private static final String UnknownCode = "Unknown";

    private static Map<Integer, String> operationCodeDic;
    private static Map<Integer, String> eventCodeDic;
    private static Map<Integer, String> returnCodeDic;

    public static String getOperationCodeName(int operationCode) {
        return operationCodeDic.getOrDefault(operationCode, UnknownCode);
    }

    public static String getEventCodeName(int eventCode) {
        return eventCodeDic.getOrDefault(eventCode, UnknownCode);
    }

    public static String getReturnCodeName(int returnCode) {
        return returnCodeDic.getOrDefault(returnCode, UnknownCode);
    }

    public CodeHelper() {
        try {
            setOperationCodeDic();
            setEventCodeDic();
            setReturnCodeDic();
        }
        catch (Exception ex) {
            logger.error("CodeHelper constructor", ex);
        }
    }

    private static void setOperationCodeDic() throws IllegalAccessException {
        operationCodeDic = new HashMap<>();

        var fields = OperationCode.class.getDeclaredFields();
        for (var field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                operationCodeDic.put(field.getInt(null), field.getName());
            }
        }
    }

    private static void setEventCodeDic() throws IllegalAccessException {
        eventCodeDic = new HashMap<>();

        var fields = EventCode.class.getDeclaredFields();
        for (var field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                eventCodeDic.put(field.getInt(null), field.getName());
            }
        }
    }

    private static void setReturnCodeDic() throws IllegalAccessException {
        returnCodeDic = new HashMap<>();

        var fields = ReturnCode.class.getDeclaredFields();
        for (var field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                returnCodeDic.put(field.getInt(null), field.getName());
            }
        }
    }
}
