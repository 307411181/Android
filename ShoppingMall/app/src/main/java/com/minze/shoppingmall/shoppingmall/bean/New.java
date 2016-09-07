package com.minze.shoppingmall.shoppingmall.bean;

/**
 * @创建者 张京
 * @创建时间 2016/9/7 10:31
 * @描述 ${TODO}
 */
public class New {

    private String title;
    private String content;

    public New(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
