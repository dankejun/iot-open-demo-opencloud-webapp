package com.cloud.common.pojo.dto;

import lombok.Data;

/**
 * @Author:gaoyx16
 * @Since:2020/9/18
 * @DESC: 返回结果封装
 */
@Data
public class BaseResponseDTO<T> {


    private String RetCode;
    private String RetInfo;
    private T RetBody;

    public BaseResponseDTO(String code, String msg, T data) {
        this.RetCode = code;
        this.RetInfo = msg;
        this.RetBody = data;
    }

    public BaseResponseDTO(String code, String msg) {
        this.RetCode = code;
        this.RetInfo = msg;
    }

    public BaseResponseDTO() {

    }

    /**
     * 构建消息内容
     *
     * @param msg
     * @return
     */
    public BaseResponseDTO buildMessage(String msg) {
        this.setRetInfo(msg);
        return this;
    }

    /**
     * 构建消息data的值，key默认为data
     *
     * @param obj data值
     * @return
     */
    public BaseResponseDTO buildData(T obj) {
        this.setRetBody(obj);
        return this;

    }

}
