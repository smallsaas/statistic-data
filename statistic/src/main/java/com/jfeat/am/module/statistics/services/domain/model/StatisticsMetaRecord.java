package com.jfeat.am.module.statistics.services.domain.model;

import com.jfeat.am.module.statistics.services.gen.persistence.model.StatisticsMeta;

/**
 * Created by Code Generator on 2020-04-17
 */
public class StatisticsMetaRecord extends StatisticsMeta{
    private String chinceseType;

    public String getChinceseType() {
        return chinceseType;
    }

    public void setChinceseType(String chinceseType) {
        this.chinceseType = chinceseType;
    }
}
