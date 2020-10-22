package com.jfeat.am.module.statistics.api.model;

public class MetaTag {
    //搜索必须先开启type
    private boolean enableSearch;
    private boolean enableTips;
    private boolean enableType;
    private boolean enablePages;
    private boolean enableHead;

    public static final boolean flag = false;

   public MetaTag(){
        this.enableSearch=flag;
        this.enableTips=flag;
        this.enableType=flag;
        this.enablePages=flag;
        this.enableHead=flag;
    }

    public boolean isEnableSearch() {
        return enableSearch;
    }

    public void setEnableSearch(boolean enableSearch) {
        this.enableSearch = enableSearch;
    }

    public boolean isEnableTips() {
        return enableTips;
    }

    public void setEnableTips(boolean enableTips) {
        this.enableTips = enableTips;
    }

    public boolean isEnableType() {
        return enableType;
    }

    public void setEnableType(boolean enableType) {
        this.enableType = enableType;
    }

    public boolean isEnablePages() {
        return enablePages;
    }

    public void setEnablePages(boolean enablePages) {
        this.enablePages = enablePages;
    }

    public boolean isEnableHead() {
        return enableHead;
    }

    public void setEnableHead(boolean enableHead) {
        this.enableHead = enableHead;
    }
}
