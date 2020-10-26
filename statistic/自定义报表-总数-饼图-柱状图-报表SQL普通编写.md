## 自定义报表-总数，饼图，柱状图 报表 SQL普通编写.md
###  一.Count SQL

```SQL
SELECT
'advertiserTotal' AS 'field',
'广告主总数' as 'title',
'广告主总数' as 'name',
 COUNT(*) AS 'value' ,
 'Count' as 'pattern',
 'BarTimeline' As 'chart',
 1 AS 'span'
FROM
  ca_advertiser
```

#### 使用说明
```
调用：/api/adm/stat/meta/{field}?pattern=count
```
注:st_statistics_meta表中配置{field} pattern这里选count
##### SQL说明

|参数|说明|
|:---:|:---:|
|field|域|
|title|标题|
|name|显示名|
|value|值|
|pattern|模式-未做处理，只是为了和旧模块统一|
|chart|使用的前端组件|

##### 配置说明

仅需配置field query_sql 即可

```SQL
INSERT INTO `st_statistics_meta` ( `field`, `query_sql`, `percent`, `icon`, `title`, `type`, `search`, `permission`, `tips`, `layout`, `span`, `chart`) VALUES 
( 'advertiserTotal', 'SELECT\r\n\'advertiserTotal\' AS \'field\',\r\n\'广告主总数\' as \'title\',\r\n\'广告主总数\' as \'name\',\r\n COUNT(*) AS \'value\' ,\r\n \'Count\' as \'pattern\',\r\n \'BarTimeline\' As \'chart\',\r\n 1 AS \'span\'\r\nFROM\r\n  ca_advertiser ', '0', NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL);
```

### 二.饼图SQL示例

```SQL
select ca_adverting_plan.`status` as `name`,COUNT(*) as `value` 
from ca_adverting_plan GROUP BY ca_adverting_plan.`status`
```
#### 使用说明
```
调用：/api/adm/stat/meta/{field}?pattern=rate
```
注:st_statistics_meta表中配置{field} pattern这里选rate
##### SQL说明

|参数|说明|
|:---:|:---:|
|name|字段名|
|value|对应的值|

##### 配置说明

需配置
field 域
query_sql查询的sql
title标题 
chart模组（不配置默认pie）

```SQL
INSERT INTO `st_statistics_meta` ( `field`, `query_sql`, `percent`, `icon`, `title`, `type`, `search`, `permission`, `tips`, `layout`, `span`, `chart`) VALUES 
( 'orderStatePie', 'select ca_adverting_plan.`status` as `name`,COUNT(*) as `value`\r\nfrom ca_adverting_plan GROUP BY ca_adverting_plan.`status`', '0', NULL, '订单状态占比', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
```


#### 三.柱状图示例

#### 使用说明
```
调用：/api/adm/stat/meta/{field}?pattern=TimeLine
```
注:st_statistics_meta表中配置{field} pattern这里选TimeLine

```SQL
SELECT 
    mon.cmonth as `name`, count(ca_adverting_plan.id) as 'value',mon.seq
FROM
(
	SELECT
		DATE_FORMAT( @cdate := DATE_ADD( @cdate, INTERVAL + 1 MONTH ), '%Y-%m' ) AS cmonth ,FORMAT(@seq := @seq+1,0) as 'seq'
	FROM
		( SELECT @cdate := DATE_ADD( concat(year(now()),'-12-31'), INTERVAL - 1 YEAR ) FROM pcd LIMIT 12 ) t0 ,(select @seq :=0) as seq
	WHERE
		date( @cdate ) <= DATE_ADD( concat(year(now()),'-12-31'), INTERVAL - 1 DAY )
	
)  mon 
LEFT JOIN
    ca_adverting_plan on mon.cmonth = DATE_FORMAT(ca_adverting_plan.create_time, '%Y-%m')
GROUP BY mon.cmonth
```

##### SQL说明

|参数|说明|
|:---:|:---:|
|name|柱状图变量名|
|value|柱状图变量值|
|seq|排序|

示例SQL中的mon子表用于查出本年的12个月
seq为排序用字段
关于月份的查询可以参考  [README.md](../README.md)

##### 配置说明
需配置
field 域
query_sql查询的sql
title标题 
chart模组（不配置默认BarGroup_2）
