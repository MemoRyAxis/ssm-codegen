package ${mainPackage}.${subPackage}.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import ${utilPackage}.base.web.BaseController;
import ${mainPackage}.${subPackage}.service.${typeName}Service;
import ${mainPackage}.${subPackage}.po.${typeName};

@Controller
@RequestMapping(value = "/${typeNameLowerCase}", produces = "application/json;charset=utf-8")
public class ${typeName}Controller extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(${typeName}Controller.class);

    @Autowired
    private ${typeName}Service ${typeNameLowerCase}Service;

    @ResponseBody
    @RequestMapping(value = {"/list", "/"}, method = RequestMethod.GET)
    public String list(${typeName} ${typeNameLowerCase}) {

        // TODO

        List<${typeName}> list = ${typeNameLowerCase}Service.getAll(${typeNameLowerCase});
        return responseJson(SUCCESS, list);
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(@RequestParam(value = "${pkCol.colJavaName}, required = false") ${pkCol.colJavaType} ${pkCol.colJavaName}) {
        return ${pkCol.colJavaName} == null ? responseJson(FAILURE) : responseJson(SUCCESS, ${typeNameLowerCase}Service.getById(${pkCol.colJavaName}));
    }

    @ResponseBody
    @RequestMapping(value = {"/save", "/{id}"}, method = RequestMethod.POST)
    public String save(${typeName} ${typeNameLowerCase}) {

        int result;
        try {
            if (${typeNameLowerCase}.get${pkCol.colGetSetName}() != null) {

                // TODO

                result = ${typeNameLowerCase}Service.update(${typeNameLowerCase});
            } else {

                // TODO

                result = ${typeNameLowerCase}Service.add(${typeNameLowerCase});
            }
        } catch (Exception e) {
            log.error("save ${typeNameLowerCase} fail!", e);
            return responseJson(ERROR);
        }

        return result > 0 ? responseJson(SUCCESS) : responseJson(FAILURE);
    }

    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public String del(@RequestParam(value = "${pkCol.colJavaName}, required = false") ${pkCol.colJavaType} ${pkCol.colJavaName}) {
        return ${pkCol.colJavaName} == null ? responseJson(FAILURE) :
                (${typeNameLowerCase}Service.delById(${pkCol.colJavaName}) != 1 ? responseJson(FAILURE) : responseJson(SUCCESS));
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getRest(@PathVariable(value = "${pkCol.colJavaName}") ${pkCol.colJavaType} ${pkCol.colJavaName}) {
        return ${pkCol.colJavaName} == null ? responseJson(FAILURE) : responseJson(SUCCESS, ${typeNameLowerCase}Service.getById(${pkCol.colJavaName}));
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public String delRest(@PathVariable(value = "${pkCol.colJavaName}") ${pkCol.colJavaType} ${pkCol.colJavaName}) {
        return ${pkCol.colJavaName} == null ? responseJson(FAILURE) :
                (${typeNameLowerCase}Service.delById(${pkCol.colJavaName}) != 1 ? responseJson(FAILURE) : responseJson(SUCCESS));
    }
}
