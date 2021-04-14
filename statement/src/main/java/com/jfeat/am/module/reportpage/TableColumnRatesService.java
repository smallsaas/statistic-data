package com.jfeat.am.module.reportpage;


import java.util.Map;

/**
 * Created by Silent-Y on 2017/11/6.
 */
@Deprecated
public interface TableColumnRatesService {
    Map<String,Integer> getColumnRates(String table,String column, String timeName,String startTime,String endTime);
    Map<String,Integer> getColumnValueTotal(String table, String column, String columnValue, String timeName, String startTime, String endTime);
}
