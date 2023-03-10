package com.toonan.core.velocity;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.toonan.core.exception.WebplusException;
import com.toonan.core.matatype.Dto;
import com.toonan.core.util.WebplusUtil;





/**
 * 
 * 类描述： <b>Velocity模板引擎辅助类</b>
 * 创建人：陈骑元
 * 创建时间：2016-6-2 上午12:08:25
 * 修改人：蓝枫 
 * 修改时间：2016-6-2 上午12:08:25
 * 修改备注： 
 * @version
 */
public class WebplusVelocity {
	
	private static VelocityEngine ve;

	/**
	 * <b>实例化Velocity模板引擎并返回</b> 为避免冲突并独享配置参数，此处自己Velocity生成一个VelocityEngine实例
	 * 而非直接使用Velocity提供的单例引擎类org.apache.velocity.app.Velocity。
	 * 
	 * @return 返回VelocityEngine实例
	 */
	static{
		ve = new VelocityEngine();
		try {
			ve.init(getDefaultProperties());
		} catch (Exception e) {
			throw new WebplusException("初始化Velocity模板引擎实例失败", e);
		}
	}

	/**
	 * 加载Velocity模板引擎属性配置文件
	 * 
	 * @return
	 */
	private static Properties getDefaultProperties() {
		InputStream is = WebplusVelocity.class.getResourceAsStream("velocity.ims.properties");
		Properties props = new Properties();
		try {
			props.load(is);
			is.close();
		} catch (Exception e) {
			throw new WebplusException("读取Velocity模板引擎属性配置文件出错[velocity.ims.properties]", e);
		}
		return props;
	}

	/**
	 * 驱动字符串模板
	 * 
	 * @param pTemplate
	 *            模板对象
	 * @param pDto
	 *            合并参数集合(将模板中所需变量全部压入Dto)
	 * @return 返回StringWriter对象
	 * @throws Exception
	 */
	public static StringWriter mergeStringTemplate(String pTemplateString, Dto pDto) {
		if (WebplusUtil.isEmpty(pTemplateString)) {
			throw new NullPointerException("字符串模板不能为空");
		}
		StringWriter writer = new StringWriter();
		VelocityContext context = WebplusVelocity.convertDto2VelocityContext(pDto);
		try {
			ve.evaluate(context, writer, "", pTemplateString);
		} catch (Exception e) {
			throw new WebplusException("字符串模板合并失败", e);
		}
		return writer;
	}

	/**
	 * 驱动文件模板
	 * 
	 * @param pTemplate
	 *            模板对象
	 * @param pDto
	 *            合并参数集合(将模板中所需变量全部压入Dto)
	 * @return 返回StringWriter对象
	 * @throws Exception
	 * @throws Exception
	 */
	public static StringWriter mergeFileTemplate(String pTemplatePath, Dto pDto) {
		if (WebplusUtil.isEmpty(pTemplatePath)) {
			throw new NullPointerException("文件模板资源路径不能为空");
		}
		StringWriter writer = new StringWriter();
		Template template = null;
		try {
			template = ve.getTemplate(pTemplatePath);
		} catch (Exception e) {
			throw new WebplusException("解析文件模板失败", e);
		}
		VelocityContext context = WebplusVelocity.convertDto2VelocityContext(pDto);
		try {
			template.merge(context, writer);
		} catch (Exception e) {
			throw new WebplusException("文件模板合并失败", e);
		}
		return writer;
	}
	
	/**
	 * 将Dto对象转换为VelocityContext对象
	 * 
	 * @param pDto
	 *            传入的Dto对象
	 * 
	 * @return 返回VelocityContext对象
	 */
	public static VelocityContext convertDto2VelocityContext(Dto pDto) {
		if (WebplusUtil.isEmpty(pDto))
			return null;
		@SuppressWarnings("rawtypes")
		Iterator it = pDto.keySet().iterator();
		VelocityContext context = new VelocityContext();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = pDto.get(key);
			context.put(key, value);
		}
		return context;
	}
}
