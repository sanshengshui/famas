package com.grozacloud.famas.server.dao;


import com.grozacloud.famas.common.data.Tenant;
import com.grozacloud.famas.common.utils.SnowFlakeUtil;
import com.grozacloud.famas.dao.sql.tenant.TenantDao;
import com.grozacloud.famas.server.GrozaServerApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author james mu
 * @date 2020/1/17 16:24
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrozaServerApplication.class)
public class TenantMapperTest {

    @Resource
    private TenantDao tenantDao;

    @Test
    public void insertTenant() {
        Tenant tenant = new Tenant();
        tenant.setAddress("浙江杭州西湖区");
        tenant.setEmail("lovewsic@gmail.com");
        tenant.setPhone("18036142209");
        tenant.setTitle("大搜车");
        tenant.setId(SnowFlakeUtil.getId());
        tenantDao.insertTenant(tenant);
    }
}
