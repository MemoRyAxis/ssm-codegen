package ${mainPackage}${subPackage}service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${utilPackage}base.persistence.BaseDao;
import ${utilPackage}base.service.BaseService;
import ${mainPackage}${subPackage}dao.${typeName}Dao;
import ${mainPackage}${subPackage}po.${typeName};

@Service
public class ${typeName}Service extends BaseService<${typeName}, ${pkCol.colJavaType}> {

    @Autowired
    private ${typeName}Dao ${typeNameLowerCase}Dao;

    @Override
    protected BaseDao<${typeName}, ${pkCol.colJavaType}> getBaseDao() {
        return this.${typeNameLowerCase}Dao;
    }

}
