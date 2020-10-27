package com.jfeat.am.module.statistics.services.domain.dao;

import com.jfeat.am.module.statistics.services.domain.model.StatisticsMetaGroupRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.statistics.services.gen.persistence.model.StatisticsMetaGroup;
import com.jfeat.am.module.statistics.services.gen.crud.model.StatisticsMetaGroupModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2020-10-27
 */
public interface QueryStatisticsMetaGroupDao extends QueryMasterDao<StatisticsMetaGroup> {
   /*
    * Query entity list by page
    */
    List<StatisticsMetaGroupRecord> findStatisticsMetaGroupPage(Page<StatisticsMetaGroupRecord> page, @Param("record") StatisticsMetaGroupRecord record,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    StatisticsMetaGroupModel queryMasterModel(@Param("id") Long id);
}