package com.memory.codegen.util.yaml;

/**
 * Author: MemoRyAxis
 * Date: 16-10-26
 * Time: 下午9:33
 */
public class Project {

    String home;

    String name;

    String buildTool;

    String mainPackage;

    String modulePackage;

    String utilPackage;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getModulePackage() {
        return modulePackage;
    }

    public void setModulePackage(String modulePackage) {
        this.modulePackage = modulePackage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildTool() {
        return buildTool;
    }

    public void setBuildTool(String buildTool) {
        this.buildTool = buildTool;
    }

    public String getMainPackage() {
        return mainPackage;
    }

    public void setMainPackage(String mainPackage) {
        this.mainPackage = mainPackage;
    }

    public String getUtilPackage() {
        return utilPackage;
    }

    public void setUtilPackage(String utilPackage) {
        this.utilPackage = utilPackage;
    }
}
