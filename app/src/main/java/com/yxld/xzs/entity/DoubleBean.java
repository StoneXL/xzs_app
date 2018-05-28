package com.yxld.xzs.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 2017/11/15.
 */

public class DoubleBean {
    private String name;
    private List<String> list;
    private boolean isShow;

    public DoubleBean(String name, List<String> list) {
        this.name = name;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
