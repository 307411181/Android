package com.unihiltop.xiangliao.bean;

/**
 * Created by Administrator on 2015/1/13 0013.
 */


import java.io.Serializable;

/**
 * 意见反馈
 */

public class Feedback implements Serializable{
    private String id;
    /**
     * 内容
     */
    private String content;
    /**
     * 反馈者的账号
     */
    private Account account;
    /**
     * 反馈意见的Tag
     */
    private String tag;
    /**
     * 反馈的日期
     */
    private long date;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj instanceof Feedback){
            Feedback feedback = (Feedback) obj;
            if(feedback.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    /**
     * 验证数据完整性
     * @return
     */

    public boolean isDataFull(){
        return !(content == null || content.trim().equals(""));
    }
}
