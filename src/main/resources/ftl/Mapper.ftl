package ${mainPackage}${subPackage}mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ${mainPackage}${subPackage}po.${typeName};

@Repository
public interface ${typeName}Mapper {

    Integer add(${typeName} ${typeNameLowerCase});

    Integer addAll(${typeName} ${typeNameLowerCase});

    <#if (pkCol)??>
    Integer delById(Integer id);
    </#if>

    Integer update(${typeName} ${typeNameLowerCase});

    Integer updateAll(${typeName} ${typeNameLowerCase});

    <#if (pkCol)??>
    ${typeName} getById(Integer id);
    </#if>

    List<${typeName}> getAll(Map<String, Object> params);

    Integer getCount(Map<String, Object> params);

    List<${typeName}> getAll(${typeName} ${typeNameLowerCase});

    Integer getCount(${typeName} ${typeNameLowerCase});

}
