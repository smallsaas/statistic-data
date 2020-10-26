SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for st_statistic_group
-- ----------------------------
DROP TABLE IF EXISTS `st_statistics_group`;
CREATE TABLE `st_statistics_group` (
  `id` bigint(20) AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL COMMENT '上级分组',
  `name` varchar(50) NOT NULL COMMENT '组名[唯一标记]',
  `layout` varchar(26) DEFAULT NULL COMMENT '布局名称',
  `title` varchar(26) DEFAULT NULL COMMENT '组标题',
  `span` smallint DEFAULT 1 COMMENT '子分组占父分组的列跨度',
  `idx` smallint DEFAULT 0 COMMENT '分组排序号',
  `note` text DEFAULT NULL COMMENT '分组描述',
  UNIQUE(`name`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for st_statistics_field
-- 图标名称 [Num,Array,Pie,Column,Chain,Line] Chain-环比
-- ----------------------------
DROP TABLE IF EXISTS `st_statistics_field`;
CREATE TABLE `st_statistics_field` (
  `id` bigint(20) AUTO_INCREMENT,
  `field` varchar(80) NOT NULL COMMENT '数据域唯一标识符',
  `group_id` bigint(20) DEFAULT NULL COMMENT '所属分组ID',
  `group_name` varchar(50) NOT NULL COMMENT '所属分组名称',
  `name` varchar(50) NOT NULL COMMENT '数据域名称',
  `pattern` varchar(26) NOT NULL COMMENT '统计数据类型[Count,Rate,Tuple [Timeline,Cluster]]',
  `chart` varchar(26) NOT NULL COMMENT '标准组件图表名称',
  `attr_invisible` smallint DEFAULT 0 COMMENT '[属性]是否不可见',
  `attr_runtime` smallint DEFAULT 0 COMMENT '是否实时查询[via meta]',
  `attr_span` smallint DEFAULT 1 COMMENT '[属性]所占布局跨列数',
  `attr_index` smallint DEFAULT 0 COMMENT '[属性]排序号',
  UNIQUE(`field`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for st_statistic_record
-- T      -  Total
-- D,W,Y  -  Day/Week/Year
-- LD3    -  Latest 3 Days
-- LM3    -  Latest 3 Months
-- TF     -  Time Frame
-- Chain  -  Record Chain
-- ----------------------------
DROP TABLE IF EXISTS `st_statistics_record`;
CREATE TABLE `st_statistics_record` (
  `id` bigint(20) AUTO_INCREMENT,
  `field` varchar(80) NOT NULL COMMENT '数据域标识符',
  `identifier` varchar(80) DEFAULT NULL COMMENT '统计归属标识',
  `seq` int NOT NULL DEFAULT 0 COMMENT '记录排序号',
  `record_name` varchar(50) NOT NULL COMMENT '记录名称',
  `record_value` varchar(50) NOT NULL COMMENT '记录值',
  `record_tuple` varchar(30) DEFAULT NULL COMMENT '记录值所属行名称',
  `record_cluster` varchar(30) DEFAULT NULL COMMENT '记录值所属分类名称',
  `record_timeline` varchar(30) DEFAULT NULL COMMENT '记录值所属时间区间名称',
  `timeline` varchar(8) DEFAULT NULL COMMENT '统计时段说明[T,D,W,M,Y,LD3,LW1,LM1,LM3,Q1,Q2,Q3,Q4,TF]',
  `create_time` datetime NOT NULL DEFAULT current_timestamp COMMENT '记录创建时间',
  `tmp_field_id` bigint(20) DEFAULT NULL COMMENT '临时标记数据域ID',
  UNIQUE(`field`,`record_name`,`record_tuple`,`record_cluster`,`timeline`,`identifier`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for st_statistics_field
-- ----------------------------
DROP TABLE IF EXISTS `st_statistics_meta`;
CREATE TABLE `st_statistics_meta` (
  `id` bigint(20) AUTO_INCREMENT,
  `field` varchar(80) NOT NULL COMMENT '数据指标唯一标识符',
  `query_sql` text DEFAULT NULL COMMENT '实时查询sql',
  `percent` smallint DEFAULT 0 COMMENT '是否显示为百分比',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标路径',
  `title` varchar(30) DEFAULT NULL COMMENT '图表标题',
  `type` varchar(50) DEFAULT NULL COMMENT '字段类型 D金钱  T时间  P百分比  C数量  S字符串 存储例子：D，D，T',
  `search` varchar(255) DEFAULT NULL COMMENT '搜索字段',
  `permission` varchar(50) DEFAULT NULL COMMENT '权限',
  `layout` VARCHAR(26) DEFAULT null COMMENT '布局',
  `chart` VARCHAR(50) DEFAULT null COMMENT '组件',
  `tips` text COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
