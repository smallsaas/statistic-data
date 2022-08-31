package com.jfeat.am.module.statistics.services.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.statistics.services.domain.model.StatisticsMetaRecord;

import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.statistics.services.gen.persistence.model.StatisticsMeta;

import java.util.Date;
import java.util.List;

/**
 * Created by Code Generator on 2020-04-17
 */
public interface QueryStatisticsMetaDao extends BaseMapper<StatisticsMeta> {
    List<StatisticsMetaRecord> findStatisticsMetaPage(Page<StatisticsMetaRecord> page, @Param("record") StatisticsMetaRecord record,
                                                      @Param("search") String search, @Param("orderBy") String orderBy,
                                                      @Param("startTime") Date startTime, @Param("endTime") Date endTime,@Param("type") Integer type);

    StatisticsMetaRecord selectOne(Long id);
}