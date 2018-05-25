package com.jfeat.am.module.statement.services.statistics;

import java.util.Calendar;

import static com.jfeat.am.module.statement.services.statistics.Timeline.Timelines.*;

/**
 * Created by vincenthuang on 08/05/2018.
 */
public class Timeline {

    /**
     * timeline options
     */
    public enum Timelines {
        T,       /// till now
        D,       /// current day
        W,       /// current week
        M,       /// current month
        Y,       /// current year
        LD3,     /// Latest 3 days
        LW1,     /// Latest week,
        LM1,     /// Latest month
        LM3,     /// Latest 3 months
        Q1,      //今天第一季度
        Q2,
        Q3,
        Q4,
    }

    /**
     * 关于时间名称，用于记录时间
     */
    private String name;

    /**
     * 关于时间的表字段
     */
    private String timestampField;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimestampField() {
        return timestampField;
    }

    public void setTimestampField(String timestampField) {
        this.timestampField = timestampField;
    }

    public Timeline() {
    }

    public Timeline(String name, String timestamp) {
        this.name = name;
        this.timestampField = timestamp;

        if (T.toString().equals(name)) {
            // ok
        } else if (D.toString().equals(name)) {
            // ok
        } else if (W.toString().equals(name)) {
            // ok
        } else if (M.toString().equals(name)) {
            // ok
        } else if (Y.toString().equals(name)) {
            // ok
        } else if (LW1.toString().equals(name)) {
            // ok
        } else if (LM1.toString().equals(name)) {
            // ok
        } else if (LM3.toString().equals(name)) {
            // ok
        } else if (Q1.toString().equals(name)) {
            // ok
        } else if (Q2.toString().equals(name)) {
            // ok
        } else if (Q3.toString().equals(name)) {
            // ok
        } else if (Q4.toString().equals(name)) {
            // ok
        }else {
            throw new RuntimeException("fatal: BAD_REQUEST: 无效的时间段名称:" + name);
        }
    }

    public String toSql(String startTime, String endTime) {
        return String.format("%s between %s and %s", timestampField, startTime, endTime);
    }

    public String toSql() {
        final String mysqlDbType = "mysql";

        String sql = null;

        Calendar calendar = Calendar.getInstance();
        if (D.toString().compareTo(name) == 0) {
            /// today
            sql = buildTodayQuery(mysqlDbType, timestampField);
        } else if(W.toString().compareTo(name)==0){
            // current week
            sql = buildLatestQuery(mysqlDbType, timestampField, calendar.get(Calendar.DAY_OF_WEEK) + " DAY");
        } else if(M.toString().compareTo(name)==0){
            // current week
            sql = buildLatestQuery(mysqlDbType, timestampField, calendar.get(Calendar.DAY_OF_MONTH) + " DAY");
        } else if(Y.toString().compareTo(name)==0){
            // current year
            sql = buildLatestQuery(mysqlDbType, timestampField, calendar.get(Calendar.DAY_OF_YEAR) + " DAY");
        } else if (LD3.toString().compareTo(name) == 0) {
            /// latest 3 days
            sql = buildLatestQuery(mysqlDbType, timestampField, "3 DAY");
        }else if (LW1.toString().compareTo(name) == 0) {
            /// latest week
            sql = buildLatestQuery(mysqlDbType, timestampField, "7 DAY");
        } else if (LM1.toString().compareTo(name) == 0) {
            /// latest month
            sql = buildLatestQuery(mysqlDbType, timestampField, "1 MONTH");
        } else if (LM3.toString().compareTo(name) == 0) {
            /// latest 3 month
            sql = buildLatestQuery(mysqlDbType, timestampField, "3 MONTH");
        } else if (Q1.toString().compareTo(name) == 0) {
            sql = buildQuarterQuery(mysqlDbType, timestampField, 1);
        } else if (Q2.toString().compareTo(name) == 0) {
            sql = buildQuarterQuery(mysqlDbType, timestampField, 2);
        } else if (Q3.toString().compareTo(name) == 0) {
            sql = buildQuarterQuery(mysqlDbType, timestampField, 3);
        } else if (Q4.toString().compareTo(name) == 0) {
            sql = buildQuarterQuery(mysqlDbType, timestampField, 4);
        } else {
            throw new RuntimeException("fatal: invalid timeline name: " + name);
        }

        return sql;
    }


    /**
     * build latest query with date_sub interval
     *
     * @param dbType
     * @param column
     * @param interval
     * @return
     */
    private String buildLatestQuery(String dbType, String column, String interval) {
        if ("mysql".equals(dbType)) {
            return String.format("%s <= now() and %s > DATE_SUB(date(now()),INTERVAL %s)", column, column, interval);
        } else if ("sqlserver".equals(dbType)) {
            return null;
        } else if ("oracle".equals(dbType)) {
            return null;
        } else if ("Access".equals(dbType)) {
            return null;
        }

        throw new RuntimeException("fatal: invalid dbType = " + dbType);
    }


    /**
     * build quarter and yearly query
     *
     * @param dbType
     * @param column
     * @param which  1-Q1, 2-Q2, 3-Q3, 4-Q4
     * @return
     */
    private String buildQuarterQuery(String dbType, String column, int which) { //季度

        int year = Calendar.getInstance().get(Calendar.YEAR);

        String start = "", end = "";
        switch (which) {
            case 0:
                start = String.format("'%s-01-01'", year);
                end = "now()";
                break;
            case 1:
                start = String.format("'%s-01-01'", year);
                end = String.format("'%s-04-01'", year);
                break;
            case 2:
                start = String.format("'%s-04-01'", year);
                end = String.format("'%s-07-01'", year);
                break;
            case 3:
                start = String.format("'%s-07-01'", year);
                end = String.format("'%s-10-01'", year);
                break;
            case 4:
                start = String.format("'%s-10-01'", year);
                end = String.format("'%s-01-01'", year + 1);
                break;
            default:
                throw new RuntimeException("这是一个内部异常，出现在 Timeline.buildQQuery() 中的Q 参数");
        }


        if ("mysql".equals(dbType)) {
            return String.format("%s <= date(%s) and %s > date('%s')", column, end, column, start);

        } else if ("sqlserver".equals(dbType)) {
            return null;
        } else if ("oracle".equals(dbType)) {
            return null;
        } else if ("Access".equals(dbType)) {
            return null;
        }

        throw new RuntimeException("fatal: invalid dbType = " + dbType);
    }


    /**
     * build today query sql
     *
     * @param dbType
     * @param column
     * @return
     */
    private String buildTodayQuery(String dbType, String column) {
        if ("mysql".equals(dbType)) {
            return String.format("%s >= date(now()) and %s < DATE_ADD(date(now()),INTERVAL 1 DAY)", column, column);
        } else if ("sqlserver".equals(dbType)) {
            return String.format("%s >=convert(varchar(10),getdate(),120) and %s < convert(varchar(10),dateadd(d,1,getdate()),120)", column, column);
        } else if ("oracle".equals(dbType)) {
            return String.format("%s >= to_char( sysdate ,’yyyy-mm-dd’) \n" +
                    "and %s < to_char( sysdate+1 ,’yyyy-mm-dd’)", column, column);
        } else if ("Access".equals(dbType)) {
            return String.format("%s >= date() and %s < DateAdd(“d”, 1, date())", column, column);
        }

        throw new RuntimeException("fatal: invalid dbType = " + dbType);
    }

    @Override
    public String toString(){
        return toSql();
    }
}
