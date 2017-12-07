package com.jfeat.am.module.statistics.services.service;

import com.jfeat.am.common.crud.CRUDServiceOverModelOne;
import com.jfeat.am.module.statistics.services.domain.model.StatisticsFieldModel;
import com.jfeat.am.module.statistics.services.persistence.model.StatisticsField;
import com.jfeat.am.module.statistics.services.persistence.model.StatisticsRecord;

import java.util.List;


/**
 * <p>
 *  service interface
 * </p>
 *
 * @author Code Generator
 * @since 2017-11-25
 */

public interface StatisticsFieldService  extends CRUDServiceOverModelOne<StatisticsField, StatisticsFieldModel, StatisticsRecord> {
    List<StatisticsField> getFieldListByChart(String chart);
    StatisticsField getFieldOfField(String field);
    List<StatisticsField> getFieldGroupByGroup(Long groupId);
}