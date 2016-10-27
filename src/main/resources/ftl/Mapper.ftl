package ${mainPackage}${subPackage}mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ${mainPackage}${subPackage}po.${typeName};

@Repository
public interface ${typeName}Mapper {

    public Integer add(${typeName});

    public Integer addAll(${typeName});

    public Integer delById(Integer id);

    public Integer update();

    public Integer updateAll();

    public ${typeName} getById(Integer id);

    public List<${typeName}> getAll(Map<String, Object> params);

    public Integer getCount(Map<String, Object> params);

}
