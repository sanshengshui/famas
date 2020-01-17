package com.grozacloud.famas.common.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author james mu
 * @date 2020/1/10 11:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class DeviceCredentials extends BaseDO {

    private String credentialsId;

    private String credentialsType;

    private String credentialsValue;

    private String deviceId;
}
