package com.jfeat.am.module.statistics.services.crud;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.statistics.api.model.MetaGroupTemplate;
import com.jfeat.am.module.statistics.api.model.MetaTag;
import com.jfeat.am.module.statistics.api.model.TemplateChildren;

public interface ExtendedStatistics {

    JSONObject getByPattern( String field,MetaTag metaTag);

    JSONObject getCountTemplate(String field);

    JSONObject getRateTemplate(String field);

    JSONObject getTimeLineTemplate(String field);
}
