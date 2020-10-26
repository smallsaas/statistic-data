package com.jfeat.am.module.statistics.services.crud;

import com.alibaba.fastjson.JSONObject;

public interface ExtendedStatistics {

    JSONObject getCountTemplate(String field);

    /*
        *  {
                            "field": "pie",
                            "pattern": "Rate",
                            "identifier": "pie",
                            "title": "pie图",
                            "chart": "BarTimeline",
                            "span": 1,
                            "tl": null,
                            "name": "pie图",
                            "rates": [
                                {
                                    "id": "pie",
                                    "name": "pie例子",
                                    "value": "20",
                                    "seq": 0
                                }
                            ]
                        }
        * */
    JSONObject getRateTemplate(String field);
}
