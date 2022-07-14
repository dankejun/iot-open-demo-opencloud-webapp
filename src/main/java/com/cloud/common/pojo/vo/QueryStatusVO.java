package com.cloud.common.pojo.vo;

import lombok.Data;

/**
 * Time: 2022/7/12
 * Author: Dankejun
 * Description:
 */
@Data
public class QueryStatusVO {
    private Integer OnlineStatus;
    private DeviceStatusVO DeviceStatusParameter;
}
