//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.module.generator.call;

import android.content.Context;
import com.syezon.plugin.call.common.util.DeviceInfo;
import com.syezon.plugin.call.module.generator.call.PhoneCallTool;
import com.syezon.plugin.call.module.generator.call.answer.AnswerStyleMatcher;
import com.syezon.plugin.call.module.generator.call.answer.BaseAnswer;
import com.syezon.plugin.call.module.generator.call.endcall.EndCallWayMatcher;

public class CallControlGenerator {
    public CallControlGenerator() {
    }

    public static void answer(Context var0) {
        AnswerStyleMatcher.getAnswerMatcher(var0).answer(var0);
    }

    public static void endCall(Context var0) {
        EndCallWayMatcher.getLogMatcher(var0).endCall(var0);
    }

    public static void headsetControl(Context var0) {
        BaseAnswer var1 = AnswerStyleMatcher.getAnswerMatcher(var0);
        PhoneCallTool.getIntance(var0).headsetPress(var1);
    }

    public static void headsetLongPress(Context var0) {
        PhoneCallTool.getIntance(var0).headsetLongPress(DeviceInfo.getDeviceType());
    }
}
