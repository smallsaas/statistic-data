## statistic-data
[![Build Status](https://travis-ci.org/pravega/pravega.svg?branch=master)](https://travis-ci.org/pravega/pravega/builds) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0) 

> 基于springboot的报表快速开发模块
基于 [ECHART](https://echarts.apache.org/examples/zh/index.html) 图表展示

### 特性
结合zero-element将统计报表可配置化。只需编写相应sql语句，即可通过相应配置做到展示统计图表，极大程度上减少了工作量。

### 使用
zero-data目前仅作为单独模块提供API支持，使用/api/stat/groups/{group}接口获取数据，通过identifier参数进行数据筛选。


### 状态统计查询例子
```sql
SELECT (CASE 
           WHEN t.status='DELIVERING' THEN '执行中',
           WHEN t.status='DELIVERED' THEN '已下发',
           WHEN t.status='CONFIRMING' THEN '待确认',
           WHEN t.status='VERIFIED' THEN '已确认',
           WHEN t.status='CLOSED' THEN '已关闭' 
	END）as name,
	COUNT(*) as value
FROM t_task_order t
GROUP BY t.status
```

### 月度查询例子
```sql
select 	SUM(CASE WHEN MONTH(s.create_time) = 1 THEN 1 ELSE 0 END) as '一月',
				SUM(CASE WHEN MONTH(s.create_time) = 2 THEN 1 ELSE 0 END) as '二月',
				SUM(CASE WHEN MONTH(s.create_time) = 3 THEN 1 ELSE 0 END) as '三月',
				SUM(CASE WHEN MONTH(s.create_time) = 4 THEN 1 ELSE 0 END) as '四月',
				SUM(CASE WHEN MONTH(s.create_time) = 5 THEN 1 ELSE 0 END) as '五月',
				SUM(CASE WHEN MONTH(s.create_time) = 6 THEN 1 ELSE 0 END) as '六月',
				SUM(CASE WHEN MONTH(s.create_time) = 7 THEN 1 ELSE 0 END) as '七月',
				SUM(CASE WHEN MONTH(s.create_time) = 8 THEN 1 ELSE 0 END) as '八月',
				SUM(CASE WHEN MONTH(s.create_time) = 9 THEN 1 ELSE 0 END) as '九月',
				SUM(CASE WHEN MONTH(s.create_time) = 10 THEN 1 ELSE 0 END) as '十月',
				SUM(CASE WHEN MONTH(s.create_time) = 11 THEN 1 ELSE 0 END) as '十一月',
				SUM(CASE WHEN MONTH(s.create_time) = 12 THEN 1 ELSE 0 END) as '十二月'
FROM ca_adverting_plan AS s
```

```SQL
-- 本月之前（包括本月）之前12月
SELECT DATE_FORMAT( @cdate := DATE_ADD( @cdate, INTERVAL + 1 MONTH ), '%Y-%m' ) AS month_list 
FROM ( SELECT @cdate := DATE_ADD( CURRENT_DATE, INTERVAL - 12 MONTH ) FROM `pcd` LIMIT 12 ) t0 
WHERE DATE( @cdate ) <= DATE_ADD( CURRENT_DATE, INTERVAL - 1 DAY ) 

-- 今年1月到12月
SELECT DATE_FORMAT( @cdate := DATE_ADD( @cdate, INTERVAL + 1 MONTH ), '%Y-%m' ) AS month_list 
FROM ( SELECT @cdate := DATE_ADD( concat(year(now()),'-12-31'), INTERVAL - 12 MONTH ) FROM `pcd` LIMIT 12 ) t0 
WHERE DATE( @cdate ) <= DATE_ADD( concat(year(now()),'-12-31'), INTERVAL - 1 DAY ) 

```

#### 测试方式
```sql
DROP TABLE IF EXISTS t_sink;
CREATE TABLE t_sink (`name` varchar(26), `count` int);
INSERT INTO t_sink VALUES ('aaa', 2), ('bbb', 10);

select name,count as '排序' from t_sink ORDER BY `排序` DESC;

DROP TABLE IF EXISTS t_sink;
```



