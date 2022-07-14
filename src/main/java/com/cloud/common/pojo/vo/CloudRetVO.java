package com.cloud.common.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Time: 2022/7/8
 * Author: Dankejun
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudRetVO<T> {
    private String RetCode;
    private String RetInfo;
    private List<T> RetBody;
}
