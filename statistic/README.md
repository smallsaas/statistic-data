git@gitee.com:kequandian/sb-statistic.git

# 自动报表模块说明
## 自动报表实现流程
### 流程概要
1.前端配置页面文件
2.后端提供服务（自动报表的服务 和 一条报表的sql）
### 一. 前端配置页面

配置菜单项
src/config/router.config.js
```
	{
	  "path": "/agent",
	  "name": "股东管理",
	  "icon": "tag",
	  "items": [
      {
        "path": "/agent/agentReport",
        "name": "股东毛利净利报表",
        "icon": "tag",
      },
	  ]
	},
```

中间过渡文件
src/pages/agent/agentReport.js
```
import React from 'react';
import ZEle from 'zero-element';
import config from './config/agentReport';

export default function AgentReport() {
  return <ZEle namespace='agentReport' config={config} />;
}
```
主要内容配置
src/pages/agent/config/agentReport.js

//搜索栏配置


//选择一  api 配置
```
{
    layout: 'Empty',
    component: 'AutoReportSearch',
    config: {
        share: 'agentReport',
    }
}
```


//选择二   本地配置
```
     {
        layout: 'Empty',
        component: 'Search',
        config: {
          share: 'sysUserReport',
          fields: [
            {
              label: '店小二',
              type: 'select-fetch',
              props: {
                placeholder: '请选择',
                style: {
                  width: 240
                }
              },
              rules: [],
              options: {
                API: '/api/adm/users?roleid=server',
                label: 'name',
                value: 'id'
              },
              field: 'sysUserId'
            },
            {
              label: '开始时间',
              type: 'date',
              field: 'startDate'
            },
            {
              label: '结束时间',
              type: 'date',
              field: 'endDate'
            }
          ]
        },
      }
```


//agentReport.js 文件配置
```
module.exports = {
  layout: 'Content',
  title: '股东毛利净利报表',
  items: [
    {
      layout: 'Empty',
      component: 'AutoReportSearch',
      config: {
        share: 'agentReport',
      },
    },
    {
      layout: 'Empty',
      component: 'AutoReport',
      config: {
        share: 'agentReport',
        pageSize: 20,
        API: {
          listAPI: '/api/adm/stat/meta/agentReport',
        },
        actions: [
          {
            title: '导出 pdf', type: 'export-excel',
            options: {
              API: '/api/io/pdf/export/股东毛利净利',
              method: 'get',

            },
          },
        ],
        fields: [],
        operation: []
      },
    },
  ],
};
```
主要配置说明：
1. actions中配置了导出pdf的按钮 不需要的话去掉即可 需要的话还要同时部署一个导出pdf的服务
2. listAPI固定为'/api/adm/stat/meta/{fileName}' fileName为数据库中配置的fileName
3. 需要后端依赖自动报表模块 或单独部署一个自动报表的服务。


### 二. 后端配置

#### 配置自动报表sql

后端这里首先要有一条对应报表的sql记录，然后是项目依赖了自动报表模块或者提供了自动报表的服务。

数据库字段说明

```
CREATE TABLE `st_statistics_meta` (

  `type` varchar(50) DEFAULT NULL COMMENT '字段类型',
  `search` varchar(255) DEFAULT NULL COMMENT '搜索字段',
  `permission` varchar(50) DEFAULT NULL COMMENT '权限',
  `tips` text COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
```

|字段|类型|说明|
|:--:|:--:|:--:|
|id|bigint|唯一标识|
|field|varchar|fileName 对应上面前端配置的 agentReport |
|query_sql|varchar| 查询sql  |
|percent|smallint| 未用到 |
|icon|varchar| 未用到 |
|title|varchar| 未用到 |
|type|varchar| 用于定义执行query_sql后返回的字段类型   需要确保此处的配置与执行sql后返回的字段数量一致|
|search|varchar|搜索字段 例如： “股东,结算年度”  此处直接填写query_sql执行后最终返回的字段|
|permission|varchar|这个报表的权限|
|tips|text|对于字段的描述 |

**下面对于type字段和tips字段的使用详解**

**type字段类型**
- DECIMAL="D";//金钱
- TIME="T";//时间
- PERCENT="P";//百分比
- COUNT="C";//数量
- STRING="S";//字符串
- AVATAR="A";//头像

对应数据库中的type配置 S,S,S,D,D,D



**tips格式**
- 格式： {第几个字段} : {描述内容}
- 例如：  1:这个字段用于存储数据
- 结果：在第一个字段上增加一行小字描述

<br>
多个不同字段的描述用','分隔，如果描述中有','请改成中文形式的'，'  ':'也是 描述中要显示的话也改为中文的'：'
<br>

**search字段说明**
- search字段中填需要查询的字段
- 直接填写sql执行后需要查询的字段 例如： “股东,结算年度”

拓展：
- 所需要的字段不存在于查询出来的字段中，但任然需要查询 则需要在传递的时候带上类型
- 例如:时间-T
- 然后在对应的sql中写入#{时间}
- 具体SQL写法请看 [自定义报表-拓展SQL的编写.md](./自定义报表-拓展SQL的编写.md)


#### 目标项目依赖自动报表模块
```
 <dependency>
    <groupId>com.jfeat</groupId>
    <artifactId>statistic</artifactId>
    <version>1.3.0</version>
</dependency>

```

- 总数-饼图-柱状图-报表SQL写法请看 [自定义报表-总数-饼图-柱状图-报表SQL普通编写.md](./自定义报表-总数-饼图-柱状图-报表SQL普通编写.md)

- 前端需要通过一个api获得所有简单报表 该报表的配置见下面文档
- [自定义报表-各图组合配置说明.md](./自定义报表-各图组合配置说明.md)
