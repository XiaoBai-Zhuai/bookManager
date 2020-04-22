package com.gydx.bookManager.entity;

import javax.persistence.*;
import java.io.Serializable;

public class Class implements Serializable {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String majorId;
    @Column
    private String principalName;
    @Column
    private String principalTel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPrincipalTel() {
        return principalTel;
    }

    public void setPrincipalTel(String principalTel) {
        this.principalTel = principalTel;
    }
}
