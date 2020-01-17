package com.grozacloud.famas.common.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author james mu
 * @date 2020/1/10 11:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class User extends BaseDO {

    private String authority;

    private String customerId;

    private String email;

    private String name;

    private String tenantId;
}
