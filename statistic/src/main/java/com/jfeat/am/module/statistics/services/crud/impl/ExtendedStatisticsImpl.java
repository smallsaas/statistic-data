package com.jfeat.am.module.statistics.services.crud.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.statistics.api.model.MetaTag;
import com.jfeat.am.module.statistics.services.crud.ExtendedStatistics;
import com.jfeat.am.module.statistics.services.crud.StatisticsMetaService;
import com.jfeat.am.module.statistics.services.gen.persistence.model.StatisticsMeta;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExtendedStatisticsImpl implements ExtendedStatistics {

    @Resource
    StatisticsMetaService statisticsMetaService;

    //返回最基础的模板
    /**
     * 		"title": "订单统计"
     * 		"layout": {"name":"Grid", "props":{}},
     * 		"span": 1,
     *        "children":
     *        [
     *           {
     *              "presenter":"titledTotal",
     *              "field":"total:today:all@stat:product",
     *           },
     *           {
     * 			 "presenter":" titledClusterTotal",
     *              "field":"total:today:all@stat:product"
     *           },
     *        ]
     * 		"total:today:all@stat:product": {…}
     * 		"total:curmon:count@stat:order:count": {…}
     */
    public JSONObject getBaseTemplate(String field){
        JSONObject template=new JSONObject();
        StatisticsMeta statisticsMetas = statisticsMetaService.getStatisticsMetas(field);
        //标题
        template.put("title",statisticsMetas.getTitle());
        //布局
        if(statisticsMetas.getLayout()!=null){
            template.put("layout",statisticsMetas.getLayout());
        }
        //子分组占父分组的列跨度
        if(statisticsMetas.getSpan()!=null){
            template.put("span",statisticsMetas.getSpan());
        }
        //
        return template;
    }

    @Override
    public JSONObject getCountTemplate(String field){
        MetaTag mataTag= new MetaTag();

        JSONObject data=new JSONObject();
        StatisticsMeta statisticsMetas = statisticsMetaService.getStatisticsMetas(field);
        StringBuilder sql = new StringBuilder(statisticsMetas.getQuerySql());
        data = statisticsMetaService.getTableInfo(data, null, sql, mataTag,"rows");
        return data;
    }

    @Override
    public JSONObject getRateTemplate(String field){
        MetaTag mataTag= new MetaTag();
        JSONObject pie=new JSONObject();

        StatisticsMeta statisticsMetas = statisticsMetaService.getStatisticsMetas(field);
        pie.put("field",field);
        pie.put("pattern","Rote");
        pie.put("title",statisticsMetas.getTitle());
        pie.put("chart","Pie");
        if(statisticsMetas.getSpan()!=null){
            pie.put("span",statisticsMetas.getSpan());
        }
        pie.put("name",statisticsMetas.getTitle());

        StringBuilder sql = new StringBuilder(statisticsMetas.getQuerySql());
        pie = statisticsMetaService.getTableInfo(pie, null, sql, mataTag,"rates");
        return pie;

    }


    public JSONObject getTimeLineTemplate(String field){
        MetaTag mataTag= new MetaTag();
        JSONObject timeLine=new JSONObject();
        JSONObject itemJSON=new JSONObject();


        JSONArray items =new JSONArray();

        StatisticsMeta statisticsMetas = statisticsMetaService.getStatisticsMetas(field);

        timeLine.put("layout",statisticsMetas.getLayout());
        timeLine.put("span",statisticsMetas.getSpan());

        timeLine.put("chart","BarGroup_2");
        timeLine.put("field",statisticsMetas.getField());

        timeLine.put("identifier","");
        timeLine.put("pattern","");
        timeLine.put("name",statisticsMetas.getTitle());
        timeLine.put("tl","");

        return timeLine;

    }


}
