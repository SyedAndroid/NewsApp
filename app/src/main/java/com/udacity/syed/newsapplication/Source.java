package com.udacity.syed.newsapplication;

/**
 * Created by shoiab on 2017-09-12.
 */

public class Source {

    private String name;
    private String id;
    private String desc;
    private String url;
    private String category;
    private int status;

    public Source(String name, String id, String desc, String url, String category) {
        this.name = name;
        this.id = id;
        this.desc = desc;
        this.url = url;
        this.category = category;
    }

    public Source(String name, String id, int status) {
        this.name = name;
        this.id = id;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getUrl() {
        return url;
    }

    public String getCategory() {
        return category;
    }
}
