package com.jfeat.am.module.statistics.services.domain.model;

import com.jfeat.am.module.statistics.services.gen.persistence.model.StatisticsMeta;

/**
 * Created by Code Generator on 2020-04-17
 */
public class StatisticsMetaRecord extends StatisticsMeta{

    private String menuName;

    private String groupMenuName;

    private Long groupMenuId;


    private String chinceseType;

    public Long getGroupMenuId() {
        return groupMenuId;
    }

    public void setGroupMenuId(Long groupMenuId) {
        this.groupMenuId = groupMenuId;
    }

    public String getGroupMenuName() {
        return groupMenuName;
    }

    public void setGroupMenuName(String groupMenuName) {
        this.groupMenuName = groupMenuName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getChinceseType() {
        return chinceseType;
    }

    public void setChinceseType(String chinceseType) {
        this.chinceseType = chinceseType;
    }
}
