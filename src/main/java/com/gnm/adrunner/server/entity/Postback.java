package com.gnm.adrunner.server.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name = "postback")
public class Postback {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String  adsKey;
    
    private String  mediaKey;

    private String  ptnClk;

    private String  uuid;

    private String  deviceId;

    private String  gaid;

    private String  idfa;

    private String  carrier;

    private String  brand;

    private String  model;

    private String  os;

    private String  osVer;

    private String  ip;

    private String  country;

    private String  language;

    private String  network;

    private String  ptnPub;

    private String  subPub;

    private String  eventName;

    private String  eventValue;

    private String  eventTime;

    private Integer advCost;

    private Integer mediaCost;

    private String  s_p1;

    private String  s_p2;

    private String  s_p3;

    private String  s_p4;

    private String  s_p5;

    private String  clickTime;

    private String  createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdsKey() {
        return adsKey;
    }

    public void setAdsKey(String adsKey) {
        this.adsKey = adsKey;
    }

    public String getMediaKey() {
        return mediaKey;
    }

    public void setMediaKey(String mediaKey) {
        this.mediaKey = mediaKey;
    }

    public String getPtnClk() {
        return ptnClk;
    }

    public void setPtnClk(String ptnClk) {
        this.ptnClk = ptnClk;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGaid() {
        return gaid;
    }

    public void setGaid(String gaid) {
        this.gaid = gaid;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVer() {
        return osVer;
    }

    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getPtnPub() {
        return ptnPub;
    }

    public void setPtnPub(String ptnPub) {
        this.ptnPub = ptnPub;
    }

    public String getSubPub() {
        return subPub;
    }

    public void setSubPub(String subPub) {
        this.subPub = subPub;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventValue() {
        return eventValue;
    }

    public void setEventValue(String eventValue) {
        this.eventValue = eventValue;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public Integer getAdvCost() {
        return advCost;
    }

    public void setAdvCost(Integer advCost) {
        this.advCost = advCost;
    }

    public Integer getMediaCost() {
        return mediaCost;
    }

    public void setMediaCost(Integer mediaCost) {
        this.mediaCost = mediaCost;
    }

    public String getS_p1() {
        return s_p1;
    }

    public void setS_p1(String s_p1) {
        this.s_p1 = s_p1;
    }

    public String getS_p2() {
        return s_p2;
    }

    public void setS_p2(String s_p2) {
        this.s_p2 = s_p2;
    }

    public String getS_p3() {
        return s_p3;
    }

    public void setS_p3(String s_p3) {
        this.s_p3 = s_p3;
    }

    public String getS_p4() {
        return s_p4;
    }

    public void setS_p4(String s_p4) {
        this.s_p4 = s_p4;
    }

    public String getS_p5() {
        return s_p5;
    }

    public void setS_p5(String s_p5) {
        this.s_p5 = s_p5;
    }

    public String getClickTime() {
        return clickTime;
    }

    public void setClickTime(String clickTime) {
        this.clickTime = clickTime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Postback [adsKey=" + adsKey + ", advCost=" + advCost + ", brand=" + brand + ", carrier=" + carrier
                + ", clickTime=" + clickTime + ", country=" + country + ", createtime=" + createtime + ", deviceId="
                + deviceId + ", eventName=" + eventName + ", eventTime=" + eventTime + ", eventValue=" + eventValue
                + ", gaid=" + gaid + ", id=" + id + ", idfa=" + idfa + ", ip=" + ip + ", language=" + language
                + ", mediaCost=" + mediaCost + ", mediaKey=" + mediaKey + ", model=" + model + ", network=" + network
                + ", os=" + os + ", osVer=" + osVer + ", ptnClk=" + ptnClk + ", ptnPub=" + ptnPub + ", s_p1=" + s_p1
                + ", s_p2=" + s_p2 + ", s_p3=" + s_p3 + ", s_p4=" + s_p4 + ", s_p5=" + s_p5 + ", subPub=" + subPub
                + ", uuid=" + uuid + "]";
    }
 
    
    
}