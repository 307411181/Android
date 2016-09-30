//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.module.generator.call.endcall;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

public abstract class BaseEndCall {
    private static final String TAG = BaseEndCall.class.getName();
    String DevBrand;
    String DevModel;
    protected String[] matchedModels;

    public BaseEndCall() {
        this.DevBrand = Build.BRAND;
        this.DevModel = Build.MODEL;
    }

    public abstract void endCall(Context var1);

    public abstract String[] getModels();

    public boolean match() {
        this.matchedModels = this.getModels();

        for(int var1 = 0; var1 < this.matchedModels.length; ++var1) {
            if(!TextUtils.isEmpty(this.DevBrand) && this.DevBrand.toLowerCase().contains(this.matchedModels[var1].toLowerCase()) || !TextUtils.isEmpty(this.DevModel) && this.DevModel.toLowerCase().contains(this.matchedModels[var1].toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}
