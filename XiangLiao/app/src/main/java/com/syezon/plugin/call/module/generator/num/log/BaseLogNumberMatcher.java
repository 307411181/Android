//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.module.generator.num.log;

import android.text.TextUtils;

public abstract class BaseLogNumberMatcher {
    private static final String TAG = BaseLogNumberMatcher.class.getName();
    protected String[] matchedModels = null;

    public BaseLogNumberMatcher() {
    }

    public abstract String[] getModels();

    public abstract String getNumber();

    public boolean match(String var1) {
        this.matchedModels = this.getModels();
        if(!TextUtils.isEmpty(var1)) {
            for(int var2 = 0; var2 < this.matchedModels.length; ++var2) {
                if(var1.toLowerCase().contains(this.matchedModels[var2].toLowerCase())) {
                    return true;
                }
            }
        }

        return false;
    }
}
