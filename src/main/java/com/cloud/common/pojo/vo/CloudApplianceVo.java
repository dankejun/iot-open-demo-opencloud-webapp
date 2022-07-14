package com.cloud.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Time: 2022/7/8
 * Author: Dankejun
 * Description:
 */
@Data
@Builder
public class CloudApplianceVo {
    @JsonProperty(value = "OID")
    private String OID;
    private String ModelID;
    private String DeviceID;
    private String DeviceName;
    private String NetMode;
    private Integer LittleType;
    private Integer BigType;
}
