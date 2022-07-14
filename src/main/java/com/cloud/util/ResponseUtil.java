package com.cloud.util;

import com.cloud.common.enums.HttpCodeEnum;
import com.cloud.common.pojo.dto.BaseResponseDTO;

/**
 * Time: 2022/7/11
 * Author: Dankejun
 * Description:
 */
public class ResponseUtil {
    public ResponseUtil() {
    }

    public static BaseResponseDTO success() {
        return new BaseResponseDTO(HttpCodeEnum.OK.getCode(), HttpCodeEnum.OK.getMessage());
    }

    public static BaseResponseDTO successAndNoMsg() {
        return new BaseResponseDTO(HttpCodeEnum.OK.getCode(), "");
    }

    public static BaseResponseDTO success(Object data) {
        return new BaseResponseDTO(HttpCodeEnum.OK.getCode(), HttpCodeEnum.OK.getMessage(), data);
    }

    public static BaseResponseDTO successAndNoMsg(Object data) {
        return new BaseResponseDTO(HttpCodeEnum.OK.getCode(), "", data);
    }
    public static BaseResponseDTO error() {
        return new BaseResponseDTO(HttpCodeEnum.ERROR.getCode(), HttpCodeEnum.ERROR.getMessage());
    }

    public static BaseResponseDTO errorAndNoMsg() {
        return new BaseResponseDTO(HttpCodeEnum.ERROR.getCode(), "");
    }

    public static BaseResponseDTO error(Object data) {
        return new BaseResponseDTO(HttpCodeEnum.ERROR.getCode(), HttpCodeEnum.ERROR.getMessage(), data);
    }

    public static BaseResponseDTO errorAndNoMsg(Object data) {
        return new BaseResponseDTO(HttpCodeEnum.ERROR.getCode(), "", data);
    }
    public static BaseResponseDTO notOwner() {
        return new BaseResponseDTO(HttpCodeEnum.NOT_OWNER.getCode(), HttpCodeEnum.NOT_OWNER.getMessage());
    }
    public static BaseResponseDTO offline() {
        return new BaseResponseDTO(HttpCodeEnum.OFFLINE.getCode(), HttpCodeEnum.OFFLINE.getMessage());
    }
}
