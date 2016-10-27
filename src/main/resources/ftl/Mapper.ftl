package ${mainPackage}${subPackage}mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ${mainPackage}${subPackage}po.${typeName};

@Repository
public interface ${typeName}Mapper {

    Integer add(${typeName} ${typeNameLowerCase});

    Integer addAll(${typeName} ${typeNameLowerCase});

    Integer delById(Integer id);

    Integer update(${typeName} ${typeNameLowerCase});

    Integer updateAll(${typeName} ${typeNameLowerCase});

    ${typeName} getById(Integer id);

    List<${typeName}> getAll(Map<String, Object> params);

    Integer getCount(Map<String, Object> params);

}
