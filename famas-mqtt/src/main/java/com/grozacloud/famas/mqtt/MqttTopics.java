package com.grozacloud.famas.mqtt;

/**
 * @author james mu
 * @date 2020/1/19 10:21
 */
public class MqttTopics {

    public static final String BASE_DEVICE_API_TOPIC = "v1/devices/me";
    public static final String DEVICE_RPC_RESPONSE_TOPIC = BASE_DEVICE_API_TOPIC + "/rpc/response/";
    public static final String DEVICE_RPC_RESPONSE_SUB_TOPIC = DEVICE_RPC_RESPONSE_TOPIC + "+";
    public static final String DEVICE_RPC_REQUEST_TOPIC = BASE_DEVICE_API_TOPIC + "/rpc/request/";
    public static final String DEVICE_RPC_REQUEST_SUB_TOPIC = DEVICE_RPC_REQUEST_TOPIC + "+";
    public static final String DEVICE_ATTRIBUTES_RESPONSE_TOPIC_PREFIX = BASE_DEVICE_API_TOPIC + "/attributes/response/";
    public static final String DEVICE_ATTRIBUTES_RESPONSES_TOPIC = DEVICE_ATTRIBUTES_RESPONSE_TOPIC_PREFIX + "+";
    public static final String DEVICE_ATTRIBUTES_REQUEST_TOPIC_PREFIX = BASE_DEVICE_API_TOPIC + "/attributes/request/";
    public static final String DEVICE_TELEMETRY_TOPIC = BASE_DEVICE_API_TOPIC + "/telemetry";
    public static final String DEVICE_CLAIM_TOPIC = BASE_DEVICE_API_TOPIC + "/claim";
    public static final String DEVICE_ATTRIBUTES_TOPIC = BASE_DEVICE_API_TOPIC + "/attributes";

    public static final String BASE_GATEWAY_API_TOPIC = "v1/gateway";
    public static final String GATEWAY_CONNECT_TOPIC = BASE_GATEWAY_API_TOPIC + "/connect";
    public static final String GATEWAY_DISCONNECT_TOPIC = BASE_GATEWAY_API_TOPIC + "/disconnect";
    public static final String GATEWAY_ATTRIBUTES_TOPIC = BASE_GATEWAY_API_TOPIC + "/attributes";
    public static final String GATEWAY_TELEMETRY_TOPIC = BASE_GATEWAY_API_TOPIC + "/telemetry";
    public static final String GATEWAY_CLAIM_TOPIC = BASE_GATEWAY_API_TOPIC + "/claim";
    public static final String GATEWAY_RPC_TOPIC = BASE_GATEWAY_API_TOPIC + "/rpc";
    public static final String GATEWAY_ATTRIBUTES_REQUEST_TOPIC = BASE_GATEWAY_API_TOPIC + "/attributes/request";
    public static final String GATEWAY_ATTRIBUTES_RESPONSE_TOPIC = BASE_GATEWAY_API_TOPIC + "/attributes/response";


    private MqttTopics() {

    }
}
