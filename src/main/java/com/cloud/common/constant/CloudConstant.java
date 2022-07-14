package com.cloud.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Time: 2022/7/11
 * Author: Dankejun
 * Description:
 */
public class CloudConstant {
    public static final String OID_PRE_STR = "1.2.156.20011.6972848671234.01.2.8.";
    public static final String D9_UP_LOCATION_DC = "{\"query\":{\"dc_location\":1,\"query_type\":\"dc\"}}";
    public static final String D9_UP_LOCATION_DB = "{\"query\":{\"db_location\":1,\"query_type\":\"db\"}}";
    public static final String D9_DOWN_LOCATION_DB = "{\"query\":{\"db_location\":2,\"query_type\":\"db\"}}";

    public static final Map<String, String> STATUS_MAP = new HashMap<>();
    public static final Map<String, String> CONTROL_MAP = new HashMap<>();

    static {
        STATUS_MAP.put("Power", "db_power");
        STATUS_MAP.put("Run_Status_1", "db_running_status");
        STATUS_MAP.put("Location_1", "db_location");
        STATUS_MAP.put("Temperature_1", "db_temperature");
        STATUS_MAP.put("Remaining_Time_1", "db_remain_time");
        STATUS_MAP.put("Water_Level_1", "db_water_level");

        STATUS_MAP.put("Run_Status_2", "db_running_status");
        STATUS_MAP.put("Location_2", "db_location");
        STATUS_MAP.put("Temperature_2", "db_temperature");
        STATUS_MAP.put("Remaining_Time_2", "db_remain_time");
        STATUS_MAP.put("Water_Level_2", "db_water_level");
    }
    static {
        CONTROL_MAP.put("Power", "db_power");
        CONTROL_MAP.put("Run_Status_1", "db_control_status");
        CONTROL_MAP.put("Location_1", "db_location");

        CONTROL_MAP.put("Run_Status_2", "db_control_status");
        CONTROL_MAP.put("Location_2", "db_location");
    }
}
