package com.bluewind.boot.module.tool.dataset.generalexport.mapper;

import com.bluewind.boot.module.tool.dataset.generalexport.entity.Dataset;
import com.bluewind.boot.module.tool.dataset.generalexport.entity.DatasetLine;
import com.bluewind.boot.module.tool.dataset.generalexport.entity.DatasetLineSqlColumn;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2022-11-24 21:49
 * @description 通用数据集工具持久层
 **/
@Repository
@Mapper
public interface GeneralExportMapper {
    /**
     * 根据dataId查询数据集配置信息
     * @param dataId 数据集id
     * @return
     */
    Dataset getDatasetByDataId(String dataId);


    /**
     * 根据dataId查询数据集明细表信息
     * @param dataId 数据集id
     * @return
     */
    List<DatasetLine> getDatasetLineByDataId(String dataId);


    /**
     * 根据dataId和lineNo查询sql列配置信息
     * @param datasetLine 数据集行表对象
     * @return
     */
    List<DatasetLineSqlColumn> getDatasetLineSqlColumnByDataIdAndLineNo(DatasetLine datasetLine);


    /**
     * 通用查询执行
     * @param sql sql语句
     * @return
     */
    List<Map<String, Object>> executeSQL(String sql);
}
