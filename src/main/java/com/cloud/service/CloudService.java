package com.cloud.service;

import com.cloud.common.pojo.dto.BaseRequestBodyDTO;
import com.cloud.common.pojo.dto.BaseResponseDTO;
import com.cloud.common.pojo.dto.BodyRequestDTO;
import com.cloud.common.pojo.dto.HeaderRequestDTO;

/**
 * Time: 2022/7/8
 * Author: Dankejun
 * Description:
 */
public interface CloudService {
    BaseResponseDTO getDeviceList(HeaderRequestDTO requestDTO);

    BaseResponseDTO queryStatus(HeaderRequestDTO header, BaseRequestBodyDTO body);

    BaseResponseDTO control(HeaderRequestDTO requestDTO, BodyRequestDTO body);
}
