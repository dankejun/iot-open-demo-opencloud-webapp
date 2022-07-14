package com.cloud.common.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Time: 2022/7/8
 * Author: Dankejun
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeaderRequestDTO {
    private String Token;
    private String TokenType;
    private String SequenceID;
    private String UPlatID;
    private String CmdDir;
    private String Timestamp;
    private String VersionID;
}
