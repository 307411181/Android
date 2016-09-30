//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.module.generator.call.answer;

import android.content.Context;
import com.syezon.plugin.call.common.config.CallConfig;
import com.syezon.plugin.call.common.log.LogHelper;
import com.syezon.plugin.call.module.generator.call.answer.BaseAnswer;
import com.syezon.plugin.call.module.generator.call.answer.CannotAnswer;
import com.syezon.plugin.call.module.generator.call.answer.EnsureHeadsetAnswer;
import com.syezon.plugin.call.module.generator.call.answer.KeypadAnswer;
import com.syezon.plugin.call.module.generator.call.answer.TryHeadsetAnswer;

public class AnswerStyleMatcher {
    private static final String TAG = AnswerStyleMatcher.class.getName();
    private static final Class<?>[] answerMatchers = new Class[]{CannotAnswer.class, EnsureHeadsetAnswer.class, TryHeadsetAnswer.class};

    public AnswerStyleMatcher() {
    }

    public static BaseAnswer getAnswerMatcher(Context var0) {
        int var1 = CallConfig.getInstance(var0).getAnswerType();
        LogHelper.e(TAG, "getAnswerMatcher, type==>" + var1);
        switch(var1) {
        case 0:
            return new CannotAnswer();
        case 1:
            return new EnsureHeadsetAnswer();
        case 2:
            return new TryHeadsetAnswer();
        case 3:
            return new KeypadAnswer();
        default:
            int var2 = 0;

            for(; var2 < answerMatchers.length; ++var2) {
                try {
                    BaseAnswer var4 = (BaseAnswer)answerMatchers[var2].newInstance();
                    if(var4.match()) {
                        LogHelper.e(TAG, "getAnswerMatcher, matcher==>" + var4);
                        CallConfig.getInstance(var0).setAnswerType(var2);
                        return var4;
                    }
                } catch (Exception var5) {
                    var5.printStackTrace();
                }
            }

            CallConfig.getInstance(var0).setAnswerType(answerMatchers.length);
            return new KeypadAnswer();
        }
    }
}
