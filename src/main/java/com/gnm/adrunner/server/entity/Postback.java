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

    private String  clickKey;

    private String  adsKey;
    
    private String  mediaKey;

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

    private String  sP1;

    private String  sP2;

    private String  sP3;

    private String  sP4;
    
    private String  sP5;

    private String  createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClickKey() {
        return clickKey;
    }

    public void setClickKey(String clickKey) {
        this.clickKey = clickKey;
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

    public String getsP1() {
        return sP1;
    }

    public void setsP1(String sP1) {
        this.sP1 = sP1;
    }

    public String getsP2() {
        return sP2;
    }

    public void setsP2(String sP2) {
        this.sP2 = sP2;
    }

    public String getsP3() {
        return sP3;
    }

    public void setsP3(String sP3) {
        this.sP3 = sP3;
    }

    public String getsP4() {
        return sP4;
    }

    public void setsP4(String sP4) {
        this.sP4 = sP4;
    }

    public String getsP5() {
        return sP5;
    }

    public void setsP5(String sP5) {
        this.sP5 = sP5;
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
                + ", clickKey=" + clickKey + ", country=" + country + ", createtime=" + createtime + ", deviceId="
                + deviceId + ", eventName=" + eventName + ", eventTime=" + eventTime + ", eventValue=" + eventValue
                + ", gaid=" + gaid + ", id=" + id + ", idfa=" + idfa + ", ip=" + ip + ", language=" + language
                + ", mediaCost=" + mediaCost + ", mediaKey=" + mediaKey + ", model=" + model + ", network=" + network
                + ", os=" + os + ", osVer=" + osVer + ", ptnPub=" + ptnPub + ", sP1=" + sP1 + ", sP2=" + sP2 + ", sP3="
                + sP3 + ", sP4=" + sP4 + ", sP5=" + sP5 + ", subPub=" + subPub + "]";
    }
 
}