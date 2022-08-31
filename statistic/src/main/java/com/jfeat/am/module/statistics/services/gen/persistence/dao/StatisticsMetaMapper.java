package com.jfeat.am.module.statistics.services.gen.persistence.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.am.module.statistics.services.domain.model.StatisticsMetaRecord;
import com.jfeat.am.module.statistics.services.gen.persistence.model.StatisticsMeta;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Code Generator
 * @since 2020-04-17
 */
public interface StatisticsMetaMapper extends BaseMapper<StatisticsMeta> {
    StatisticsMetaRecord getMetaByField(String field, String appid);
}