//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.common.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public abstract class BaseConfig {
    protected Context context = null;
    protected String fileName = null;
    protected SharedPreferences preferences = null;

    protected BaseConfig(Context var1, String var2) {
        this.context = var1;
        this.fileName = var2;
        this.preferences = var1.getSharedPreferences(var2, 0);
    }

    public void clearConfig() {
        this.preferences.edit().clear().commit();
    }

    public void clearValue(String var1) {
        this.preferences.edit().remove(var1).commit();
    }

    public boolean getBoolean(String var1, boolean var2) {
        return this.preferences.getBoolean(var1, var2);
    }

    public float getFloat(String var1, float var2) {
        return this.preferences.getFloat(var1, var2);
    }

    public int getInt(String var1, int var2) {
        return this.preferences.getInt(var1, var2);
    }

    public long getLong(String var1, long var2) {
        return this.preferences.getLong(var1, var2);
    }

    public String getString(String var1, String var2) {
        return this.preferences.getString(var1, var2);
    }

    public boolean setValue(String var1, Object var2) {
        Editor var3 = this.preferences.edit();
        return var2 instanceof String?var3.putString(var1, (String)var2).commit():(var2 instanceof Integer?var3.putInt(var1, ((Integer)var2).intValue()).commit():(var2 instanceof Boolean?var3.putBoolean(var1, ((Boolean)var2).booleanValue()).commit():(var2 instanceof Float?var3.putFloat(var1, ((Float)var2).floatValue()).commit():(var2 instanceof Long?var3.putLong(var1, ((Long)var2).longValue()).commit():false))));
    }
}
