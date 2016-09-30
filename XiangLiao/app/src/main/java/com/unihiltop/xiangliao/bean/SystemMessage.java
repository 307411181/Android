package com.unihiltop.xiangliao.bean;


/**
 * 系统消息
 */

public class SystemMessage {
    public long id;
    /**
     * 内容
     */
    public String content;
    /**
     * 发布时间
     */
    public long publishTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
}
