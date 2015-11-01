package com.memory.codegen.model;

/**
 * MySQL column model
 * 
 * @author memoryaxis@gmail.com
 */
public class ColumnModel {

    private String colName;

    private String colJavaName;

    private String colGetSetName;

    private boolean isPk = false;

    private String colDbType;

    private String colJavaType;

    private String colJdbcType;

    private long length = 0;

    private int precision = 0;

    private int scale = 0;

    private String displayDbType;

    private boolean isNotNull = false;

    private boolean isAutoGen = false;

    private String comment;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColJavaName() {
        return colJavaName;
    }

    public void setColJavaName(String colJavaName) {
        this.colJavaName = colJavaName;
    }

    public String getColGetSetName() {
        return colGetSetName;
    }

    public void setColGetSetName(String colGetSetName) {
        this.colGetSetName = colGetSetName;
    }

    public boolean getIsPk() {
        return isPk;
    }

    public void setIsPk(boolean isPk) {
        this.isPk = isPk;
    }

    public String getColDbType() {
        return colDbType;
    }

    public void setColDbType(String colDbType) {
        this.colDbType = colDbType;
    }

    public String getColJavaType() {
        return colJavaType;
    }

    public void setColJavaType(String colJavaType) {
        this.colJavaType = colJavaType;
    }

    public String getColJdbcType() {
        return colJdbcType;
    }

    public void setColJdbcType(String colJdbcType) {
        this.colJdbcType = colJdbcType;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getDisplayDbType() {
        return displayDbType;
    }

    public void setDisplayDbType(String displayDbType) {
        this.displayDbType = displayDbType;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public void setNotNull(boolean isNotNull) {
        this.isNotNull = isNotNull;
    }

    public boolean isAutoGen() {
        return isAutoGen;
    }

    public void setAutoGen(boolean isAutoGen) {
        this.isAutoGen = isAutoGen;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
