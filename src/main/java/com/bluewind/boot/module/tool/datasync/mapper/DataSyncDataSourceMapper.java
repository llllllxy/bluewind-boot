package com.bluewind.boot.module.tool.datasync.mapper;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2022-08-02 16:52
 * @description
 **/
@Repository
public interface DataSyncDataSourceMapper {

    Map<String, Object> getDataSource(String dataSourceId);

}
