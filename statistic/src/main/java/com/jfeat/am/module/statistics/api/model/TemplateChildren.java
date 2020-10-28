package com.jfeat.am.module.statistics.api.model;

public class TemplateChildren {

    public TemplateChildren(String presenter,String field,Integer seq){
        this.presenter = presenter;
        this.field = field;
        this.seq = seq;

    }

    private Integer seq;
    //前端组件名
    private String presenter;
    //域
    private String field;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
