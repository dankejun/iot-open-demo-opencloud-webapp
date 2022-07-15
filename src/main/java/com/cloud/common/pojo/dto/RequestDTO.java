package com.cloud.common.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Time: 2022/7/15
 * Author: Dankejun
 * Description:
 */
@Data
public class RequestDTO {
    @JsonProperty(value = "Header")
    private HeaderRequestDTO header;
    @JsonProperty(value = "Body")
    private BodyRequestDTO body;
}
