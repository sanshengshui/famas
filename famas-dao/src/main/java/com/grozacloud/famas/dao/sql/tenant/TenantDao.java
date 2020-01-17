package com.grozacloud.famas.dao.sql.tenant;

import com.grozacloud.famas.common.data.Tenant;
import org.springframework.stereotype.Repository;

/**
 * @author james mu
 * @date 2020/1/17 16:04
 */
@Repository
public interface TenantDao {

    int insertTenant(Tenant tenant);
}
