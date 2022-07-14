package com.cloud.common.pojo.vo;

import lombok.Data;

/**
 * Time: 2022/7/12
 * Author: Dankejun
 * Description:
 */
@Data
public class DeviceStatusVO {
    private Integer Power;
    private Integer Run_Status_1;
    private Integer Location_1;
    private Integer Temperature_1;
    private Integer Remaining_Time_1;
    private Integer Water_Level_1 = 0;
    private Integer Run_Status_2;
    private Integer Location_2;
    private Integer Temperature_2;
    private Integer Remaining_Time_2;
    private Integer Water_Level_2;
}
