package com.jfeat.am.module.statistics.services.domain.dao;

import com.jfeat.am.module.statistics.services.domain.model.StatisticsUsedHistoryRecord;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.crud.plus.QueryMasterDao;
import org.apache.ibatis.annotations.Param;
import com.jfeat.am.module.statistics.services.gen.persistence.model.StatisticsUsedHistory;
import com.jfeat.am.module.statistics.services.gen.crud.model.StatisticsUsedHistoryModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Code generator on 2022-08-31
 */
public interface QueryStatisticsUsedHistoryDao extends QueryMasterDao<StatisticsUsedHistory> {
   /*
    * Query entity list by page
    */
    List<StatisticsUsedHistoryRecord> findStatisticsUsedHistoryPage(Page<StatisticsUsedHistoryRecord> page, @Param("record") StatisticsUsedHistoryRecord record,
                                            @Param("search") String search, @Param("orderBy") String orderBy,
                                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /*
     * Query entity model for details
     */
    StatisticsUsedHistoryModel queryMasterModel(@Param("id") Long id);
}