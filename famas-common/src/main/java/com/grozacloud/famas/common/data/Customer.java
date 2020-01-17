package com.grozacloud.famas.common.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author james mu
 * @date 2020/1/10 11:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class Customer extends BaseDO {

    private String title;

    private String phone;

    private String email;

    private String address;

    private String tenantId;

}
