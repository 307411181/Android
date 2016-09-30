package com.unihiltop.xiangliao.bean;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * 联系人信息实体类
 *
 * @author 邱明月
 * @version 1.0
 *
 */
public class ContactInfo implements Parcelable {

    /**
     * 联系人Id
     */

    public int id;

    /**
     * 属于哪个用户
     */
    public String accountId;

    /**
     * 联系人电话号码
     */
    public String phone;
    /**
     * 联系人姓名
     */
    public String name;

    /**
     * 联系人邮箱
     */
    public String email;
    /**
     * 联系人注册状态
     */
    public int registerState;

    /**
     * 用于排序使用
     */
    public String sortKey;

    /**
     *
     */
    public String rowId;

    /**
     *
     *
     */
    public boolean checked;

    public static Creator<ContactInfo> getCREATOR() {
        return CREATOR;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public int getRegisterState() {
        return registerState;
    }

    public void setRegisterState(int registerState) {
        this.registerState = registerState;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public ContactInfo() {
    }

    public ContactInfo(int id, String accountId, String phone, String name,
                       String email, int registerState, String sortKey, String rowId,
                       boolean checked) {
        this.id = id;
        this.accountId = accountId;
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.registerState = registerState;
        this.sortKey = sortKey;
        this.rowId = rowId;
        this.checked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(accountId);
        dest.writeString(phone);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeInt(registerState);
        dest.writeString(sortKey);
        dest.writeString(rowId);
    }

    public static final Creator<ContactInfo> CREATOR = new Creator<ContactInfo>() {

        @Override
        public ContactInfo createFromParcel(Parcel source) {
            ContactInfo linkmanInfo = new ContactInfo();
            linkmanInfo.id = source.readInt();
            linkmanInfo.accountId = source.readString();
            linkmanInfo.phone = source.readString();
            linkmanInfo.name = source.readString();
            linkmanInfo.email = source.readString();
            linkmanInfo.registerState = source.readInt();
            linkmanInfo.sortKey = source.readString();
            linkmanInfo.rowId = source.readString();
            return linkmanInfo;
        }

        @Override
        public ContactInfo[] newArray(int size) {
            return new ContactInfo[size];
        }

    };


}
