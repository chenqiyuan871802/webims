package com.ims.common.builder;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;

/**
 * 
 * 类名:com.ims.common.builder.WebimsBuilder 描述:代码生成工具类 编写者:陈骑元
 * 创建时间:2018年4月5日下午11:24:26 修改说明:
 */

public class WebimsBuilder {
	public static void main(String[] args) {
		String outputDir = "e:\\dao";
		final String viewOutputDir = outputDir + "/view/";
		AutoGenerator mpg = new AutoGenerator();
		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(outputDir);
		gc.setFileOverride(true);
		gc.setActiveRecord(true);
		// XML 二级缓存
		gc.setEnableCache(false);
		// XML ResultMap
		gc.setBaseResultMap(true);
		// XML columList
		gc.setBaseColumnList(true);
		gc.setAuthor("陈骑元");
		
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("root");
		dsc.setUrl("jdbc:mysql://127.0.0.1:3306/webims?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false");
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
		strategy.setTablePrefix(new String[] { "sys_" });// 此处可以修改为您的表前缀
		// 表名生成策略
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setSuperControllerClass("com.ims.common.web.BaseController");
		// strategy.setSuperEntityColumns(new String[] { "update_by",
		// "update_time" });
		strategy.setInclude(new String[] {"sys_role_user" }); // 需要生成的表
		mpg.setStrategy(strategy);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent("com.ims");
		pc.setController("controller");
		gc.setServiceName("%sService");
		pc.setEntity("model");
		pc.setModuleName("system");
		mpg.setPackageInfo(pc);

		// 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
			}
		};
		// 生成的模版路径，不存在时需要先新建
		File viewDir = new File(viewOutputDir);
		if (!viewDir.exists()) {
			viewDir.mkdirs();
		}

		mpg.setCfg(cfg);
		/*TemplateConfig tc = new TemplateConfig();
		tc.setController("/templates/generator/controller.java.vm");
		tc.setService("/templates/generator/service.java.vm");
		tc.setServiceImpl("/templates/generator/serviceImpl.java.vm");
		tc.setMapper("/templates/generator/mapper.java.vm");
		tc.setXml("/templates/generator/mapper.xml.vm");
		tc.setEntity("templates/generator/entity.java.vm");
		mpg.setTemplate(tc);*/

		// 生成controller相关
		mpg.execute();
	}

}
