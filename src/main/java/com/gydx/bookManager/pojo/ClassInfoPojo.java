package com.gydx.bookManager.pojo;

public class ClassInfoPojo {

    private Integer page;
    private Integer limit;
    private String name;
    private String majorName;
    private String schoolName;
    private String principalNumber;
    private String principalName;

    public ClassInfoPojo(Integer page, Integer limit, String name, String majorName, String schoolName, String principalNumber) {
        this.page = page;
        this.limit = limit;
        this.name = name;
        this.majorName = majorName;
        this.schoolName = schoolName;
        this.principalNumber = principalNumber;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPrincipalNumber() {
        return principalNumber;
    }

    public void setPrincipalNumber(String principalNumber) {
        this.principalNumber = principalNumber;
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
        return this.majorName + this.name + this.schoolName;
    }
}
