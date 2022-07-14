package com.cloud.common.enums;

/**
 * Time: 2022/7/11
 * Author: Dankejun
 * Description:
 */
public enum HttpCodeEnum {
    OK("0001", "success"),
    ERROR("0002", "failure"),
    NOT_OWNER("9093", "Permission denied,you are not owner"),
    OFFLINE("0309", "Home appliance off line"),
    IOT_ERROR_OFFLINE("1307","The appliance is offline"),
    IOT_ERROR_NOT_OWNED("1305", "The appliance is not owned by the user"),
    IOT_ERROR_NO_REPLY("1306", "The asyn reply does not exist");

    private final String code;
    private final String message;

    HttpCodeEnum(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return this.code;
    }
}

