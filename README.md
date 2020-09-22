## statistic-data
基于springboot的报表快速开发模块

### 特性
结合zero-element将统计报表可配置化。只需编写相应sql语句，即可通过相应配置做到展示统计图表，极大程度上减少了工作量。

### 使用
zero-data目前仅作为单独模块提供API支持，使用/api/stat/groups/{group}接口获取数据，通过identifier参数进行数据筛选。


### 统计SQL写法参考
```sql
SELECT field,record_name,record_time,
  max(case when field_name='field1' then value else 0 end) AS '点赞数',
  max(case when field_name='field2' then value else 0 end) AS '阅读数',
  max(case when field_name='field3' then value else 0 end) AS '文章名称'
FROM st_statistic_record group by field, record_name, record_time;
```
