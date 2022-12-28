package com.bluewind.boot.module.tool.dataset.generalexport.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuxingyu01
 * @date 2022-11-24 22:04
 * @description
 **/
public class DatasetLineSqlColumn implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 更新时间
     * */
    private Date updateTime;

    /**
     * 创建时间
     * */
    private Date createTime;

    /**
     * 数据集id
     */
    private String dataId;

    /**
     * 子数据行号
     */
    private Integer lineNo;

    /**
     *  列编流水号(使用雪花id即可)
     */
    private String colId;

    /**
     * 列编码
     */
    private String colKey;

    /**
     * 列名称
     */
    private String colName;

    /**
     * 顺序
     */
    private Integer seq;

    /**
     * 列数据类型 0:文本;1:数字
     */
    private String colDataType;

    /**
     *  数字类型保留小数位数,如果无需保留小数，设置成负数或者置空
     */
    private Integer decimalCnt;

    /**
     * 是否是枚举编码
     */
    private String isDict;

    /**
     * 枚举信息
     */
    private String dictInfo;

    /**
     * 是否是权限值编码
     */
    private String isOrgan;

    /**
     * 数据权限信息
     */
    private String organInfo;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public String getColId() {
        return colId;
    }

    public void setColId(String colId) {
        this.colId = colId;
    }

    public String getColKey() {
        return colKey;
    }

    public void setColKey(String colKey) {
        this.colKey = colKey;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getColDataType() {
        return colDataType;
    }

    public void setColDataType(String colDataType) {
        this.colDataType = colDataType;
    }

    public Integer getDecimalCnt() {
        return decimalCnt;
    }

    public void setDecimalCnt(Integer decimalCnt) {
        this.decimalCnt = decimalCnt;
    }

    public String getIsDict() {
        return isDict;
    }

    public void setIsDict(String isDict) {
        this.isDict = isDict;
    }

    public String getDictInfo() {
        return dictInfo;
    }

    public void setDictInfo(String dictInfo) {
        this.dictInfo = dictInfo;
    }

    public String getIsOrgan() {
        return isOrgan;
    }

    public void setIsOrgan(String isOrgan) {
        this.isOrgan = isOrgan;
    }

    public String getOrganInfo() {
        return organInfo;
    }

    public void setOrganInfo(String organInfo) {
        this.organInfo = organInfo;
    }

    @Override
    public String toString() {
        return "DatasetLineSqlColumn{" +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", dataId='" + dataId + '\'' +
                ", lineNo=" + lineNo +
                ", colId='" + colId + '\'' +
                ", colKey='" + colKey + '\'' +
                ", colName='" + colName + '\'' +
                ", seq=" + seq +
                ", colDataType='" + colDataType + '\'' +
                ", decimalCnt=" + decimalCnt +
                ", isDict='" + isDict + '\'' +
                ", dictInfo='" + dictInfo + '\'' +
                ", isOrgan='" + isOrgan + '\'' +
                ", organInfo='" + organInfo + '\'' +
                '}';
    }
}
