package com.memory.codegen.util;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * simple freemarker utility
 *
 * @author memoryaxis@gmail.com
 */
@SuppressWarnings("deprecation")
public class FreeMarkerUtil {

    private static Configuration config;

    public static void genFileByTemplate(String filePath, String tempDir, String tempName,
                                         Map<String, Object> params) {
        try {
            config.setDirectoryForTemplateLoading(new File(tempDir));
            Template template = config.getTemplate(tempName);
            File file = new File(filePath);
//            if (file.exists()) {
//                System.out.println("\t-> file is already exists!");
//                boolean removeSuccess = false;
//                int flag = 0;
//                while (!removeSuccess) {
//                    flag++;
//                    File fileBak = new File(filePath + ".bak" + flag);
//                    if (fileBak.exists()) continue;
//                    removeSuccess = file.renameTo(fileBak);
//                }
//                System.out.println("\t-> overwrite file: " + filePath);
//                FileWriter out = new FileWriter(filePath);
//                params.put("ws_", new WhiteSpaceTMM());
//                template.process(params, out);
//            } else {
//            }
            System.out.println("\t-> generate file: " + filePath);
            FileWriter out = new FileWriter(filePath);
            params.put("ws_", new WhiteSpaceTMM());
            template.process(params, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        config = new Configuration();
    }
}


class WhiteSpaceTMM implements TemplateMethodModelEx {

    @SuppressWarnings("rawtypes")
    @Override
    public Object exec(List paramList) throws TemplateModelException {
        if (paramList.size() < 3) throw new TemplateModelException();
        String value = paramList.get(0).toString();
        int maxLen = Integer.parseInt(paramList.get(1).toString());
        int method = Integer.parseInt(paramList.get(2).toString());

        StringBuilder formatStr = new StringBuilder("%");
        StringBuilder valueStr = new StringBuilder();

        switch (method) {
            case 10: // ___id
                formatStr.append(maxLen + 1).append('s');
                valueStr.append(value);
                break;
            case 20: // id___
                formatStr.append("-").append(maxLen + 1).append('s');
                valueStr.append(value);
                break;
            case 11: // ___"id"
                formatStr.append(maxLen + 3).append('s');
                valueStr.append('"').append(value).append('"');
                break;
            case 21: // "id"___
                formatStr.append("-").append(maxLen + 3).append('s');
                valueStr.append('"').append(value).append('"');
                break;
            case 12: // ___id"
                formatStr.append(maxLen + 2).append('s');
                valueStr.append(value).append('"');
                break;
            case 22: // "id___
                formatStr.append("-").append(maxLen + 2).append('s');
                valueStr.append('"').append(value);
                break;
            default:
                throw new TemplateModelException("invalid parameter!");
        }

        return String.format(formatStr.toString(), valueStr.toString());
    }
}
