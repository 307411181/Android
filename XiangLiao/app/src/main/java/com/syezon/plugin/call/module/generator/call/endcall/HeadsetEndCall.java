//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.module.generator.call.endcall;

import android.content.Context;
import com.syezon.plugin.call.common.util.StringUtil;
import com.syezon.plugin.call.module.generator.call.PhoneCallTool;
import com.syezon.plugin.call.module.generator.call.endcall.BaseEndCall;

public class HeadsetEndCall extends BaseEndCall {
    private static final String[] MODELS;

    static {
        String[] var0 = new String[]{StringUtil.lge};
        MODELS = var0;
    }

    public HeadsetEndCall() {
    }

    public void endCall(Context var1) {
        PhoneCallTool.getIntance(var1).endCallByP970();
    }

    public String[] getModels() {
        return MODELS;
    }
}
