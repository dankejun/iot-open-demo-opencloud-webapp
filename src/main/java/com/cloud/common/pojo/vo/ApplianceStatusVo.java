package com.cloud.common.pojo.vo;

import com.alibaba.fastjson.JSONObject;

public class ApplianceStatusVo {
    private String applianceCode;
    private String onlineStatus;
    private JSONObject status = new JSONObject();

    public String getApplianceCode() {
        return applianceCode;
    }

    public void setApplianceCode(String applianceCode) {
        this.applianceCode = applianceCode;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public JSONObject getStatus() {
        return status;
    }

    public void setStatus(JSONObject status) {
        this.status = status;
    }
}
