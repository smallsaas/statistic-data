package com.jfeat.am.module.statistics.util;

import com.jfeat.am.module.statistics.api.model.MetaColumns;

public class MetaUtil {


    public static String transientType(String type) {

        String ctype = type.replaceAll(MetaColumns.DECIMAL, "金钱")
                .replaceAll(MetaColumns.TIME, "时间")
                .replaceAll(MetaColumns.PERCENT, "百分比")
                .replaceAll(MetaColumns.COUNT, "数量")
                .replaceAll(MetaColumns.STRING, "字符串")
                .replaceAll(MetaColumns.BIG_STRING, "粗体 字符串")
                .replaceAll(MetaColumns.JSON_OBJECT, "json object")
                .replaceAll(MetaColumns.USER_ARRAY, "用户头像")
                .replaceAll(MetaColumns.JSON_ARRAY, "json array");
        return ctype;
    }

    public static String replaceType(String type) {
        String ctype = type.replaceAll("金钱", MetaColumns.DECIMAL)
                .replaceAll("时间",MetaColumns.TIME)
                .replaceAll("百分比", MetaColumns.PERCENT)
                .replaceAll("数量", MetaColumns.COUNT)
                .replaceAll("字符串", MetaColumns.STRING)
                .replaceAll("粗体 字符串", MetaColumns.BIG_STRING)
                .replaceAll("json object", MetaColumns.JSON_OBJECT)
                .replaceAll("用户头像", MetaColumns.USER_ARRAY)
                .replaceAll("json array", MetaColumns.JSON_ARRAY);
        return ctype;
    }
}
