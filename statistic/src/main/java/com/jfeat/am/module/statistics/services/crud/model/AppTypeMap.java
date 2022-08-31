package com.jfeat.am.module.statistics.services.crud.model;

public enum AppTypeMap {
    Min("MiniProgram","小程序"),
    APP("App","应用");


    private String name;
    private String chiName;

    AppTypeMap(String name,String chiName){
        this.name = name;this.chiName = chiName;
    }
    public String getName (){
        return name;
    }

    public String getChiName(){
        return chiName;
    }

    public static String getChiName(String name) {
        AppTypeMap[] values = AppTypeMap.values();
        for(AppTypeMap map : values){
           if(map.getName().equals(name)){
               return map.getChiName();
           }
        }
        return "";
    }
}
