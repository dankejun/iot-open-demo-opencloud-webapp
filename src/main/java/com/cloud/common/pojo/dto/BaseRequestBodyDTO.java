package com.cloud.common.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Time: 2022/7/11
 * Author: Dankejun
 * Description:
 */
@Data
public class BaseRequestBodyDTO {
    @JsonProperty(value = "OID")
    private String OID;
}
