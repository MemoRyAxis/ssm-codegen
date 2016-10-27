package com.memory.codegen.constant;

/**
 * Author: MemoRyAxis
 * Date: 16-10-27
 * Time: 下午3:42
 */
public enum Layer {

    PO("Po", "po", ".java", MavenFolder.JAVA),

    DAO("Dao", "dao", "Dao.java", MavenFolder.JAVA),
    DAO_XML("DaoXML", "dao", "Dao.xml", MavenFolder.RESOURCES),

    MAPPER("Mapper", "mapper", "Mapper.java", MavenFolder.JAVA),
    MAPPER_XML("MapperXML", "mapper", "Mapper.xml", MavenFolder.RESOURCES),

    SERVICE("Service", "service", "Service.java", MavenFolder.JAVA),
    Controller("Controller", "controller", "Controller.java", MavenFolder.JAVA);

    String type;

    String folder;

    String suffix;

    MavenFolder mavenFolder;

    Layer(String type, String folder, String suffix, MavenFolder mavenFolder) {
        this.type = type;
        this.folder = folder;
        this.suffix = suffix;
        this.mavenFolder = mavenFolder;
    }

    public String getType() {
        return type;
    }

    public String getFolder() {
        return folder;
    }

    public String getSuffix() {
        return suffix;
    }

    public MavenFolder getMavenFolder() {
        return mavenFolder;
    }

    public static Layer fromString(String layerType) {
        if (layerType == null || layerType.length() < 1)
            return null;
        for (Layer layer : values())
            if (layer.type.toLowerCase().equals(layerType.toLowerCase()))
                return layer;
        return null;
    }

}
