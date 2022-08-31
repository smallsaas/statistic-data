package com.jfeat.am.module.statistics.services.crud.model;

import java.util.List;

public class RangeModel {
    private Integer left;
    private Integer right;

    public RangeModel(Integer left, Integer right){
        this.left = left;
        this.right=right;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

}
