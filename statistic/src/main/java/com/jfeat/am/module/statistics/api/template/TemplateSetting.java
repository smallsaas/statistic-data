package com.jfeat.am.module.statistics.api.template;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.statistics.api.model.TemplateChildren;
import com.jfeat.am.module.statistics.api.model.MetaGroupTemplate;

import java.util.List;

public class TemplateSetting {
    public static MetaGroupTemplate getAdvTemplate(){
        MetaGroupTemplate templateConfig = new MetaGroupTemplate();
        templateConfig.setLayout(new JSONObject());
        templateConfig.setTitle("广告主报表");
        templateConfig.setSpan(1);

        TemplateChildren templateChildren = new TemplateChildren("titledTotal","advertiserTotal","count");
        TemplateChildren templateChildren2 = new TemplateChildren("TimeLine","mon","TimeLine");
        List<TemplateChildren> templateChildrenList = List.of(templateChildren,templateChildren2);
        templateConfig.setChildren(templateChildrenList);

        return templateConfig;


    }
}
