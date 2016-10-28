package com.memory.codegen.constant;

/**
 * Author: MemoRyAxis
 * Date: 16-10-27
 * Time: 下午7:17
 * <p>
 * http://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-type-conversions.html
 */
public enum MySQLTypesMapping {

    BIT("BIT", "BIT", "byte[]"),
    TINYINT("TINYINT", "TINYINT", "Integer"),
    BOOL("BOOL", "TINYINT", "boolean"),
    BOOLEAN("BOOLEAN", "TINYINT", "boolean"),
    SMALLINT("SMALLINT", "SMALLINT", "Integer"),
    MEDIUMINT("MEDIUMINT", "INTEGER", "Integer"),
    INT("INT", "INTEGER", "Integer"), // ifUNSIGNEDjava.lang.Long"),
    INTEGER("INTEGER", "INTEGER", "Integer"), // ifUNSIGNEDjava.lang.Long"),
    BIGINT("BIGINT", "BIGINT", "Long"), // ifUNSIGNEDjava.math.BigInteger"),
    FLOAT("FLOAT", "FLOAT", "java.math.BigDecimal"), // "Float"),
    DOUBLE("DOUBLE", "DOUBLE", "java.math.BigDecimal"), // "Double"),
    DECIMAL("DECIMAL", "DECIMAL", "java.math.BigDecimal"),
    DATE("DATE", "DATE", "java.util.Date"),
    DATETIME("DATETIME", "DATE", "java.util.Date"),
    TIMESTAMP("TIMESTAMP", "TIMESTAMP", "java.util.Date"),
    TIME("TIME", "TIME", "java.util.Date"),
    CHAR("CHAR", "CHAR", "String"),
    VARCHAR("VARCHAR", "VARCHAR", "String"),
    BINARY("BINARY", "BINARY", "byte[]"),
    VARBINARY("VARBINARY", "VARBINARY", "byte[]"),
    TINYBLOB("TINYBLOB", "BLOB", "byte[]"),
    TINYTEXT("TINYTEXT", "VARCHAR", "String"),
    BLOB("BLOB", "BLOB", "byte[]"),
    TEXT("TEXT", "VARCHAR", "String"),
    MEDIUMBLOB("MEDIUMBLOB", "BLOB", "byte[]"),
    MEDIUMTEXT("MEDIUMTEXT", "VARCHAR", "String"),
    LONGBLOB("LONGBLOB", "BLOB", "byte[]"),
    LONGTEXT("LONGTEXT", "VARCHAR", "String"),
    ENUM("ENUM", "CHAR", "String"),
    SET("SET", "CHAR", "String"),;

    String dbType;
    String jdbcType;
    String javaType;

    MySQLTypesMapping(String dbType, String jdbcType, String javaType) {
        this.dbType = dbType;
        this.jdbcType = jdbcType;
        this.javaType = javaType;
    }

    public String getDbType() {
        return dbType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public static MySQLTypesMapping fromDbType(String dbType) {
        if (dbType == null || dbType.length() < 1)
            return null;
        for (MySQLTypesMapping type : values())
            if (type.getDbType().toUpperCase().equals(dbType.toUpperCase()))
                return type;
        return null;
    }

    public static void main(String[] args) {

    }

}
