package ${mainPackage}.${subPackage}.dao;

import org.springframework.stereotype.Repository;

import ${utilPackage}.base.persistence.BaseDao;
import ${mainPackage}.${subPackage}.po.${typeName};

@Repository
public class ${typeName}Dao extends BaseDao<${typeName}, ${pkCol.colJavaType}> {

    @Override
    public Class<${typeName}> getEntityClass() {
        return ${typeName}.class;
    }

}
