//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.syezon.plugin.call.common.config;

import android.content.Context;
import com.syezon.plugin.call.common.config.BaseConfig;

public class CallConfig extends BaseConfig {
    private static CallConfig sConfig = null;
    private final String ANSWER_TYPE = "isAnswerType";
    private final String BATCH_DOWNLOAD_TIME = "batchDownloadTime";
    private final String CHECK_PRIORITY_TIME = "checkPriorityTime";
    private final String ENDCALL_TYPE = "isEndCallType";
    private final String FIRST_RUN = "csFirstRun";
    private final String IN_CALL_SWITCH = "ComeCallSwitch";
    private final String IN_OUT_CALL = "InOrOutCall";
    private final String IN_PHONENUM = "InPhoneNumber";
    private final String LAZY_ID = "lazy_id";
    private final String LAZY_MAXID = "lazy_maxid";
    private final String LAZY_MINID = "lazy_minid";
    private final String LOCATION_AREA_CODE = "areaCode";
    private final String LOCATION_CITY = "city";
    private final String LOCATION_LATITUDE = "latitude";
    private final String LOCATION_LONGITUDE = "longitude";
    private final String LOCATION_STORT_TIME = "locationStortTime";
    private final String NO_DND = "NoDnd";
    private final String OUT_CALL_SWITCH = "OutCallSwitch";
    private final String OUT_PHONENUM = "OutPhoneNumber";
    private final String PHONENUM_FROM_LOG_TAG = "PhoneFormLogTag";
    private final String PHONE_TYPE = "PhoneType";
    private final String PHOTO_ENABLE = "is_photo_enable";
    private final String RINGING_MUTE = "IsRingingMute";
    private final String SET_CALL_STYLE = "setCallStyle";
    private final String SHOW_PRIORITY = "showPriority";
    private final String STYLE_NAME = "styleName";
    private final String THEME_CYCLE = "theme_cycle";
    private final String THEME_RING = "theme_ring";
    private final String THEME_SET_COUNT = "theme_set_count";
    private final String THEME_SET_INDEX = "theme_set_index";
    private final String USER_MID = "user_mid";

    protected CallConfig(Context var1, String var2) {
        super(var1, var2);
    }

    public static CallConfig getInstance(Context var0) {
        synchronized(CallConfig.class){}

        CallConfig var2;
        try {
            if(sConfig == null) {
                sConfig = new CallConfig(var0, "call_config");
            }

            var2 = sConfig;
        } finally {
            ;
        }

        return var2;
    }

    public void deleteAnswerType() {
        this.clearValue("isAnswerType");
    }

    public void deleteAreaCode() {
        this.clearValue("areaCode");
    }

    public void deleteBatchDownloadTime() {
        this.clearValue("batchDownloadTime");
    }

    public void deleteCallComeSwitch() {
        this.clearValue("ComeCallSwitch");
    }

    public void deleteCallGoSwitch() {
        this.clearValue("OutCallSwitch");
    }

    public void deleteCallStyle() {
        this.clearValue("setCallStyle");
    }

    public void deleteCallTag() {
        this.clearValue("InOrOutCall");
    }

    public void deleteCity() {
        this.clearValue("city");
    }

    public void deleteEndCallType() {
        this.clearValue("isEndCallType");
    }

    public void deleteFirstRun() {
        this.clearValue("csFirstRun");
    }

    public void deleteFormLogTag() {
        this.clearValue("PhoneFormLogTag");
    }

    public void deleteInPhoneNum() {
        this.clearValue("InPhoneNumber");
    }

    public void deleteIsRingingMute() {
        this.clearValue("IsRingingMute");
    }

    public void deleteLatitude() {
        this.clearValue("latitude");
    }

    public void deleteLazyMaxId() {
        this.clearValue("lazy_maxid");
    }

    public void deleteLazyMinId() {
        this.clearValue("lazy_minid");
    }

    public void deleteLazyid() {
        this.clearValue("lazy_id");
    }

    public void deleteLongitude() {
        this.clearValue("longitude");
    }

    public void deleteNoDnd() {
        this.clearValue("NoDnd");
    }

    public void deleteOutPhoneNum() {
        this.clearValue("OutPhoneNumber");
    }

    public void deletePhoneEnable() {
        this.clearValue("is_photo_enable");
    }

    public void deletePhoneType() {
        this.clearValue("PhoneType");
    }

    public void deletePriorityTime() {
        this.clearValue("checkPriorityTime");
    }

    public void deleteShowPriority() {
        this.clearValue("showPriority");
    }

    public void deleteStortTime() {
        this.clearValue("locationStortTime");
    }

    public void deleteThemeCycle() {
        this.clearValue("theme_cycle");
    }

    public void deleteThemeRing() {
        this.clearValue("theme_ring");
    }

    public void deleteThemeSetCount() {
        this.clearValue("theme_set_count");
    }

    public void deleteThemeSetIndex() {
        this.clearValue("theme_set_index");
    }

    public void deleteUserMid() {
        this.clearValue("user_mid");
    }

    public int getAnswerType() {
        return this.getInt("isAnswerType", -1);
    }

    public String getAreaCode() {
        return this.getString("areaCode", (String)null);
    }

    public long getBatchDownloadTime() {
        return this.getLong("batchDownloadTime", 0L);
    }

    public int getCallComeSwitch(int var1) {
        return this.getInt("ComeCallSwitch", var1);
    }

    public int getCallGoSwitch(int var1) {
        return this.getInt("OutCallSwitch", var1);
    }

    public boolean getCallStyle() {
        return this.getBoolean("setCallStyle", false);
    }

    public int getCallTag(int var1) {
        return this.getInt("InOrOutCall", var1);
    }

