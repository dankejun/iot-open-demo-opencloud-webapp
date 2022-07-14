package com.cloud.common.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Time: 2022/7/8
 * Author: Dankejun
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BodyRequestDTO extends BaseRequestBodyDTO{
    @JsonProperty(value = "NameSpace")
    private List<String> NameSpace;
    @JsonProperty(value = "SetParameter")
    private Map<String,Object> SetParameter;
}
