//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.module.generator.call.answer;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.syezon.plugin.call.module.generator.num.log.BaseLogNumberMatcher;

public abstract class BaseAnswer {
    private static final String TAG = BaseLogNumberMatcher.class.getName();
    private String brand;
    protected String[] matchedModels = null;
    private String model;

    public BaseAnswer() {
        this.brand = Build.BRAND;
        this.model = Build.MODEL;
    }

    public abstract void answer(Context var1);

    public abstract String[] getModels();

    public boolean match() {
        this.matchedModels = this.getModels();

        for(int var1 = 0; var1 < this.matchedModels.length; ++var1) {
            if(!TextUtils.isEmpty(this.matchedModels[var1]) && (!TextUtils.isEmpty(this.brand) && this.brand.toLowerCase().contains(this.matchedModels[var1].toLowerCase()) || !TextUtils.isEmpty(this.model) && this.model.toLowerCase().contains(this.matchedModels[var1].toLowerCase()))) {
                return true;
            }
        }

        return false;
    }
}
