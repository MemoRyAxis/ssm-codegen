package com.memory.codegen.main;

import com.memory.codegen.model.ColumnModel;
import com.memory.codegen.model.TableModel;
import com.memory.codegen.util.FreeMarkerUtil;
import com.memory.codegen.util.MySqlHelper;
import com.memory.codegen.util.NiceUtil;
import com.memory.codegen.util.PropertiesUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * main
 *
 * @author memoryaxis@gmail.com
 */
public class Codegen {

    private static final String SEP = System.getProperty("file.separator");

    private static final String MAVEN_$ = "$m";
    private static final String[] MAVEN_FOLDERS = new String[]{"java", "resources"}; // , "webapp"};

    private static final String LAYERS_$ = "$c";
    private static final String[] LAYER_TYPES = new String[]{"Po", "Dao", "MapperDao", "Service", "Controller", "MapperXML"}; // , "view"};
    private static final String[] LAYER_FOLDER = new String[]{"po", "dao", "mapper", "service", "controller", "mapper"}; // , "view"};
    private static final String[] LAYER_SUFFIX = new String[]{".java", "Dao.java", "Mapper.java", "Service.java", "Controller.java", "Mapper.xml"}; // , "view"};

    public static void main(String[] args) throws Exception {

        String projectPath = getProjectPath();
        if (NiceUtil.isEmptyString(projectPath)) {
            return;
        }

        String packagePath = getPackagePath();
        if (NiceUtil.isEmptyString(packagePath)) {
            return;
        }

        String codeGenPath = projectPath + packagePath;

        MySqlHelper helper = MySqlHelper.getInstance();
        helper.init();

        String[] models = PropertiesUtil.getStringValue("tableName.typeName").split(",");
        for (String model : models) {
            String tableName = model.split(":")[0];
            String typeName = model.split(":")[1];
            TableModel table = helper.getTableInfo(tableName);

            for (int i = 0; i < LAYER_TYPES.length; i++) {
                String layerType = LAYER_TYPES[i];
                String layerFolder = LAYER_FOLDER[i];
                String layerSuffix = LAYER_SUFFIX[i];

                String dir = codeGenPath.replace(LAYERS_$, layerFolder);

                if (layerType.equals("MapperXML")) {
                    dir = dir.replace(MAVEN_$, MAVEN_FOLDERS[1]);
                } else {
                    dir = dir.replace(MAVEN_$, MAVEN_FOLDERS[0]);
                }

                File dirF = new File(dir);
                if (!dirF.exists()) {
                    dirF.mkdirs();
                }

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("typeName", typeName);
                params.put("typeNameLowerCase",
                        typeName.substring(0, 1).toLowerCase() + typeName.substring(1));
                params.put("mainPackage", PropertiesUtil.getStringValue("main.package.name"));
                params.put("subPackage", PropertiesUtil.getStringValue("module.package.name"));
                params.put("utilPackage", PropertiesUtil.getStringValue("util.package.name"));
                params.put("table", table);

                for (ColumnModel col : table.getColumnList()) {
                    if (col.getIsPk()) {
                        params.put("pkCol", col);
                    }
                }

                String fileName = dir + typeName + layerSuffix;
                FreeMarkerUtil.genFileByTemplate(fileName, NiceUtil.getResourcesPath("ftl"), layerType + ".ftl", params);
            }
        }

        helper.destory();
    }

    /**
     * get project absolute path of project
     */
    private static String getProjectPath() {

        StringBuilder projectPath;

        String exportDir = PropertiesUtil.getStringValue("export.dir");
        if (NiceUtil.isEmptyString(exportDir)) {
            System.err.println("\t-> export dir can not be empty!");
            return "";
        }
        projectPath = new StringBuilder(exportDir);

        String projectName = PropertiesUtil.getStringValue("project.name");
        if (NiceUtil.isEmptyString(projectName)) {
            System.err.println("\t-> project name can not be empty!");
        }
        projectPath.append(projectName).append(SEP);

        String projectBuildType = PropertiesUtil.getStringValue("project.build.type");
        if (NiceUtil.isEmptyString(projectBuildType) || projectBuildType.equals("maven")) {
            projectPath.append("src").append(SEP).append("main").append(SEP).append(MAVEN_$)
                    .append(SEP);
        } else {
            // TODO others
            System.err.println("\t-> build failed!");
            return "";
        }

        return projectPath.toString();
    }

    /**
     * get package path
     */
    private static String getPackagePath() {

        int projectType = 0;
        try {
            projectType = PropertiesUtil.getIntegerValue("project.structure");
        } catch (Exception e) {
            System.err.println("\t-> project structure should be a number!");
            return "";
        }

        String projectName = PropertiesUtil.getStringValue("project.name");
        if (NiceUtil.isEmptyString(projectName)) {
            System.err.println("\t-> project name can not be empty!");
            return "";
        }

        StringBuilder packagePath = new StringBuilder("");

        String mainPackage = PropertiesUtil.getStringValue("main.package.name");
        if (!NiceUtil.isEmptyString(mainPackage)) {
            for (String packageName : mainPackage.split("\\.")) {
                packagePath.append(packageName).append(SEP);
            }
        } else {
            System.err.println("\t-> main package name can not be empty!");
            return "";
        }

        StringBuilder modulePackage = new StringBuilder();

        String moduleNames = PropertiesUtil.getStringValue("module.package.name");
        if (!NiceUtil.isEmptyString(moduleNames)) {
            for (String moduleName : moduleNames.split("\\.")) {
                modulePackage.append(moduleName).append(SEP);
            }
        }

        if (projectType == 2) {
            packagePath.append(modulePackage).append(LAYERS_$).append(SEP);
        } else {
            packagePath.append(LAYERS_$).append(SEP).append(modulePackage);
        }

        return packagePath.toString();
    }

}
