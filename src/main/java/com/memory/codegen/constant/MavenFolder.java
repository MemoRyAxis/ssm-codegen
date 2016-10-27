package com.memory.codegen.constant;

/**
 * Author: MemoRyAxis
 * Date: 16-10-27
 * Time: 下午3:48
 */
public enum MavenFolder {

    JAVA("java"),
    RESOURCES("resources"),
    WEBAPP("webapp");

    String filder;

    MavenFolder(String filder) {
        this.filder = filder;
    }

    public String getFilder() {
        return filder;
    }

}
