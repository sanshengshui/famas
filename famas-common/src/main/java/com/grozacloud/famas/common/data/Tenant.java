package com.grozacloud.famas.common.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author james mu
 * @date 2020/1/10 10:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class Tenant extends BaseDO {

    private String title;

    private String phone;

    private String email;

    private String address;

}
