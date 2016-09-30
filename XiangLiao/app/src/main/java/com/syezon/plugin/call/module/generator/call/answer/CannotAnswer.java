//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.module.generator.call.answer;

import android.content.Context;
import com.syezon.plugin.call.module.generator.call.PhoneCallTool;

public class CannotAnswer extends BaseAnswer {
    private static final String[] MODELS = new String[]{"hs-u8", "a60", "a520", "a750", "a500", "a65", "a366", "lenovo p700"};

    public CannotAnswer() {
    }

    public void answer(Context var1) {
        try {
            PhoneCallTool.getIntance(var1).answer();
        } catch (Throwable var3) {
            var3.printStackTrace();
        }
    }

    public String[] getModels() {
        return MODELS;
    }
}
