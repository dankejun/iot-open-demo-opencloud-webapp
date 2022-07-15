package com.cloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.constant.CloudConstant;
import com.cloud.common.enums.HttpCodeEnum;
import com.cloud.common.pojo.dto.BaseRequestBodyDTO;
import com.cloud.common.pojo.dto.BaseResponseDTO;
import com.cloud.common.pojo.dto.BodyRequestDTO;
import com.cloud.common.pojo.dto.HeaderRequestDTO;
import com.cloud.common.pojo.vo.CloudApplianceVo;
import com.cloud.common.pojo.vo.DeviceStatusVO;
import com.cloud.common.pojo.vo.QueryStatusVO;
import com.cloud.service.CloudService;
import com.cloud.util.RequestDateUtils;
import com.cloud.util.ResponseUtil;
import com.cloud.util.SignUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Time: 2022/7/8
 * Author: Dankejun
 * Description:
 */
@Service
@Slf4j
public class CloudServiceImpl implements CloudService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${iot.appId}")
    private String appId;
    @Value("${iot.baseUrl}")
    private String baseUrl;
    @Value("${iot.appKey}")
    private String appKey;


    @Override
    public BaseResponseDTO<CloudApplianceVo> getDeviceList(HeaderRequestDTO requestDTO) {
        log.info("通过IOT接口查询用户名下设备列表-请求iot 入参：Header:{}", JSON.toJSONString(requestDTO));
        BaseResponseDTO<CloudApplianceVo> cloudRetVO;
        try {
            //1.请求iot
            //组装参数
            String token = requestDTO.getToken();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            String uri = "/v1/open/device/list/get";
            params.add("clientId", appId);
            params.add("reqId", RequestDateUtils.genRequestDateStr());
            params.add("stamp", requestDTO.getTimestamp());
            params.add("sign", SignUtil.requestSign(uri, params, appKey));
            log.info("查询设备信息-请求iot 入参：{}", params);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplate.exchange(baseUrl + uri, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                String res = response.getBody();
                log.info("通过IOT接口查询用户名下设备列表-请求iot 出参：{}", res);
                JSONObject iotRetBody = JSON.parseObject(res);
                JSONArray applianceList = iotRetBody.getJSONArray("applianceList");
                ArrayList<CloudApplianceVo> deviceList = Lists.newArrayList();
                if (applianceList != null && !applianceList.isEmpty()) {
                    applianceList.forEach(item -> {
                        JSONObject parseObject = JSONObject.parseObject(item.toString());
                        if (parseObject.getString("type").equalsIgnoreCase("0xD9")) {
                            int length = parseObject.getString("applianceCode").length() + 16;
                            String applianceId = parseObject.getString("applianceCode");
                            String modelNo = parseObject.getString("type").substring(2) + "." + parseObject.getString("modelNumber");
                            String OID = CloudConstant.OID_PRE_STR + length + "." + "PK*" + modelNo + ".MAC*" + applianceId;
                            CloudApplianceVo applianceVo = CloudApplianceVo.builder().OID(OID).ModelID(modelNo)
                                    .DeviceID(applianceId).DeviceName(parseObject.getString("name")).NetMode("Wifi")
                                    .BigType(2).LittleType(8).build();
                            deviceList.add(applianceVo);
                        }
                    });
                }
                cloudRetVO = ResponseUtil.success(deviceList);
                log.info("通过IOT接口查询用户名下设备列表 出参：{}", cloudRetVO);
                return cloudRetVO;
            } else {
                log.error("通过IOT接口查询用户名下设备列表异常-请求iot 出参：{}", response);
                cloudRetVO = ResponseUtil.error();
                return cloudRetVO;
            }
        } catch (Exception e) {
            log.info("请求异常：{}", e.getMessage());
            cloudRetVO = ResponseUtil.error();
            return cloudRetVO;
        }
    }

    @Override
    public BaseResponseDTO queryStatus(HeaderRequestDTO header, BaseRequestBodyDTO body) {
        log.info("查询设备状态 入参：Header:{},Body:{}", JSON.toJSONString(header), JSON.toJSONString(body));
        BaseResponseDTO<QueryStatusVO> responseDTO;
        String token = header.getToken();
        String timeStamp = header.getTimestamp();
        QueryStatusVO queryStatusVO = new QueryStatusVO();
        DeviceStatusVO deviceStatusVO = new DeviceStatusVO();

        String OID = body.getOID();
        String applianceId = OID.substring(OID.indexOf("MAC*") + 4);

        try {
            String command1 = CloudConstant.D9_UP_LOCATION_DB;
            ResponseEntity<String> location1Response = iotQueryStatus(token, timeStamp, applianceId, command1);
            String location1 = location1Response.getBody();
            JSONObject location1Obj = JSON.parseObject(location1);
            JSONObject status1 = location1Obj.getJSONObject("status");
            deviceStatusVO.setPower(status1.getString(CloudConstant.STATUS_MAP.get("Power")).equals("on") ? 1 : 0);
            deviceStatusVO.setRun_Status_1(status1.getString(CloudConstant.STATUS_MAP.get("Run_Status_1")).equals("start") ? 1 : 0);
            deviceStatusVO.setLocation_1(1);
            deviceStatusVO.setTemperature_1(status1.getInteger(CloudConstant.STATUS_MAP.get("Temperature_1")));
            deviceStatusVO.setRemaining_Time_1(status1.getInteger(CloudConstant.STATUS_MAP.get("Remaining_Time_1")));
            deviceStatusVO.setWater_Level_1(status1.getInteger(CloudConstant.STATUS_MAP.get("Water_Level_1")));

            String command2 = CloudConstant.D9_DOWN_LOCATION_DB;
            ResponseEntity<String> location2Response = iotQueryStatus(token, timeStamp, applianceId, command2);
            String location2 = location2Response.getBody();
            JSONObject location2Obj = JSON.parseObject(location2);
            JSONObject status2 = location2Obj.getJSONObject("status");
            deviceStatusVO.setRun_Status_2(status2.getString(CloudConstant.STATUS_MAP.get("Run_Status_2")).equals("start") ? 1 : 0);
            deviceStatusVO.setLocation_2(2);
            deviceStatusVO.setTemperature_2(status2.getInteger(CloudConstant.STATUS_MAP.get("Temperature_2")));
            deviceStatusVO.setRemaining_Time_2(status2.getInteger(CloudConstant.STATUS_MAP.get("Remaining_Time_2")));
            deviceStatusVO.setWater_Level_2(status2.getInteger(CloudConstant.STATUS_MAP.get("Water_Level_2")));

            queryStatusVO.setOnlineStatus(1);
            queryStatusVO.setDeviceStatusParameter(deviceStatusVO);
            responseDTO = ResponseUtil.success(queryStatusVO);
            log.info("查询设备状态 出参：{}", responseDTO);
            return responseDTO;
        } catch (RestClientResponseException e) {
            JSONObject error = JSONObject.parseObject(e.getResponseBodyAsString());
            if (error.get("error").equals(HttpCodeEnum.IOT_ERROR_NOT_OWNED.getCode())) {
                responseDTO = ResponseUtil.notOwner();
                log.error("查询设备状态异常：{}", e.getMessage());
            } else if (error.get("error").equals(HttpCodeEnum.IOT_ERROR_OFFLINE.getCode())) {
                responseDTO = ResponseUtil.offline();
                log.error("查询设备状态异常：{}", e.getMessage());
            } else {
                responseDTO = ResponseUtil.error();
                log.error("查询设备状态异常：{}", e.getMessage());
            }
            return responseDTO;
        } catch (Exception e1) {
            responseDTO = ResponseUtil.error();
            log.info("查询设备状态异常：{}", e1.getMessage());
            return responseDTO;
        }
    }

    @Override
    public BaseResponseDTO control(HeaderRequestDTO requestDTO, BodyRequestDTO body) {
        BaseResponseDTO responseDTO;
        String token = requestDTO.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String OID = body.getOID();
        String applianceId = OID.substring(OID.indexOf("MAC*") + 4);

        String command = command(body);
        if (null == command || command.isEmpty()) {
            responseDTO = ResponseUtil.error();
            return responseDTO;
        }

        try {
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            String uri = "/v1/open/device/lua/control";
            params.add("clientId", appId);
            params.add("reqId", RequestDateUtils.genRequestDateStr());
            params.add("stamp", requestDTO.getTimestamp());
            params.add("applianceCode", applianceId);
            params.add("command", command);
            params.add("sign", SignUtil.requestSign(uri, params, appKey));
            log.info("控制设备-请求iot 入参：{}", params);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplate.exchange(baseUrl + uri, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                responseDTO = ResponseUtil.success();
            } else {
                responseDTO = ResponseUtil.error();
            }
            return responseDTO;
        } catch (RestClientResponseException e) {
            JSONObject error = JSONObject.parseObject(e.getResponseBodyAsString());
            if (error.get("error").equals(HttpCodeEnum.IOT_ERROR_NO_REPLY.getCode())) {
                responseDTO = ResponseUtil.success();
                log.info("设备控制出参：{}", responseDTO);
            } else {
                responseDTO = ResponseUtil.error();
                log.error("设备控制异常：{}", e.getMessage());
            }
            return responseDTO;
        } catch (Exception e1) {
            responseDTO = ResponseUtil.error();
            log.error("设备控制异常：{}", e1.getMessage());
            return responseDTO;
        }
    }

    private String command(BodyRequestDTO body) {
        List<String> nameSpace = body.getNameSpace();
        List<String> locations = nameSpace.stream().filter(item -> item.startsWith("Location_")).collect(Collectors.toList());
        if (locations.size() != 1) {
            return null;
        }
        Map<String, Object> command = new HashMap<>();
        Map<String, Object> parameter = body.getSetParameter();

        List<String> paramName;
        if (locations.get(0).equals("Location_1")) {
            //上筒
            paramName = nameSpace.stream().filter(item -> item.endsWith("1") || item.equals("Power")).collect(Collectors.toList());
            // command.put("bucket", "db");
        } else {
            //下筒
            paramName = nameSpace.stream().filter(item -> item.endsWith("2") || item.equals("Power")).collect(Collectors.toList());
            // command.put("bucket", "db");
        }
        command.put("bucket", "db");
        paramName.forEach(item -> {
            if (CloudConstant.CONTROL_MAP.get(item) != null) {
                if (item.equals("Power")) {
                    command.put(CloudConstant.CONTROL_MAP.get(item), (Integer) parameter.get(item) == 1 ? "on" : "off");
                } else if (item.equals("Run_Status_1") || item.equals("Run_Status_2")) {
                    command.put(CloudConstant.CONTROL_MAP.get(item), (Integer) parameter.get(item) == 1 ? "start" : "pause");
                } else {
                    command.put(CloudConstant.CONTROL_MAP.get(item), parameter.get(item));
                }
            }
        });
        JSONObject control = new JSONObject();
        control.put("control", command);
        JSONObject jsonObject = new JSONObject(control);
        return jsonObject.toJSONString();
    }

    private ResponseEntity<String> iotQueryStatus(String token, String timeStamp, String applianceId, String command) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        String uri = "/v1/open/device/status/lua/get";
        params.add("clientId", appId);
        params.add("reqId", RequestDateUtils.genRequestDateStr());
        params.add("stamp", timeStamp);
        params.add("applianceCode", applianceId);
        params.add("command", command);
        params.add("sign", SignUtil.requestSign(uri, params, appKey));
        log.info("查询设备信息-请求iot 入参：{}", params);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);

        return restTemplate.exchange(baseUrl + uri, HttpMethod.POST, requestEntity, String.class);
    }
}
