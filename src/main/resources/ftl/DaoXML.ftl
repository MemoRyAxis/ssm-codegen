<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mainPackage}${subPackage}po.${typeName}">
    <#assign colLen=table.colMaxLen />
    <#assign colLen2=table.colMaxLen + 5/>

    <resultMap id="${typeName}" type="${mainPackage}${subPackage}po.${typeName}">
    <#list table.columnList as col>
    <#if (col.isPk)>
        <id     column=${ws_(col.colName, colLen, 21)} property=${ws_(col.colJavaName, colLen, 21)} jdbcType="${col.colJdbcType}" />
    <#else>
        <result column=${ws_(col.colName, colLen, 21)} property=${ws_(col.colJavaName, colLen, 21)} jdbcType="${col.colJdbcType}" />
    </#if>
    </#list>
    </resultMap>

    <sql id="columns">
        <#list table.columnList as col>${col.colName}<#if col_has_next>, </#if></#list>
    </sql>

    <sql id="dynamicWhere">
        <where>
            <#list table.columnList as col>
            <#if col.colJavaType=="String">
            <if test=${ws_(col.colJavaName, colLen2, 22)} != null"> AND ${ws_(col.colName, colLen, 20)} ${ws_("LIKE", 5, 20)} <#noparse>#{</#noparse>${col.colJavaName}} </if>
            <#elseif col.colJavaType=="java.util.Date">
            <if test=${ws_(col.colJavaName, colLen2, 22)} != null"> AND ${ws_(col.colName, colLen, 20)} ${ws_("=", 5, 20)} <#noparse>#{</#noparse>${col.colJavaName}, jdbcType=DATE} </if>
            <if test=${ws_("start" + col.colGetSetName, colLen2, 22)} != null"> AND ${ws_(col.colName, colLen, 20)} ${ws_("&gt;=", 5, 20)} <#noparse>#{</#noparse>${col.colJavaName}, jdbcType=DATE} </if>
            <if test=${ws_("end" + col.colGetSetName, colLen2, 22)} != null"> AND ${ws_(col.colName, colLen, 20)} ${ws_("&lt;=", 5, 20)} <#noparse>#{</#noparse>${col.colJavaName}, jdbcType=DATE} </if>
            <#else>
            <if test=${ws_(col.colJavaName, colLen2, 22)} != null"> AND ${ws_(col.colName, colLen, 20)} ${ws_("=", 5, 20)} <#noparse>#{</#noparse>${col.colJavaName}} </if>
            </#if>
            </#list>
        </where>
    </sql>

    <insert id="add" parameterType="${mainPackage}${subPackage}po.${typeName}">
        INSERT INTO
            ${table.tableName}
        (<trim suffixOverrides=",">
            <#list table.columnList as col>
            <#if (!col.isPk)>
            <if test=${ws_(col.colJavaName, colLen, 22)} != null"> ${col.colName}, </if>
            </#if>
            </#list>
        </trim>)
        VALUES
        (<trim suffixOverrides=",">
            <#list table.columnList as col>
            <#if (!col.isPk)>
            <if test=${ws_(col.colJavaName, colLen, 22)} != null"> <#noparse>#{</#noparse>${col.colJavaName}, jdbcType=${col.colJdbcType}<#noparse>}</#noparse>, </if>
            </#if>
            </#list>
        </trim>)
        <#if (pkCol)??>
        <selectKey resultType="int" keyProperty="${pkCol.colName}" order="AFTER">
            SELECT LAST_INSERT_ID() AS ${pkCol.colName}
        </selectKey>
        </#if>
    </insert>

    <insert id="addAll" parameterType="${mainPackage}${subPackage}po.${typeName}">
        INSERT INTO 
            ${table.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list table.columnList as col>
            <#if (!col.isPk)>
            ${col.colName},
            </#if>
            </#list>
        </trim>
        VALUES 
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list table.columnList as col>
            <#if (!col.isPk)>
            <#noparse>#{</#noparse>${col.colJavaName}, jdbcType=${col.colJdbcType}<#noparse>},</#noparse>
            </#if>
            </#list>
        </trim>
        <#if (pkCol)??>
        <selectKey resultType="int" keyProperty="${pkCol.colName}" order="AFTER">
            SELECT LAST_INSERT_ID() AS ${pkCol.colName}
        </selectKey>
        </#if>
    </insert>

    <#if (pkCol)??>
    <delete id="delById" parameterType="java.lang.Long">
        DELETE FROM 
            ${table.tableName}
        WHERE
            ${pkCol.colName} = <#noparse>#{</#noparse>${pkCol.colJavaName}<#noparse>}</#noparse>
    </delete>
    </#if>

    <#if (pkCol)??>
    <update id="update" parameterType="${mainPackage}${subPackage}po.${typeName}">
        UPDATE 
            ${table.tableName} 
        <trim prefix="SET" suffixOverrides=",">
            <#list table.columnList as col>
            <#if (!col.isPk)>
            <if test=${ws_(col.colJavaName, colLen, 22)} != null"> ${ws_(col.colName, colLen, 20)} = <#noparse>#{</#noparse>${col.colJavaName}, jdbcType=${col.colJdbcType}<#noparse>}</#noparse>, </if>
            <#else>
            ${pkCol.colName} = <#noparse>#{</#noparse>${pkCol.colJavaName}<#noparse>}</#noparse>,
            </#if>
            </#list>
        </trim>
        WHERE
            ${pkCol.colName} = <#noparse>#{</#noparse>${pkCol.colJavaName}<#noparse>}</#noparse>
    </update>
    </#if>

    <#if (pkCol)??>
    <update id="updateAll" parameterType="${mainPackage}${subPackage}po.${typeName}">
        UPDATE 
            ${table.tableName} 
        SET 
            <#list table.columnList as col>
            <#if (!col.isPk)>
            ${ws_(col.colName, colLen, 20)} = <#noparse>#{</#noparse>${col.colJavaName}, jdbcType=${col.colJdbcType}<#noparse>}</#noparse><#if col_has_next>,</#if>
            </#if>
            </#list>
        WHERE
            ${pkCol.colName} = <#noparse>#{</#noparse>${pkCol.colJavaName}<#noparse>}</#noparse>
    </update>
    </#if>

    <#if (pkCol)??>
    <select id="getById" resultMap="${typeName}">
        SELECT 
            <include refid="columns" />
        FROM 
            ${table.tableName}
        WHERE
            ${pkCol.colName} = <#noparse>#{</#noparse>${pkCol.colJavaName}<#noparse>}</#noparse>
    </select>
    </#if>

    <select id="getAll" resultMap="${typeName}">
        SELECT
            <include refid="columns" />
        FROM 
            ${table.tableName}
        <include refid="dynamicWhere" />
        <if test="orderStack != null">
            ORDER BY
            <foreach item="orderField" collection="orderStack" separator=",">
                <#noparse>#{</#noparse>orderField.orderField<#noparse>}</#noparse> <#noparse>#{</#noparse>orderField.orderSeq<#noparse>}</#noparse>
            </foreach>
        </if>
        <if test="offset != null and rowCount != null">
            LIMIT
                <#noparse>#{</#noparse>offset<#noparse>}</#noparse>, <#noparse>#{</#noparse>rowCount<#noparse>}</#noparse>
        </if>
    </select>

    <select id="getCount" resultType="int">
        SELECT COUNT(*) FROM 
            ${table.tableName}
        <include refid="dynamicWhere" />
    </select>

</mapper>
