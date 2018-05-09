package com.jfeat.am.module.statement.services.statistics.service.impl;

import com.jfeat.am.module.statement.services.statistics.util.JDBCConnectionUtil;
import com.jfeat.am.module.statement.services.statistics.service.GeneralStatisticService;
import com.jfeat.am.module.statement.services.statistics.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by vincent on 2018/5/8.
 */
@Service
public class GeneralStatisticServiceImpl implements GeneralStatisticService {

    @Autowired
    DataSource dataSource;

    Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Statistics queryStatistic(String name, String sql) throws SQLException {
        Connection connection = getConnection();

        List<Map<String, String>> result =  JDBCConnectionUtil.querySQL(connection, sql);
        if(result==null || result.size()==0){
            return null;
        }
        if(result.size()!=1){
            throw new RuntimeException("error: Bad SQL request (only one row allowed) : " + sql);
        }

        /// set statistic data
        Statistic stat = new Statistic();

        Map<String,String> it = result.get(0);
        Map.Entry<String,String> entry = it.entrySet().iterator().next();

        /// set name
        if(name==null || name.length()==0) {
            stat.setName(entry.getKey());
        }else {
            stat.setName(name);
        }
        stat.setValue(entry.getValue());

        return stat;
    }

    @Override
    public StatisticRate queryStatisticRate(String name, String sql)  throws SQLException {
        Connection connection = getConnection();
        List<Map<String, String>> result =  JDBCConnectionUtil.querySQL(connection, sql);
        if(result==null || result.size()==0){
            return null;
        }

        /// only one row allowed
        if(result.size()!=1){
            throw new RuntimeException("error: Bad SQL request(only one row allowed)" + sql);
        }

        /// set statistic data
        StatisticRate rates = new StatisticRate();
        // set name
        rates.setName(name);

        Iterator<Map<String,String>> it = result.iterator();
        while (it.hasNext()){
            Map<String,String> item = it.next();
            Map.Entry<String,String> entry = item.entrySet().iterator().next();
            rates.addRate(entry.getKey(), entry.getValue());
        }

        return rates;
    }

    @Override
    public StatisticTuple queryStatisticTuple(String name, String sql, List<String> tuples) throws SQLException {
        Connection connection = getConnection();
        List<Map<String, String>> result =  JDBCConnectionUtil.querySQL(connection, sql);
        if(result==null || result.size()==0){
            return null;
        }

        ///
        if(result.size()!=tuples.size()){
            throw new RuntimeException("fatal: [Bad Request] parameter tuples size is out of range");
        }

        /// set statistic data
        /// 报表数据转换为 列数据, 如按时间段查询
        StatisticTuple statisticTuple = new StatisticTuple();
        statisticTuple.setName(name);

        // 列表为各占比的名称
        // create column name hash table

        // 历遍报表结构查询结果，转换并创建各占比数据
        int curr = 0;
        Iterator<Map<String,String>> it = result.iterator();
        while (it.hasNext()){
            Map<String,String> data = it.next();

            String tupleName = tuples.get(curr);
            StatisticRate rate = convertMapToStatisticRate(data);
            rate.setName(tupleName);

            statisticTuple.addRate(rate);

            curr++;
        }

        return statisticTuple;
    }

    @Override
    public StatisticTimeline queryStatisticTimeline(String name, String sql, Timeline timeline) throws SQLException  {
        Connection connection = getConnection();
        List<Map<String, String>> result =  JDBCConnectionUtil.querySQL(connection, sql);
        if(result==null || result.size()==0){
            return null;
        }

        /// set statistic data
        /// 报表数据转换为 列数据, 如按时间段查询
        StatisticTimeline timelines = new StatisticTimeline();
        timelines.setName(name);

        // 列表为各占比的名称
        // create column name hash table

        // 历遍报表结构查询结果，转换并创建各占比数据
        Iterator<Map<String,String>> it = result.iterator();
        while (it.hasNext()){
            Map<String,String> item = it.next();
            Map.Entry<String,String> entry = item.entrySet().iterator().next();
        }

        return timelines;
    }


    /**
     * 转换sql查询结果为 占比数据结构
     * @param map
     * @return
     */
    private StatisticRate convertMapToStatisticRate(Map<String,String> map){
        if(map==null || map.size()==0){
            return null;
        }

        StatisticRate rate = new StatisticRate();

        Iterator<Map.Entry<String,String>> it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,String> entry = it.next();
            rate.addRate(new Statistic(entry.getKey(), entry.getValue()));
        }

        return rate;
    }
}