package com.gnm.adrunner.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name = "ads")
public class Ads {
   
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private Integer aff;

    private Integer os;
    
    private Integer type;

    private Boolean autostart;

    private Boolean autodown;

    private Boolean supplyDemand;

    private String  startdate;

    private String  enddate;

    private String  loopbackdate;

    private String  description;

    private String  name;

    private String  advKey;

    private String  adsKey;

    private Boolean isDelete;

    private Integer status;

    private String  deletetime;

    private String  updatetime;

    private String  createtime;

    private String  trackingUrl;

    private String  eventName;

    private Integer cost1;

    private Integer cost2;

    private Integer redisGroup;
    
    private Integer redisDb;

    private Boolean isPostback;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAff() {
        return aff;
    }

    public void setAff(Integer aff) {
        this.aff = aff;
    }

    public Integer getOs() {
        return os;
    }

    public void setOs(Integer os) {
        this.os = os;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getAutostart() {
        return autostart;
    }

    public void setAutostart(Boolean autostart) {
        this.autostart = autostart;
    }

    public Boolean getAutodown() {
        return autodown;
    }

    public void setAutodown(Boolean autodown) {
        this.autodown = autodown;
    }

    public Boolean getSupplyDemand() {
        return supplyDemand;
    }

    public void setSupplyDemand(Boolean supplyDemand) {
        this.supplyDemand = supplyDemand;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getLoopbackdate() {
        return loopbackdate;
    }

    public void setLoopbackdate(String loopbackdate) {
        this.loopbackdate = loopbackdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdvKey() {
        return advKey;
    }

    public void setAdvKey(String advKey) {
        this.advKey = advKey;
    }

    public String getAdsKey() {
        return adsKey;
    }

    public void setAdsKey(String adsKey) {
        this.adsKey = adsKey;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(String deletetime) {
        this.deletetime = deletetime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getCost1() {
        return cost1;
    }

    public void setCost1(Integer cost1) {
        this.cost1 = cost1;
    }

    public Integer getCost2() {
        return cost2;
    }

    public void setCost2(Integer cost2) {
        this.cost2 = cost2;
    }

    public Integer getRedisGroup() {
        return redisGroup;
    }

    public void setRedisGroup(Integer redisGroup) {
        this.redisGroup = redisGroup;
    }

    public Integer getRedisDb() {
        return redisDb;
    }

    public void setRedisDb(Integer redisDb) {
        this.redisDb = redisDb;
    }

    public Boolean getIsPostback() {
        return isPostback;
    }

    public void setIsPostback(Boolean isPostback) {
        this.isPostback = isPostback;
    }

    @Override
    public String toString() {
        return "Ads [adsKey=" + adsKey + ", advKey=" + advKey + ", aff=" + aff + ", autodown=" + autodown
                + ", autostart=" + autostart + ", cost1=" + cost1 + ", cost2=" + cost2 + ", createtime=" + createtime
                + ", deletetime=" + deletetime + ", description=" + description + ", enddate=" + enddate
                + ", eventName=" + eventName + ", id=" + id + ", isDelete=" + isDelete + ", isPostback=" + isPostback
                + ", loopbackdate=" + loopbackdate + ", name=" + name + ", os=" + os + ", redisDb=" + redisDb
                + ", redisGroup=" + redisGroup + ", startdate=" + startdate + ", status=" + status + ", supplyDemand="
                + supplyDemand + ", trackingUrl=" + trackingUrl + ", type=" + type + ", updatetime=" + updatetime + "]";
    }
 
    
}