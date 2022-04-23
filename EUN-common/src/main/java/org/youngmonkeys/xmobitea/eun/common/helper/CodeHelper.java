package org.youngmonkeys.xmobitea.eun.common.helper;

import lombok.var;
import org.youngmonkeys.xmobitea.eun.common.constant.EventCode;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ReturnCode;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public final class CodeHelper {
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

    CodeHelper() throws IllegalAccessException {
        setOperationCodeDic();
        setEventCodeDic();
        setReturnCodeDic();
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
