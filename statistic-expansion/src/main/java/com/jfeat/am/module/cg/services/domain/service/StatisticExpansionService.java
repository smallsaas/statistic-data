package com.jfeat.am.module.cg.services.domain.service;


import com.jfeat.am.module.statistics.services.domain.model.StatisticsMetaRecord;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vincent on 2017/10/19.
 */
public interface StatisticExpansionService {
    @Transactional
    Integer createStatisticAndMenu(StatisticsMetaRecord meta);
}