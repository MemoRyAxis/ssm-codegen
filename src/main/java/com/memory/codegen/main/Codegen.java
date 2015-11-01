package com.memory.codegen.main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.memory.codegen.model.ColumnModel;
import com.memory.codegen.model.TableModel;
import com.memory.codegen.util.FreeMarkerUtil;
import com.memory.codegen.util.MySqlHelper;
import com.memory.codegen.util.NiceUtil;
import com.memory.codegen.util.PropertiesUtil;

/**
 * main
 * 
 * @author memoryaxis@gmail.com
 */
public class Codegen {

    private static final String sep = System.getProperty("file.separator");

    private static final String[] MAVEN_FOLDERS = new String[] {"java", "resources", "webapp"};
    private static final String MAVEN_$ = "$m";

    private static final String[] LAYER_FOLDERS = new String[] {"Po", "Dao", "Service",
            "Controller", "Mapper"}; // , "view"};
    // TODO controller HTML

    private static final String LAYERS_$ = "$c";

    public static void main(String[] args) throws Exception {

        String projectPath = getProjectPath();

        String packagePath = "";
        try {
            int projectType = PropertiesUtil.getIntegerValue("project.type");
            packagePath = getPackagePath(projectType);
        } catch (Exception e) {
            packagePath = getPackagePath(1);
        }

        String codeGenPath = projectPath + packagePath;

        MySqlHelper helper = MySqlHelper.getInstance();
        helper.init();

        String[] models = PropertiesUtil.getStringValue("tableName.typeName").split(",");
        for (String model : models) {
            String tableName = model.split(":")[0];
            String typeName = model.split(":")[1];
            TableModel table = helper.getTableInfo(tableName);

            for (String codeFolder : LAYER_FOLDERS) {

                String dir = codeGenPath.replace(LAYERS_$, codeFolder.toLowerCase());

                String suffix;
                if (codeFolder.equals("Mapper")) {
                    dir = dir.replace(MAVEN_$, MAVEN_FOLDERS[1]);
                    suffix = ".xml";
                } else {
                    dir = dir.replace(MAVEN_$, MAVEN_FOLDERS[0]);
                    suffix = ".java";
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
                params.put("subPackage", PropertiesUtil.getStringValue("sub.package.name"));
                params.put("utilPackage", PropertiesUtil.getStringValue("util.package.name"));
                params.put("table", table);

                for (ColumnModel col : table.getColumnList()) {
                    if (col.getIsPk()) {
                        params.put("pkCol", col);
                    }
                }

                String fileName =
                        dir + typeName + (codeFolder.equals("Po") ? "" : codeFolder) + suffix;

                FreeMarkerUtil.genFileByTemplate(fileName, NiceUtil.getResourcesPath("ftl"),
                        codeFolder + ".ftl", params);
            }
        }

        helper.destory();
    }

    private static String getProjectPath() {

        StringBuilder projectPath;

        String exportDir = PropertiesUtil.getStringValue("export.dir");
        if (NiceUtil.isEmptyString(exportDir)) {
            System.err.println("\t-> export dir can not be empty!");
        }
        projectPath = new StringBuilder(exportDir);

        String projectName = PropertiesUtil.getStringValue("project.name");
        if (NiceUtil.isEmptyString(projectName)) {
            System.err.println("\t-> project name can not be empty!");
        }
        projectPath.append(projectName).append(sep);

        String projectBuildType = PropertiesUtil.getStringValue("project.build.type");
        if (projectBuildType.equals("maven")) {
            projectPath.append("src").append(sep).append("main").append(sep).append(MAVEN_$)
                    .append(sep);
        } else {
            // TODO others
        }

        return projectPath.toString();
    }

    private static String getPackagePath(int projectType) {

        String projectName = PropertiesUtil.getStringValue("project.name");
        if (NiceUtil.isEmptyString(projectName)) {
            System.err.println("\t-> project name can not be empty!");
        }

        StringBuilder packagePath = new StringBuilder("");
        String companyName = PropertiesUtil.getStringValue("main.package.name");
        if (!NiceUtil.isEmptyString(companyName)) {
            for (String packageName : companyName.split("\\.")) {
                packagePath.append(packageName).append(sep);
            }
        } else {
            packagePath.append("com").append(projectName).append(sep);
        }

        StringBuilder modulePackage = new StringBuilder();
        String moduleNames = PropertiesUtil.getStringValue("sub.package.name");
        if (!NiceUtil.isEmptyString(moduleNames)) {
            for (String moduleName : moduleNames.split("\\.")) {
                modulePackage.append(moduleName).append(sep);
            }
        }

        if (projectType == 2) {
            packagePath.append(LAYERS_$).append(sep).append(modulePackage);
        } else {
            packagePath.append(modulePackage).append(LAYERS_$).append(sep);
        }

        return packagePath.toString();
    }

}