    public String getCity() {
        return this.getString("city", (String)null);
    }

    public int getEndCallType() {
        return this.getInt("isEndCallType", -1);
    }

    public boolean getFirstRun(boolean var1) {
        return this.getBoolean("csFirstRun", var1);
    }

    public int getFormLogTag() {
        return this.getInt("PhoneFormLogTag", -1);
    }

    public String getInPhoneNum(String var1) {
        return this.getString("InPhoneNumber", var1);
    }

    public boolean getIsRingingMute(boolean var1) {
        return this.getBoolean("IsRingingMute", var1);
    }

    public double getLatitude() {
        return Double.valueOf(this.getString("latitude", "0")).doubleValue();
    }

    public long getLazyId() {
        return this.getLong("lazy_id", -1L);
    }

    public long getLazyMaxId() {
        return this.getLong("lazy_maxid", -1L);
    }

    public long getLazyMinId() {
        return this.getLong("lazy_minid", -1L);
    }

    public double getLongitude() {
        return Double.valueOf(this.getString("longitude", "0")).doubleValue();
    }

    public boolean getNoDnd() {
        return this.getBoolean("NoDnd", false);
    }

    public String getOutPhoneNum(String var1) {
        return this.getString("OutPhoneNumber", var1);
    }

    public boolean getPhoneEnable() {
        return this.getBoolean("is_photo_enable", true);
    }

    public int getPhoneType(int var1) {
        return this.getInt("PhoneType", var1);
    }

    public long getPriorityTime() {
        return this.getLong("checkPriorityTime", 0L);
    }

    public float getShowPriority() {
        return this.getFloat("showPriority", 1.5F);
    }

    public long getStortTime() {
        return this.getLong("locationStortTime", 0L);
    }

    public boolean getThemeCycle() {
        return this.getBoolean("theme_cycle", false);
    }

    public boolean getThemeRing() {
        return this.getBoolean("theme_ring", true);
    }

    public int getThemeSetCount() {
        return this.getInt("theme_set_count", 0);
    }

    public int getThemeSetIndex() {
        return this.getInt("theme_set_index", 2);
    }

    public String getUserMid() {
        return this.getString("user_mid", (String)null);
    }

    public void setAnswerType(int var1) {
        this.setValue("isAnswerType", Integer.valueOf(var1));
    }

    public void setAreaCode(String var1) {
        this.setValue("areaCode", var1);
    }

    public void setBatchDownloadTime(long var1) {
        this.setValue("batchDownloadTime", Long.valueOf(var1));
    }

    public void setCallComeSwitch(int var1) {
        this.setValue("ComeCallSwitch", Integer.valueOf(var1));
    }

    public void setCallGoSwitch(int var1) {
        this.setValue("OutCallSwitch", Integer.valueOf(var1));
    }

    public void setCallStyle(boolean var1) {
        this.setValue("setCallStyle", Boolean.valueOf(var1));
    }

    public void setCallTag(int var1) {
        this.setValue("InOrOutCall", Integer.valueOf(var1));
    }

    public void setCity(String var1) {
        this.setValue("city", var1);
    }

    public void setEndCallType(int var1) {
        this.setValue("isEndCallType", Integer.valueOf(var1));
    }

    public void setFirstRun(boolean var1) {
        this.setValue("csFirstRun", Boolean.valueOf(var1));
    }

    public void setFormLogTag(int var1) {
        this.setValue("PhoneFormLogTag", Integer.valueOf(var1));
    }

    public void setInPhoneNum(String var1) {
        this.setValue("InPhoneNumber", var1);
    }

    public void setIsRingingMute(boolean var1) {
        this.setValue("IsRingingMute", Boolean.valueOf(var1));
    }

    public void setLatitude(double var1) {
        this.setValue("latitude", String.valueOf(var1));
    }

    public void setLazyMaxId(long var1) {
        this.setValue("lazy_maxid", Long.valueOf(var1));
    }

    public void setLazyMinId(long var1) {
        this.setValue("lazy_minid", Long.valueOf(var1));
    }

    public void setLazyid(long var1) {
        this.setValue("lazy_id", Long.valueOf(var1));
    }

    public void setLongitude(double var1) {
        this.setValue("longitude", String.valueOf(var1));
    }

    public void setNoDnd(boolean var1) {
        this.setValue("NoDnd", Boolean.valueOf(var1));
    }

    public void setOutPhoneNum(String var1) {
        this.setValue("OutPhoneNumber", var1);
    }

    public void setPhoneEnable(boolean var1) {
        this.setValue("is_photo_enable", Boolean.valueOf(var1));
    }

    public void setPhoneType(int var1) {
        this.setValue("PhoneType", Integer.valueOf(var1));
    }

    public void setPriorityTime(long var1) {
        this.setValue("checkPriorityTime", Long.valueOf(var1));
    }

    public void setShowPriority(float var1) {
        this.setValue("showPriority", Float.valueOf(var1));
    }

    public void setStortTime(long var1) {
        this.setValue("locationStortTime", Long.valueOf(var1));
    }

    public void setThemeCycle(boolean var1) {
        this.setValue("theme_cycle", Boolean.valueOf(var1));
    }

    public void setThemeRing(boolean var1) {
        this.setValue("theme_ring", Boolean.valueOf(var1));
    }

    public void setThemeSetCount(int var1) {
        this.setValue("theme_set_count", Integer.valueOf(var1));
    }

    public void setThemeSetIndex(int var1) {
        this.setValue("theme_set_index", Integer.valueOf(var1));
    }

    public void setUserMid(String var1) {
        this.setValue("user_mid", var1);
    }
}
