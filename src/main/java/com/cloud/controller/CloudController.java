package com.cloud.controller;

import com.cloud.common.pojo.dto.BaseRequestBodyDTO;
import com.cloud.common.pojo.dto.BaseResponseDTO;
import com.cloud.common.pojo.dto.BodyRequestDTO;
import com.cloud.common.pojo.dto.HeaderRequestDTO;
import com.cloud.service.CloudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public BaseResponseDTO getDeviceList(@RequestHeader HttpHeaders headers) {
        String Token = headers.getFirst("Token");
        String TokenType = headers.getFirst("TokenType");
        String SequenceID = headers.getFirst("SequenceID");
        String UPlatID = headers.getFirst("UPlatID");
        String CmdDir = headers.getFirst("CmdDir");
        String Timestamp = headers.getFirst("Timestamp");
        String VersionID = headers.getFirst("VersionID");
        HeaderRequestDTO requestDTO = new HeaderRequestDTO(Token, TokenType, SequenceID, UPlatID, CmdDir, Timestamp, VersionID);
        return cloudService.getDeviceList(requestDTO);
    }
    @PostMapping("/api/device/status")
    public BaseResponseDTO queryStatus(@RequestHeader HttpHeaders headers, @RequestBody BaseRequestBodyDTO body) {
        String Token = headers.getFirst("Token");
        String TokenType = headers.getFirst("TokenType");
        String SequenceID = headers.getFirst("SequenceID");
        String UPlatID = headers.getFirst("UPlatID");
        String CmdDir = headers.getFirst("CmdDir");
        String Timestamp = headers.getFirst("Timestamp");
        String VersionID = headers.getFirst("VersionID");
        HeaderRequestDTO requestDTO = new HeaderRequestDTO(Token, TokenType, SequenceID, UPlatID, CmdDir, Timestamp, VersionID);
        return cloudService.queryStatus(requestDTO, body);
    }

    @PostMapping("/api/device/operator")
    public BaseResponseDTO control(@RequestHeader HttpHeaders headers, @RequestBody BodyRequestDTO body) {
        String Token = headers.getFirst("Token");
        String TokenType = headers.getFirst("TokenType");
        String SequenceID = headers.getFirst("SequenceID");
        String UPlatID = headers.getFirst("UPlatID");
        String CmdDir = headers.getFirst("CmdDir");
        String Timestamp = headers.getFirst("Timestamp");
        String VersionID = headers.getFirst("VersionID");
        HeaderRequestDTO requestDTO = new HeaderRequestDTO(Token, TokenType, SequenceID, UPlatID, CmdDir, Timestamp, VersionID);
        return cloudService.control(requestDTO, body);

    }
}
