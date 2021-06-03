package com.jfeat.am.module.statistics.util;

import com.jfeat.am.module.statistics.services.gen.crud.model.StatisticsMetaModel;
import com.jfeat.am.module.statistics.services.gen.persistence.model.StatisticsMeta;

import java.io.*;

public class GenCodeUtil {

    public static final String DEFAULT_WEB_PAGE = File.separator+"src"+File.separator+"pages"+File.separator;
    public static String genUrl(String webProject,String field){
        StringBuffer url = new StringBuffer();
        url.append("..");
        url.append(File.separator);
        url.append(webProject);
        url.append(DEFAULT_WEB_PAGE);
        url.append(File.separator);
        url.append(field);
        return url.toString();

    }

    public static StringBuffer genIndexTemplate(StatisticsMeta statisticsMeta){
        StringBuffer reportString = new StringBuffer();
        reportString.append(" import React from 'react';\n");
        reportString.append(" import ZEle from 'zero-element';\n");
        reportString.append(" import config from './"+statisticsMeta.getField()+"';\n");
        reportString.append("\n");
        reportString.append(" export default () => <ZEle namespace=\""+statisticsMeta.getField()+"\" config={config} />;");
        return reportString;
    }

    public static StringBuffer genStringByMeta(StatisticsMeta statisticsMeta){
        StringBuffer reportString = new StringBuffer();
        reportString.append("module.exports = {\n");
        reportString.append("  layout: \"Content\",\n");
        reportString.append("  title: \""+statisticsMeta.getTitle()+"\",\n");
        reportString.append("  items: [\n");
        reportString.append( "    {\n");
        reportString.append( "      component: 'AutoReportSearch',\n");
        reportString.append( "      config: {},\n");
        reportString.append("    },\n");
        reportString.append("    {\n");
        reportString.append("      component: 'AutoReport',\n");
        reportString.append("      config: {\n");
        reportString.append("        pageSize: 20,\n");
        reportString.append("        API: {\n");
        reportString.append("          listAPI: \"/api/adm/stat/meta/"+statisticsMeta.getField()+"\",\n");
        reportString.append("        },\n");
        reportString.append("        actions: [\n");
        reportString.append("          {\n");
        reportString.append("            \"title\": \"导出\",\n");
        reportString.append("            \"type\": \"export\",\n");
        reportString.append("            \"options\": {\n");
        reportString.append("              \"API\": \"/api/io/excel/export/"+statisticsMeta.getField()+"\"\n");
        reportString.append("            }\n");
        reportString.append("          }\n");
        reportString.append("        ],\n");
        reportString.append("        fields: [],\n");
        reportString.append("        operation: []\n");
        reportString.append("      },\n");
        reportString.append( "    },\n");
        reportString.append("  ],\n");
        reportString.append("}");
        return reportString;
    }

    public static void genCode(String fileUrl,String fileName,String value){
        //创建目录
        File dir = new File(fileUrl);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //创建文件
        File f = new File(fileUrl+File.separator+fileName);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
           }
        try {

            FileOutputStream fileOutputStream = new FileOutputStream(f);
            fileOutputStream.write(value.toString().getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
