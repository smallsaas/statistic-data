package com.jfeat.am.module.statistics.services.crud.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.statistics.api.model.MetaGroupTemplate;
import com.jfeat.am.module.statistics.api.model.MetaTag;
import com.jfeat.am.module.statistics.api.model.TemplateChildren;
import com.jfeat.am.module.statistics.services.crud.ExtendedStatistics;
import com.jfeat.am.module.statistics.services.crud.StatisticsMetaService;
import com.jfeat.am.module.statistics.services.gen.persistence.model.StatisticsMeta;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExtendedStatisticsImpl implements ExtendedStatistics {

    private static final Logger logger = LoggerFactory.getLogger(ExtendedStatisticsImpl.class);

    @Resource
    StatisticsMetaService statisticsMetaService;

    @Override
    public JSONObject getByPattern(String field,MetaTag metaTag){
        JSONObject data = new JSONObject();
        StatisticsMeta statisticsMetas = statisticsMetaService.getStatisticsMetas(field);
        String pattern = statisticsMetas.getPattern();
        if(pattern==null){
            metaTag.setEnableHead(true);
            metaTag.setEnablePages(true);
            metaTag.setEnableSearch(true);
            metaTag.setEnableTips(true);
            metaTag.setEnableType(true);
            data=statisticsMetaService.getByField(field,metaTag);
        }else{
            switch (pattern){
                case "count":case "Count": data=this.getCountTemplate(field);break;
                case "Rate": case "rate" : data=this.getRateTemplate(field);break;
                case "Timeline":case "TimeLine": case "timeLine": case "timeline": data=this.getTimeLineTemplate(field);break;
                default : logger.info("当前配置类型:{} 配置的域:{}",pattern,field);throw new BusinessException(BusinessCode.ErrorStatus,"该类型未配置");
            }
        }


        return data;
    }



    @Override
    public JSONObject getCountTemplate(String field){
        MetaTag mataTag= new MetaTag();

        JSONObject data=new JSONObject();
        StatisticsMeta statisticsMetas = statisticsMetaService.getStatisticsMetas(field);
        StringBuilder sql = new StringBuilder(statisticsMetas.getQuerySql());
        if(statisticsMetas.getSpan()!=null){
            data.put("span",statisticsMetas.getSpan());
        }
        data = statisticsMetaService.getTableInfo(data, null, sql, mataTag,"rates");
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
        if(StringUtils.isEmpty(statisticsMetas.getChart())){
            pie.put("chart","Pie");
        }else{
            pie.put("chart",statisticsMetas.getChart());
        }

        if(statisticsMetas.getSpan()!=null){
            pie.put("span",statisticsMetas.getSpan());
        }
        pie.put("name",statisticsMetas.getTitle());

        StringBuilder sql = new StringBuilder(statisticsMetas.getQuerySql());
        pie = statisticsMetaService.getTableInfo(pie, null, sql, mataTag,"rates");
        return pie;

    }

    @Override
    public JSONObject getTimeLineTemplate(String field){
        MetaTag mataTag= new MetaTag();
        JSONObject timeLine=new JSONObject();

        StatisticsMeta statisticsMetas = statisticsMetaService.getStatisticsMetas(field);

        if(StringUtils.isEmpty(statisticsMetas.getChart())){
            timeLine.put("chart","BarGroup_2");
        }else{
            timeLine.put("chart",statisticsMetas.getChart());
        }
        timeLine.put("field",statisticsMetas.getField());
        timeLine.put("identifier","");
        timeLine.put("name",statisticsMetas.getTitle());
        timeLine.put("pattern","Rate");
        timeLine.put("span",statisticsMetas.getSpan());
        timeLine.put("title",statisticsMetas.getTitle());
        timeLine.put("tl","");

        StringBuilder sql = new StringBuilder(statisticsMetas.getQuerySql());
        timeLine = statisticsMetaService.getTableInfo(timeLine, null, sql, mataTag,"rates");

        return timeLine;

    }


}
