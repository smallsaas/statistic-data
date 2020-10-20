package com.jfeat.am.module.statistics.services.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.statistics.services.persistence.model.StatisticsGroup;

import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Created by Code Generator on 2017-11-25
 */
public interface QueryStatisticsGroupDao extends BaseMapper<StatisticsGroup> {
    List<StatisticsGroup> findStatisticsGroupPage(Page<StatisticsGroup> page, @Param("statisticsgroup") StatisticsGroup statisticsgroup);
}