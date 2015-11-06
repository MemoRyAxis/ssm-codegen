package ${mainPackage}.${subPackage}.po;

import ${utilPackage}.base.model.BaseModel;

public class ${typeName} extends BaseModel {

    private static final long serialVersionUID = 1L;
    <#list table.columnList as col>

    private ${col.colJavaType} ${col.colJavaName};
    </#list>
    <#list table.columnList as col>

    public ${col.colJavaType} get${col.colGetSetName}() {
        return ${col.colJavaName};
    }

    public void set${col.colGetSetName}(${col.colJavaType} ${col.colJavaName}) {
        this.${col.colJavaName} = ${col.colJavaName};
    }
    </#list>
}