package com.gnm.adrunner.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name = "media_param")
public class MediaParam {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private Integer type;

    private String  mediaKey;

    private String  paramKey;

    private String  paramValue;

    private String  createtime;

    public Integer getId() {
        return id;
    }

    public Integer getType() {
        return type;
    }

    public String getMediaKey() {
        return mediaKey;
    }

    public String getParamKey() {
        return paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public String getCreatetime() {
        return createtime;
    }

    @Override
    public String toString() {
        return "MediaParam [createtime=" + createtime + ", id=" + id + ", mediaKey=" + mediaKey + ", paramKey="
                + paramKey + ", paramValue=" + paramValue + ", type=" + type + "]";
    }

    
}