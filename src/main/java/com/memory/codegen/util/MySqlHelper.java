package com.memory.codegen.util;

import com.memory.codegen.constant.SQL;
import com.memory.codegen.model.ColumnModel;
import com.memory.codegen.model.TableModel;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * MySql utility
 *
 * get table or column information
 *
 * get javaType or jdbcType by dbType
 *
 * @author memoryaxis@gmail.com
 */
public class MySqlHelper {

    private Connection conn;

    private DriverManagerDataSource dataSource;

    private static MySqlHelper mySqlHelper;

    public static synchronized MySqlHelper getInstance() {
        return mySqlHelper == null ? mySqlHelper = new MySqlHelper() : mySqlHelper;
    }

    public synchronized void init() {
        try {
            dataSource = new DriverManagerDataSource();

            dataSource.setDriverClassName(PropertiesUtil.getStringValue("jdbc.base.driverClassName"));
            dataSource.setUrl(PropertiesUtil.getStringValue("jdbc.base.url"));
            dataSource.setUsername(PropertiesUtil.getStringValue("jdbc.base.username"));
            dataSource.setPassword(PropertiesUtil.getStringValue("jdbc.base.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized Connection getConnection() {
        try {
            if (conn == null) {
                conn = dataSource.getConnection();
            }
            conn.setAutoCommit(true);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TableModel getTableInfo(final String tableName) throws Exception {
        TableModel table = new TableModel();
        table.setTableName(tableName);
        List<ColumnModel> columnList = new ArrayList<ColumnModel>();

        Statement statement = getConnection().createStatement();
        ResultSet rs = statement.executeQuery(String.format(SQL.SQL_GET_COLUMNS, tableName));
        int colMaxLen = 0;
        while (rs.next()) {
            ColumnModel column = new ColumnModel();

            String dataType = rs.getString("data_type");
            long characterLength = rs.getLong("character_octet_length");
            int precision = rs.getInt("numeric_precision");
            int scale = rs.getInt("numeric_scale");

            column.setColName(rs.getString("column_name"));
            column.setColJavaName(getJavaName(rs.getString("column_name")));
            column.setColGetSetName(getGetSetName(rs.getString("column_name")));
            column.setIsPk(rs.getString("column_key").equals("PRI"));
            column.setColDbType(dataType);
            column.setColJavaType(getJavaType(dataType, precision, scale));
            column.setColJdbcType(getJdbcType(dataType));
            column.setLength(characterLength);
            column.setPrecision(precision);
            column.setScale(scale);
            column.setDisplayDbType(getDisplayDbType(dataType, characterLength, precision, scale));
            column.setNotNull(!rs.getString("is_nullable").equals("YES"));
            column.setAutoGen(rs.getString("extra").equals("auto_increment"));
            column.setComment(rs.getString("column_comment").split("\n")[0]);

            int colLen = column.getColName().length();
            colMaxLen = colMaxLen < colLen ? colLen : colMaxLen;

            columnList.add(column);
        }

        statement = getConnection().createStatement();
        rs = statement.executeQuery(String.format(SQL.SQL_GET_COMMENT, tableName));
        String tableComment = "";
        while (rs.next()) {
            tableComment = rs.getString("table_comment");
        }

        rs.close();
        statement.close();

        table.setColumnList(columnList);
        table.setTabComment(tableComment);
        table.setColMaxLen(colMaxLen);
        return table;
    }

    private String getGetSetName(String columnName) {
        String[] words = columnName.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
        }
        return sb.toString();
    }

    private String getJavaName(String columnName) {
        return getGetSetName(columnName).substring(0, 1).toLowerCase()
                + getGetSetName(columnName).substring(1);
    }

    private String getJavaType(String dbType, int precision, int scale) {
        if (dbType.equals("bigint")) return "Long";
        if (dbType.equals("int")) return "Integer";
        if (dbType.equals("tinyint") || dbType.equals("smallint")) return "Short";
        if (dbType.endsWith("char") || dbType.endsWith("text")) return "String";
        if (dbType.equals("double")) return "Double";
        if (dbType.equals("float")) return "Float";
        if (dbType.endsWith("blob")) return "byte[]";
        if (dbType.equals("decimal")) {
            if (scale == 0) {
                if (precision <= 10)
                    return "Integer";
                else
                    return "Long";
            } else {
                return "Double";
            }
        }
        if (dbType.startsWith("date") || dbType.startsWith("time")) {
            return "java.util.Date";
        }
        return dbType;
    }

    private String getJdbcType(String dbType) {
        if (dbType.contains("int") || dbType.equals("float") || dbType.equals("decimal"))
            return "NUMERIC";
        if (dbType.contains("lob") || dbType.contains("text") || dbType.contains("char"))
            return "VARCHAR";
        if (dbType.equals("timestamp")) return "TIMESTAMP";
        if (dbType.contains("date") || dbType.contains("time")) return "DATE";
        return getJavaType(dbType, 0, 0).toUpperCase();
    }

    private String getDisplayDbType(String dbtype, long character_length, int precision, int scale) {
        if (dbtype.equals("varchar")) return "varchar(" + character_length + ")";
        if (dbtype.equals("decimal")) return "decimal(" + precision + "," + scale + ")";
        return dbtype;
    }
}
