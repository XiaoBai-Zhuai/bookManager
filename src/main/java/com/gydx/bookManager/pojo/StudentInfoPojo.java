package com.gydx.bookManager.pojo;

public class StudentInfoPojo {

    private Integer page;
    private Integer limit;
    private String name;
    private String number;
    private String className;
    private String majorName;
    private String schoolName;

    public StudentInfoPojo(Integer page, Integer limit, String name, String number, String className, String majorName, String schoolName) {
        this.page = page;
        this.limit = limit;
        this.name = name;
        this.number = number;
        this.className = className;
        this.majorName = majorName;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getCondition() {
        return this.name + this.number + this.className + this.majorName + this.schoolName;
    }
}
