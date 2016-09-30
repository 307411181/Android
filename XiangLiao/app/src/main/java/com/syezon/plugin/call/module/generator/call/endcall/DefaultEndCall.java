//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.module.generator.call.endcall;

import android.content.Context;
import com.syezon.plugin.call.module.generator.call.PhoneCallTool;
import com.syezon.plugin.call.module.generator.call.endcall.BaseEndCall;

public class DefaultEndCall extends BaseEndCall {
    private static final String[] MODELS = new String[0];

    public DefaultEndCall() {
    }

    public void endCall(Context var1) {
        PhoneCallTool.getIntance(var1).endCall();
    }

    public String[] getModels() {
        return MODELS;
    }
}
