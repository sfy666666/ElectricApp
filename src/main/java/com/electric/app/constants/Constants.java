package com.electric.app.constants;


import com.electric.app.utils.LogUtils;

/**
 *
 */

public class Constants {
    /***************debug 开关**********/
    public static int DEBUGLEVEL = LogUtils.LEVEL_ALL;

    public static final int REQUEST_SUCCESS = 1;
    public static final int REQUEST_FAIL = 0;

    /****************APP KEY*********/
    public static final String IDENTITY = "IDENTITY";//用户身份
    public static final int IDENTITY_CONSUMER = 1;//消费者
    public static final int IDENTITY_DISTRIBUTOR = 2;//经销商
    public static final String FRAGMENT_POSITION = "FRAGMENT_POSITION";//首页按钮
    public static final String INITIAL_INSTALLATION = " Initial_Installation";//初次安装

    /*********preference KEY *************/
    public static final String USER_INFO = "user_info";
    public static final String COMPANY_INFO = "company_info";
    public static final String LOCATION = "location";
    public static final String MOBILE = "mobile";
    public static final String CLIENTID = "clientId";
    public static final String USER_ID = "userId";
    public static final String EQUIPMENT_TYPE = "equipmentType";
    public static final String BIND_PRODUCT_ENTITY = "BindProductEntity";
    public static final String NOTIFY_MESSAGE = "notify_message";//通知提醒开关
    public static final String NOTIFY_VOICE = "notify_voice";//声音开关
    public static final String NOTIFY_SHOCK = "notify_shock";//震动开关

    //蚂蚁兴能BMS配置    String mac = "3C:A5:08:0B:0C:E3";
    public static final String ALIAS = "a";
    public static final String INSTRUCTIONS = "DBDB00000000";
    public static final String SERVICE_UUID = "0000ffe0-0000-1000-8000-00805f9b34fb";
    public static final String CHARACTERISTIC_UUID = "0000ffe1-0000-1000-8000-00805f9b34fb";

    /**********intent  KEY **************/
    public static String SCAN_RESULT = "SCAN_RESULT";
    public static String SN_CODE= "SN_CODE";
    public static String SEARCH_TYPE= "SEARCH_TYPE";


}
