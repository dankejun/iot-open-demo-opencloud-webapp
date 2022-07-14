package com.cloud.common.pojo.vo;

import com.alibaba.fastjson.JSONObject;

/**
 * body的基本格式
 * 其中:
 * <li>header<li/>是固定的
 * <li>payload<li/>是不固定的
 */
public class CommonRequest {

    private Header header;
    //TODO 需要确认
    private JSONObject payload = new JSONObject();

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public JSONObject getPayload() {
        return payload;
    }

    public void setPayload(JSONObject payload) {
        this.payload = payload;
    }

}
