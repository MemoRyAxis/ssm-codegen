package com.memory.codegen.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MySQL table model
 * 
 * @author memoryaxis@gmail.com
 */
public class TableModel {

    private String tableName;

    private String tabComment;

    private String foreignKey = "";

    private Map<String, String> variables = new HashMap<String, String>();

    private List<ColumnModel> columnList = new ArrayList<ColumnModel>();

    private int colMaxLen;

    private List<TableModel> subTableList = new ArrayList<TableModel>();

    private boolean sub;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTabComment() {
        return tabComment;
    }

    public void setTabComment(String tabComment) {
        this.tabComment = tabComment;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public List<ColumnModel> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ColumnModel> columnList) {
        this.columnList = columnList;
    }

    public List<TableModel> getSubTableList() {
        return subTableList;
    }

    public void setSubTableList(List<TableModel> subTableList) {
        this.subTableList = subTableList;
    }

    public boolean isSub() {
        return sub;
    }

    public void setSub(boolean sub) {
        this.sub = sub;
    }

    public int getColMaxLen() {
        return colMaxLen;
    }

    public void setColMaxLen(int colMaxLen) {
        this.colMaxLen = colMaxLen;
    }
}
