package com.memory.codegen.main;

import com.memory.codegen.constant.Layer;
import com.memory.codegen.model.ColumnModel;
import com.memory.codegen.model.TableModel;
import com.memory.codegen.util.FreeMarkerUtil;
import com.memory.codegen.util.MySqlHelper;
import com.memory.codegen.util.NiceUtil;
import com.memory.codegen.util.yaml.Model;
import com.memory.codegen.util.yaml.Table;
import com.memory.codegen.util.yaml.YamlConfig;
import com.memory.codegen.util.yaml.YamlConfigUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: MemoRyAxis
 * Date: 16-10-27
 * Time: 下午3:19
 */
public class Codegen {

    private static YamlConfig config;

    private static MySqlHelper sqlHelper;

    private static final String MAVEN_$ = "$m";

    private static final String LAYERS_$ = "$l";

    private static Codegen codegen;

    public static synchronized Codegen getInstance() {
        return codegen == null ? codegen = new Codegen() : codegen;
    }

    private Codegen() {
        init();
    }

    public void genCode() {
        generateCode();
    }

    private void init() {
        config = YamlConfigUtil.getConfig();
        sqlHelper = MySqlHelper.getInstance();
        sqlHelper.init(
                config.getDb().getDriver(),
                config.getDb().getUrl(),
                config.getDb().getUsername(),
                config.getDb().getPassword());
    }


    private String getProjectPath() {
        StringBuilder projectPath;

        String exportDir = config.getExport().getDir();
        if (NiceUtil.isEmptyString(exportDir)) {
            System.err.println("\t-> export dir can not be empty!");
        }
        projectPath = new StringBuilder(exportDir);

        String projectName = config.getProject().getName();
        if (NiceUtil.isEmptyString(projectName)) {
            System.err.println("\t-> project name can not be empty!");
        }
        projectPath.append(projectName).append(NiceUtil.SEP)
                .append("src").append(NiceUtil.SEP)
                .append("main").append(NiceUtil.SEP)
                .append(MAVEN_$).append(NiceUtil.SEP);

        return projectPath.toString();
    }


    private String getPackagePath() {

        String projectName = config.getProject().getName();
        if (NiceUtil.isEmptyString(projectName)) {
            System.err.println("\t-> project name can not be empty!");
            return "";
        }

        StringBuilder packagePath = new StringBuilder("");

        String mainPackage = config.getProject().getMainPackage();
        if (!NiceUtil.isEmptyString(mainPackage)) {
            for (String packageName : mainPackage.split("\\.")) {
                packagePath.append(packageName).append(NiceUtil.SEP);
            }
        } else {
            System.err.println("\t-> main package name can not be empty!");
            return "";
        }

        StringBuilder modulePackage = new StringBuilder();

        String moduleNames = config.getProject().getModulePackage();
        if (!NiceUtil.isEmptyString(moduleNames)) {
            for (String moduleName : moduleNames.split("\\.")) {
                modulePackage.append(moduleName).append(NiceUtil.SEP);
            }
        }

        packagePath.append(LAYERS_$).append(NiceUtil.SEP).append(modulePackage);

        return packagePath.toString();
    }


    private void generateCode() {

        Table[] tables = config.getTables();
        if (tables == null || tables.length < 1) {
            System.err.println("\t-> no table selected.");
            return;
        }

        Model[] models = config.getExport().getModels();
        if (models == null || models.length < 1) {
            System.err.println("\t-> no model selected.");
            return;
        }

        for (Table table : tables) {
            generateCode(table, models);
        }
    }


    private void generateCode(Table table, Model[] models) {
        String codeGenPath = getProjectPath() + getPackagePath();

        try {
            TableModel tableModel = sqlHelper.getTableInfo(table.getTableName());
            for (Model model : models) {
                Layer layer = Layer.fromString(model.getModel());
                String dir = codeGenPath
                        .replace(LAYERS_$, layer.getFolder())
                        .replace(MAVEN_$, layer.getMavenFolder().getFilder());

                File dirF = new File(dir);
                if (!dirF.exists()) {
                    dirF.mkdirs();
                }

                String typeName = table.getJavaType();
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("typeName", typeName);
                params.put("table", tableModel);

                params.put("typeNameLowerCase",
                        typeName.substring(0, 1).toLowerCase() + typeName.substring(1));

                if (!NiceUtil.isEmptyString(config.getProject().getMainPackage())) {
                    params.put("mainPackage",
                            config.getProject().getMainPackage() + '.');
                } else {
                    throw new Exception();
                }
                if (!NiceUtil.isEmptyString(config.getProject().getUtilPackage())) {
                    params.put("utilPackage",
                            config.getProject().getUtilPackage() + '.');
                } else {
                    throw new Exception();
                }

                if (!NiceUtil.isEmptyString(config.getProject().getModulePackage())) {
                    params.put("subPackage",
                            config.getProject().getModulePackage() + '.');
                } else {
                    params.put("subPackage", "");
                }

                for (ColumnModel col : tableModel.getColumnList()) {
                    if (col.getIsPk()) {
                        params.put("pkCol", col);
                    }
                }

                String fileName = dir + typeName + layer.getSuffix();
                FreeMarkerUtil.genFileByTemplate(
                        fileName,
                        NiceUtil.getResourcesPath("ftl"),
                        layer.getType() + ".ftl",
                        params);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

}
