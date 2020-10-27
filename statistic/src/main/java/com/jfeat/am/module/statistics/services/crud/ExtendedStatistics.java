package com.jfeat.am.module.statistics.services.crud;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.statistics.api.model.MetaGroupTemplate;
import com.jfeat.am.module.statistics.api.model.TemplateChildren;

public interface ExtendedStatistics {

    JSONObject getByPattern(String pattern, String field);

    JSONObject getCountTemplate(String field);

    JSONObject getRateTemplate(String field);

    JSONObject getTimeLineTemplate(String field);
}
