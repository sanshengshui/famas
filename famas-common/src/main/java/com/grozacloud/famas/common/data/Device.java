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
public class Device extends BaseDO {

    private String customerId;

    private String type;

    private String name;

    private String label;

    private String tenantId;
}
