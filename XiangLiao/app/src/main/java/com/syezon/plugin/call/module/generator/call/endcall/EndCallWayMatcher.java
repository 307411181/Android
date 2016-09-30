//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.module.generator.call.endcall;

import android.content.Context;
import com.syezon.plugin.call.common.config.CallConfig;
import com.syezon.plugin.call.module.generator.call.endcall.BaseEndCall;
import com.syezon.plugin.call.module.generator.call.endcall.DefaultEndCall;
import com.syezon.plugin.call.module.generator.call.endcall.HeadsetEndCall;

public class EndCallWayMatcher {
    private static final Class[] endcallMatchers = new Class[]{HeadsetEndCall.class};

    public EndCallWayMatcher() {
    }

    public static BaseEndCall getLogMatcher(Context var0) {
        switch(CallConfig.getInstance(var0).getEndCallType()) {
        case 0:
            return new HeadsetEndCall();
        case 10:
            return new DefaultEndCall();
        default:
            int var1 = 0;

            for(; var1 < endcallMatchers.length; ++var1) {
                try {
                    BaseEndCall var3 = (BaseEndCall)endcallMatchers[var1].newInstance();
                    if(var3.match()) {
                        CallConfig.getInstance(var0).setEndCallType(var1);
                        return var3;
                    }
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }

            CallConfig.getInstance(var0).setEndCallType(endcallMatchers.length);
            return new DefaultEndCall();
        }
    }
}
