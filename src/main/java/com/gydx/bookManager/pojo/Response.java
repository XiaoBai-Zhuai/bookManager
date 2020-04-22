package com.gydx.bookManager.pojo;

import java.util.List;

public class Response<T> {
    private int code;
    private String msg;
    private List<T> list1;
    private List<T> list2;
    private Object object;

    public Response(int i, String msg) {
        this.code = i;
        this.msg = msg;
    }

    public Response(int i, String msg, Object o) {
        this.code = i;
        this.msg = msg;
        this.object = o;
    }

    public Response(int code, String msg, List<T> list1, Object o) {
        this.code = code;
        this.msg = msg;
        this.list1 = list1;
        this.object = o;
    }

    public Response(int code, String msg, List<T> list1, List<T> list2, Object o) {
        this.code = code;
        this.msg = msg;
        this.list1 = list1;
        this.list2 = list2;
        this.object = o;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getList1() {
        return list1;
    }

    public void setList1(List<T> list1) {
        this.list1 = list1;
    }

    public List<T> getList2() {
        return list2;
    }

    public void setList2(List<T> list2) {
        this.list2 = list2;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
