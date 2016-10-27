package com.memory.codegen.util.yaml;

import com.memory.codegen.util.NiceUtil;
import org.ho.yaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Author: MemoRyAxis
 * Date: 16-10-26
 * Time: 下午9:06
 */
public class YamlConfigUtil {

    private static YamlConfig config;

    static {
        File file = new File(NiceUtil.getResourcesPath("conf/config.yml"));
        try {
            config = Yaml.loadType(file, YamlConfig.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static YamlConfig getConfig() {
        return config;
    }
}

