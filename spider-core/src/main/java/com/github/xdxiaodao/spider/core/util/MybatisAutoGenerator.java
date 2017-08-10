package com.github.xdxiaodao.spider.core.util;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/8/7
 * Time 19:33
 * Desc
 */
public class MybatisAutoGenerator {

    public static void main(String[] args) {
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(
                    MybatisAutoGenerator.class.getResourceAsStream("/mybatis/generator/generatorConfig.xml"));
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
