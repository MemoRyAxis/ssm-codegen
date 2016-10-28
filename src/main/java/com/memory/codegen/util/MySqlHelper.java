package com.memory.codegen.util;

import com.memory.codegen.constant.MySQLTypesMapping;
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
 * <p>
 * get table or column information
 * <p>
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

    public synchronized void init(String driver, String url, String username, String password) {
        try {
            dataSource = new DriverManagerDataSource();

            dataSource.setDriverClassName(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
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
            String columnType = rs.getString("column_type");
            long characterLength = rs.getLong("character_octet_length");
            int precision = rs.getInt("numeric_precision");
            int scale = rs.getInt("numeric_scale");

            MySQLTypesMapping mySQLTypesMapping = MySQLTypesMapping.fromDbType(dataType);

            column.setColName(rs.getString("column_name"));
            column.setColJavaName(getJavaName(rs.getString("column_name")));
            column.setColGetSetName(getGetSetName(rs.getString("column_name")));
            column.setIsPk(rs.getString("column_key").equals("PRI"));
            column.setColDbType(dataType);
            column.setColJavaType(mySQLTypesMapping.getJavaType());
            column.setColJdbcType(mySQLTypesMapping.getJdbcType());
            column.setLength(characterLength);
            column.setPrecision(precision);
            column.setScale(scale);
            column.setDisplayDbType(columnType);
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

}
