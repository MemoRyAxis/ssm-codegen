package com.memory.codegen.util.yaml;

/**
 * Author: MemoRyAxis
 * Date: 16-10-26
 * Time: 下午9:32
 */
public class YamlConfig {

    Db db;

    Project project;

    Export export;

    Table[] tables;

    public Db getDb() {
        return db;
    }

    public void setDb(Db db) {
        this.db = db;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Export getExport() {
        return export;
    }

    public void setExport(Export export) {
        this.export = export;
    }

    public Table[] getTables() {
        return tables;
    }

    public void setTables(Table[] tables) {
        this.tables = tables;
    }
}
