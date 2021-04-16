package com.jfeat.am.module.statistics.api.model;

public class MetaOutputSetting {
   public static final String JSON_STRING_SETTING = "" +
           "{\n" +
           "  layout: \"Content\",\n" +
           "  title: \"广告主统计\",\n" +
           "  items: [\n" +
           "    {\n" +
           "      component: 'AutoReportSearch',\n" +
           "      config: {},\n" +
           "    },\n" +
           "    {\n" +
           "      component: 'AutoReport',\n" +
           "      config: {\n" +
           "        pageSize: 20,\n" +
           "        API: {\n" +
           "          listAPI: \"/api/adm/stat/meta/advertiserReport\",\n" +
           "        },\n" +
           "        actions: [\n" +
           "          {\n" +
           "            \"title\": \"导出\",\n" +
           "            \"type\": \"export\",\n" +
           "            \"options\": {\n" +
           "              \"API\": \"/api/io/excel/export/advertiserReport\"\n" +
           "            }\n" +
           "          }\n" +
           "        ],\n" +
           "        fields: [],\n" +
           "        operation: []\n" +
           "      },\n" +
           "    },\n" +
           "  ],\n" +
           "}";

}
