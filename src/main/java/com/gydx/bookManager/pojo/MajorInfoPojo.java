package com.gydx.bookManager.pojo;

public class MajorInfoPojo {
    private Integer page;
    private Integer limit;
    private String name;
    private String schoolName;

    public MajorInfoPojo(Integer page, Integer limit, String name, String schoolName) {
        this.page = page;
        this.limit = limit;
        this.name = name;
        this.schoolName = schoolName;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getCondition() {
        return this.name + this.schoolName;
    }
}
