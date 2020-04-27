package com.gydx.bookManager.pojo;

public class SchoolInfoPojo {

    private Integer page;
    private Integer limit;
    private String name;
    private String principalName;
    private String principalNumber;

    public SchoolInfoPojo(Integer page, Integer limit, String name, String principalName, String principalNumber) {
        this.page = page;
        this.limit = limit;
        this.name = name;
        this.principalName = principalName;
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

    public String getCondition() {
        return this.name + this.principalName + this.principalNumber;
    }
}
