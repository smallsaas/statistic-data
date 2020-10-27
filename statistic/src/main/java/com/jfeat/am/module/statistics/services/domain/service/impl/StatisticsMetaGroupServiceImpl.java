package com.jfeat.am.module.statistics.services.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.statistics.api.model.TemplateChildren;
import com.jfeat.am.module.statistics.services.crud.ExtendedStatistics;
import com.jfeat.am.module.statistics.services.domain.service.StatisticsMetaGroupService;
import com.jfeat.am.module.statistics.services.gen.crud.service.impl.CRUDStatisticsMetaGroupServiceImpl;
import com.jfeat.am.module.statistics.services.gen.persistence.dao.StatisticsMetaGroupMapper;
import com.jfeat.am.module.statistics.services.gen.persistence.model.StatisticsMetaGroup;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("statisticsMetaGroupService")
public class StatisticsMetaGroupServiceImpl extends CRUDStatisticsMetaGroupServiceImpl implements StatisticsMetaGroupService {

    @Resource
    StatisticsMetaGroupMapper smgMapper;
    @Resource
    ExtendedStatistics extendedStatistics;

    protected final static Logger logger = LoggerFactory.getLogger(StatisticsMetaGroupServiceImpl.class);

    public StatisticsMetaGroup getPGroup(String groupName){
       StatisticsMetaGroup group = smgMapper.selectOne(new QueryWrapper<StatisticsMetaGroup>()
                .eq("name", groupName).isNull("pid"));

        if(group==null){
            throw new BusinessException(BusinessCode.CRUD_QUERY_FAILURE,"查询失败,未找到对应配置");
        }
       return group;
    }

    public JSONObject setHead(JSONObject mgTemplate,StatisticsMetaGroup pGroup){
        String layout = pGroup.getLayout();
        if(!StringUtils.isEmpty(layout)){
            JSONObject jsonObject = JSONObject.parseObject(layout);
            mgTemplate.put("layout",jsonObject);
        }else{
            mgTemplate.put("layout","");
        }

        mgTemplate.put("span",pGroup.getSpan());
        mgTemplate.put("title",pGroup.getTitle());
        return mgTemplate;
    }

    public JSONObject setBody(JSONObject mgTemplate, Long pid){
        List<StatisticsMetaGroup> sonList = smgMapper.selectList(new QueryWrapper<StatisticsMetaGroup>().eq("pid", pid));

        List<TemplateChildren> templateChildrenList =new ArrayList<>();

        for(StatisticsMetaGroup statisticsMetaGroup:sonList){
            if(StringUtils.isEmpty(statisticsMetaGroup.getField())){
                JSONObject pJSON = setBody(new JSONObject(), statisticsMetaGroup.getId());
                pJSON = setHead(pJSON, statisticsMetaGroup);
                mgTemplate.put(statisticsMetaGroup.getName(),pJSON);
                TemplateChildren templateChildren = new TemplateChildren(statisticsMetaGroup.getPresenter(),statisticsMetaGroup.getName());
                templateChildrenList.add(templateChildren);
            }else{
                //处理子类
                TemplateChildren templateChildren = new TemplateChildren(statisticsMetaGroup.getPresenter(),statisticsMetaGroup.getField());
                templateChildrenList.add(templateChildren);
                if (StringUtils.isEmpty(statisticsMetaGroup.getPattern())) {
                    logger.info("pattern为空,对应项Field：{}",statisticsMetaGroup.getField());
                    logger.info("pattern为空,对应项id：{}",statisticsMetaGroup.getId());
                    throw new BusinessException(BusinessCode.CRUD_QUERY_FAILURE,"对应配置的Pattern项为空");
                }
                putChindrenJSON(mgTemplate,templateChildren,statisticsMetaGroup.getPattern());
            }
        }
        mgTemplate.put("children",templateChildrenList);

        return mgTemplate;
    }

    @Override
    public JSONObject getTemplateByName(String groupName){
        StatisticsMetaGroup pGroup = getPGroup(groupName);
        JSONObject templateJSON = new JSONObject();
        templateJSON = setHead(templateJSON,pGroup);
        templateJSON=setBody(templateJSON, pGroup.getId());
        return templateJSON;
    }




    @Override
    public JSONObject putChindrenJSON(JSONObject template, TemplateChildren children, String pattern){
        JSONObject chJson = extendedStatistics.getByPattern(pattern, children.getField());
        template.put(children.getField(),chJson);
        return template;
    }

}
