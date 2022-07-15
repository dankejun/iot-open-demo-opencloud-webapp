package com.cloud.controller;

import com.cloud.common.pojo.dto.BaseResponseDTO;
import com.cloud.common.pojo.dto.RequestDTO;
import com.cloud.service.CloudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Time: 2022/7/8
 * Author: Dankejun
 * Description:
 */
@RestController
@Slf4j
public class CloudController {
    @Autowired
    private CloudService cloudService;

    @PostMapping("/api/device/list")
    public BaseResponseDTO getDeviceList(@RequestBody RequestDTO requestDTO) {
        return cloudService.getDeviceList(requestDTO.getHeader());
    }
    @PostMapping("/api/device/status")
    public BaseResponseDTO queryStatus(@RequestBody RequestDTO requestDTO) {
        return cloudService.queryStatus(requestDTO.getHeader(), requestDTO.getBody());
    }

    @PostMapping("/api/device/operator")
    public BaseResponseDTO control(@RequestBody RequestDTO requestDTO) {
        return cloudService.control(requestDTO.getHeader(), requestDTO.getBody());
    }
}
