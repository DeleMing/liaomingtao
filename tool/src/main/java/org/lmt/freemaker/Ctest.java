package org.lmt.freemaker;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: LiaoMingtao
 * @date: 2022/9/8
 */
@Slf4j
public class Ctest {

    public static void main(String[] args) {
        // String templateStr = "${key}";
        String templateStr = "<#if keys.a?? && (keys.a?length gt 0)>\n" +
                "${keys.a}\n" +
                "</#if>";
        Map<String, Object> map =new HashMap<>(8);
        map.put("keys","HLzq2022$#@!");
        Map<String, Object> map2 = new HashMap<>(8);
        map2.put("a", "a");
        map.put("keys", map2);
        System.out.println(freemarkerProcess(map, templateStr));
    }

    public static String freemarkerProcess(Map<String, Object> input, String templateStr) {
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        String template = "content";
        stringLoader.putTemplate(template, templateStr);
        Configuration cfg = new Configuration();
        cfg.setTemplateLoader(stringLoader);
        try {
            Template templateCon = cfg.getTemplate(template);
            StringWriter writer = new StringWriter();
            templateCon.process(input, writer);
            return writer.toString();
        } catch (Exception e) {
            log.error("freemarkerProcess errMsg:", e);
        }
        return null;
    }
}
