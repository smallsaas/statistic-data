package com.jfeat.am.module.statistics.services.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.am.module.statistics.services.persistence.model.StatisticsField;
import com.jfeat.am.module.statistics.services.persistence.model.StatisticsRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Code Generator on 2017-11-25
 */
public interface QueryStatisticsRecordDao extends BaseMapper<StatisticsRecord> {
    List<StatisticsRecord> querySql(String sql);

    List<StatisticsRecord> items(@Param("field")String field,
                                 @Param("identifier")String identifier);

}