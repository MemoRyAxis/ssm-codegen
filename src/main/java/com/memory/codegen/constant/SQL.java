package com.memory.codegen.constant;

/**
 * SQLs
 * 
 * @author memoryaxis@gmail.com
 */
public class SQL {

    /**
     * get columns by table name
     */
    public static final String SQL_GET_COLUMNS =
            "SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND TABLE_NAME='%s'";

    /**
     * get table comment
     */
    public static final String SQL_GET_COMMENT =
            "SELECT table_comment FROM information_schema.tables t WHERE t.table_schema=DATABASE() AND TABLE_NAME='%s'";

}
