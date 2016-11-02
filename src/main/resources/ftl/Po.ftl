package ${mainPackage}${subPackage!}po;

import ${utilPackage}model.BaseModel;

/**
 * <#if table.tabComment != "">${table.tabComment}</#if>
 */
public class ${typeName} extends BaseModel {

    private static final long serialVersionUID = 1L;
    <#list table.columnList as col>
        <#if col.colJavaType=="java.util.Date">

    // ${col.comment}
    private ${col.colJavaType} ${col.colJavaName};

    private ${col.colJavaType} start${col.colGetSetName};

    private ${col.colJavaType} end${col.colGetSetName};
        <#else>

        <#if col.comment != "">
    // ${col.comment}
        </#if>
    private ${col.colJavaType} ${col.colJavaName};
        </#if>
    </#list>
    <#list table.columnList as col>
        <#if col.colJavaType=="java.util.Date">

    public ${col.colJavaType} get${col.colGetSetName}() {
        return ${col.colJavaName};
    }

    public void set${col.colGetSetName}(${col.colJavaType} ${col.colJavaName}) {
        this.${col.colJavaName} = ${col.colJavaName};
    }

    public ${col.colJavaType} getStart${col.colGetSetName}() {
        return start${col.colGetSetName};
    }

    public void setStart${col.colGetSetName}(${col.colJavaType} start${col.colGetSetName}) {
        this.start${col.colGetSetName} = start${col.colGetSetName};
    }

    public ${col.colJavaType} getEnd${col.colGetSetName}() {
        return end${col.colGetSetName};
    }

    public void setEnd${col.colGetSetName}(${col.colJavaType} end${col.colGetSetName}) {
        this.end${col.colGetSetName} = end${col.colGetSetName};
    }
        <#else>

    public ${col.colJavaType} get${col.colGetSetName}() {
        return ${col.colJavaName};
    }

    public void set${col.colGetSetName}(${col.colJavaType} ${col.colJavaName}) {
        this.${col.colJavaName} = ${col.colJavaName};
    }
        </#if>
    </#list>

}
