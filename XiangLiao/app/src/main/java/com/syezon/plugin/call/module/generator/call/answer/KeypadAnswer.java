//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.module.generator.call.answer;

import android.content.Context;
import com.syezon.plugin.call.module.generator.call.PhoneCallTool;
import com.syezon.plugin.call.module.generator.call.answer.BaseAnswer;

public class KeypadAnswer extends BaseAnswer {
    private static final String[] MODELS = new String[0];

    public KeypadAnswer() {
    }

    public void answer(Context var1) {
        try {
            PhoneCallTool.getIntance(var1).answer();
        } catch (Throwable var3) {
            PhoneCallTool.getIntance(var1).answerRingCallByKey();
        }
    }

    public String[] getModels() {
        return MODELS;
    }
}
