## 自定义报表-总数，饼图，柱状图 报表 SQL普通编写.md

### 创建数据表
```SQL
CREATE TABLE `st_statistics_meta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `field` varchar(80) NOT NULL COMMENT '数据指标唯一标识符',
  `query_sql` text COMMENT '实时查询sql',
  `percent` smallint(6) DEFAULT '0' COMMENT '是否显示为百分比',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标路径',
  `title` varchar(30) DEFAULT NULL COMMENT '图表标题',
  `type` varchar(50) DEFAULT NULL COMMENT '字段类型 D金钱  T时间  P百分比  C数量  S字符串 存储例子：D，D，T',
  `search` varchar(255) DEFAULT NULL COMMENT '搜索字段',
  `permission` varchar(50) DEFAULT NULL COMMENT '权限',
  `tips` text COMMENT '说明',
  `layout` varchar(26) DEFAULT NULL COMMENT '布局',
  `span` int(11) DEFAULT NULL COMMENT '子分组占父分组的列跨度',
  `chart` varchar(50) DEFAULT NULL COMMENT '组件',
  `pattern` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
```

###  一.Count SQL配置

#### SQL（不带参数）
> api调用：/api/adm/stat/meta/{field}

> field ：数据指标唯一标识符
```SQL
SELECT
	'advertingPlanTotal' AS 'field',
	'投放广告总数' AS 'title',
	'投放广告总数' AS 'name',
	COUNT(*) AS 'value',
	'Count' AS 'pattern',
	'BarTimeline' AS 'chart',
	1 AS 'span'
FROM
	ca_adverting_plan
WHERE
	ca_adverting_plan.`status` != 'REVIEW_REJECTED'
```

#### SQL（带参数）
> api调用：/api/adm/stat/meta/{field}?param=100000000000000090

> field ：数据指标唯一标识符

> param : 传递的参数（orgId，userId和bUserType这三个参数不用传递，后台已经处理好）

> SQL添加
@if[param!=null]
AND ca_table.param = #{param}
@end

```SQL
SELECT
	'advertingPlanTotal' AS 'field',
	'投放广告总数' AS 'title',
	'投放广告总数' AS 'name',
	COUNT(*) AS 'value',
	'Count' AS 'pattern',
	'BarTimeline' AS 'chart',
	1 AS 'span'
FROM
	ca_adverting_plan
LEFT JOIN ca_advertiser ON ca_advertiser.id = ca_adverting_plan.advertiser_id
WHERE
	ca_adverting_plan.`status` != 'REVIEW_REJECTED'
@if[orgId!=null]
AND ca_advertiser.org_id = #{orgId}
@end
```

##### SQL说明

|参数|说明|
|:---:|:---:|
|field|域|
|title|标题|
|name|显示名|
|value|值|
|pattern|模式-未做处理，只是为了和旧模块统一|
|chart|模式-未做处理，使用的前端组件|
|span|模式-未做处理，布局|

##### 配置说明

仅需配置field query_sql 即可

```SQL
INSERT INTO `st_statistics_meta` ( `field`, `query_sql`, `percent`, `icon`, `title`, `type`, `search`, `permission`, `tips`, `layout`, `span`, `chart`, `pattern`) 
 VALUES ('2', 'advertiserTotal', 'SELECT\r\n\'advertiserTotal\' AS \'field\',\r\n\'广告主总数\' as \'title\',\r\n\'广告主总数\' as \'name\',\r\n COUNT(*) AS \'value\' ,\r\n \'Count\' as \'pattern\',\r\n \'BarTimeline\' As \'chart\',\r\n 1 AS \'span\'\r\nFROM\r\n  ca_advertiser ', '0', NULL, NULL, '', NULL, NULL, NULL, NULL, '1', NULL, 'Count');
```

### 二.饼图SQL示例

```SQL
SELECT
   (case 
       WHEN t.`status`= 'TO_BE_REVIEWED' THEN '待审核'
       WHEN t.`status`= 'REVIEW_APPROVED' THEN '审核通过'
       WHEN t.`status`= 'REVIEW_REJECTED' THEN '审核拒绝'
       WHEN t.`status`= 'ORDER_PLACED' THEN '已下单'
       WHEN t.`status`= 'ABOLISHED' THEN '已作废'
       ELSE '' 
       END ) AS name,
        COUNT(*) AS `value`
    FROM
        ca_adverting_plan t
    GROUP BY
        t.`status`
```
#### 使用说明
> field ：数据指标唯一标识符
```
调用：/api/adm/stat/meta/{field}
```
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
INSERT INTO `st_statistics_meta` ( `field`, `query_sql`, `percent`, `icon`, `title`, `type`, `search`, `permission`, `tips`, `layout`, `span`, `chart`, `pattern`) 
  VALUES ('5', 'orderStatePie', 'SELECT\r\n	   (case \r\n           WHEN t.`status`= \'TO_BE_REVIEWED\' THEN \'待审核\'\r\n           WHEN t.`status`= \'REVIEW_APPROVED\' THEN \'审核通过\'\r\n					 WHEN t.`status`= \'REVIEW_REJECTED\' THEN \'审核拒绝\'\r\n					 WHEN t.`status`= \'ORDER_PLACED\' THEN \'已下单\'\r\n					 WHEN t.`status`= \'ABOLISHED\' THEN \'已作废\'\r\n           ELSE \'\' END)AS name,\r\n			COUNT(*) AS `value`\r\n		FROM\r\n			ca_adverting_plan t\r\n		GROUP BY\r\n			t.`status`', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2', NULL, 'Rate');
```


#### 三.柱状图示例

#### 使用说明
```
调用：/api/adm/stat/meta/{field}
```

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

#### 四.列表排名示例

#### 使用说明
```
调用：/api/adm/stat/meta/{field}
```

```SQL
SELECT
	ca_advertiser.contact_name AS `名字`,
    ca_advertiser.avatar AS `头像`,
	ca_advertiser.contact_phone AS `联系方式`,
	ca_advertiser.address AS `地址`,
	ca_advertiser.id,
	COUNT(*) AS `广告计划数量`
FROM
	ca_advertiser
LEFT JOIN ca_adverting_plan ON ca_adverting_plan.advertiser_id = ca_advertiser.id
WHERE
	ca_adverting_plan.`status` != 'REVIEW_REJECTED'
GROUP BY
	ca_advertiser.id
ORDER BY
	`广告计划数量` DESC
LIMIT 0,10
```

##### SQL说明
> 注 如`名字`和`头像`字段需要合并，则必须配置为`名字`和`头像`。其他字段可以根据需求显示。

> 如需显示头像字段，数据表type域需配置为。例如S,A,S,S,C,C

A
##### 配置说明
需配置
field 域
query_sql查询的sql
title标题 
chart模组（不配置默认BarGroup_2）
