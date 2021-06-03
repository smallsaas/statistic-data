package com.jfeat.am.module.statistics.api.model;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "genWeb")
public class GenWebSetting {
    private String webProject;

    public String getWebProject() {
        return webProject;
    }

    public void setWebProject(String webProject) {
        this.webProject = webProject;
    }
}
